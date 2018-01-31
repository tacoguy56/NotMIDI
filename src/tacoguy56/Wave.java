package tacoguy56;

public class Wave {
    public final WaveFormat format;
    public final int[] samples;
    public Wave(int sampleRate, int sampleSize, int channels, boolean signed, int[] samples){
        format = new WaveFormat(sampleRate, sampleSize, signed, channels);
        this.samples = samples;
    }
    public Wave(WaveFormat format, int[] samples){
        this.format = format;
        this.samples = samples;
    }
    public boolean isCompatible(Wave toCompare){
        return format.sampleRate == toCompare.format.sampleRate && format.sampleSize == toCompare.format.sampleSize && format.signed == toCompare.format.signed && format.channels ==  toCompare.format.channels;
    }
    public byte[] convertToBytes(boolean isBigEndian){
        //QOL
        int numBytes = format.sampleSize / 8;
        byte[] toReturn = new byte[samples.length * numBytes];
        //most significant first
        if(isBigEndian)
            for (int i = 0; i < samples.length * numBytes; i++) {
                //in[i/numBytes] = get the corresponding int, if there are 2 bytes per int, divide by two and round down
                //unsigned bitshift by the number of bytes -- starting at numBytes-1 and counting down
                //it'll cut off all but the least significant 8 bits when casting to a byte
                int bitShift = (8*(numBytes - 1 - i % numBytes));
                toReturn[i] = (byte) (samples[i / numBytes] >>> bitShift);
                //if this is the most significant byte, replace the most significant bit with the sign bit of the integer being copied
                if(format.signed && bitShift/8 == numBytes - 1) {
                    //set bit 7 to 0
                    toReturn[i] = (byte)(toReturn[i] & 0x7F);
                    //bitshift the int 3 bytes over and the mask only the sign bit to align it and have the rest set to zero.
                    toReturn[i] = (byte)(toReturn[i] | ((samples[i/numBytes] >>> 24) & 0x80));
                }
            }
            //least significant first
        else
            for (int i = 0; i < samples.length * numBytes; i++) {
                int bitShift = 8 * (i % numBytes);
                toReturn[i] = (byte) (samples[i / numBytes] >>> bitShift);
                //if this is the most significant byte, replace the most significant bit with the sign bit of the integer being copied
                if(format.signed && bitShift/8 == numBytes - 1) {
                    //set bit 7 to 0
                    toReturn[i] = (byte)(toReturn[i] & 0x7F);
                    //bitshift the int 3 bytes over and the mask only the sign bit to align it and have the rest set to zero.
                    toReturn[i] = (byte)(toReturn[i] | ((samples[i/numBytes] >>> 24) & 0x80));
                }
            }

        return toReturn;
    }
    public static Wave byteToWave(byte[] in, WaveFormat format, boolean isBigEndian){
        int[] samples = new int[in.length * 8 / format.sampleSize];
        int numBytes = format.sampleSize / 8;
        int toBitShift;
            for(int i = 0; i < in.length; i++) {
                if(isBigEndian) toBitShift = 8 * (numBytes - 1 - (i % numBytes));
                else toBitShift = 8 * (i % numBytes);
                samples[i / numBytes] |= in[i] << toBitShift;
                if(toBitShift == numBytes-1 && format.signed){
                    boolean signDigit = (in[i] & 0x80) > 0;
                    if(signDigit)samples[i/numBytes] |= (1 << 31);
                    else samples[i/numBytes] &= ~(1 << 31);
                }
            }

        return new Wave(format, samples);
    }
}
