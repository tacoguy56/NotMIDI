package tacoguy56;

public class SineGenerator extends WaveSource {
    @Override
    public Wave getWaveForm(double frequency, double len, double amplitude) {
        int[] toReturn = new int[(int)(len*Sampler.SAMPLERATE)];
        double period = Sampler.SAMPLERATE / frequency;
        for(int i = 0; i < toReturn.length; i++) {
            toReturn[i] =  (int)(Math.sin(2*Math.PI * i / period) * (Math.pow(2, Sampler.SAMPLESIZE-1)- 1));
        }
        return new Wave(Sampler.SAMPLERATE, Sampler.SAMPLESIZE, 1, true, toReturn);
    }
}
