package tacoguy56;

public class SquareGenerator extends WaveSource{
    @Override
    public Wave getWaveForm(double frequency, double len, double amplitude){
        //Samples per second / waves per second = samples / wave
        double waveLen = Sampler.SAMPLERATE / frequency;
        //toReturn is length in seconds times samples per second = # of samples
        int[] toReturn = new int[(int)(len*Sampler.SAMPLERATE)];
        for(int i = 0; i < toReturn.length; i++) {
            //if more than halfway through current cycle - peak, else trough
            //2^Samplerate = max amplitude, 0 is min
            if(i/waveLen - (int)(i / waveLen) > 0.5)
                //255 is max for 8 bits, not 256
                toReturn[i] = (int) (Math.pow(2, Sampler.SAMPLESIZE) * amplitude) - 1;
            else
                //go same amount below 2^(SAMPLESIZE-1) as you did above
                toReturn[i] = (int) (Math.pow(2, Sampler.SAMPLESIZE) * (1 - amplitude));
            //shift center to zero if signed
            toReturn[i] -= Math.pow(2, Sampler.SAMPLESIZE-1);
        }
        return new Wave(Sampler.SAMPLERATE, Sampler.SAMPLESIZE, 1, true, toReturn);
    }
}
