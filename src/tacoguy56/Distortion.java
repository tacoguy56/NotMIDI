package tacoguy56;

public class Distortion extends FX{
    @Override
    public Wave processWave(Wave inWave) {

        int[] in = inWave.samples;
        int[] toReturn = new int[in.length];
        for(int i = 0, sample; i < in.length; i++){
            //ugly function that does distortion as -1<=x<1 -> x/|x| * (1-e^(x^2/|x|))
            //found on stackoverflow lol
            sample = in[i];
            if(inWave.format.signed) sample -= Math.pow(2, inWave.format.sampleSize-1);
            if(sample == 0)toReturn[i] = 0;
            else toReturn[i] = (int)(sample/Math.abs(sample) * Math.pow(2, inWave.format.sampleSize-1) * (1 - Math.pow(Math.E, Math.abs(sample / Math.pow(2, inWave.format.sampleSize-1)))));
        }
        return new Wave(inWave.format.sampleRate, inWave.format.sampleSize, inWave.format.channels, true, toReturn) ;
    }
}
