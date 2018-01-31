package tacoguy56;

public class WaveFormat {
    public final int sampleRate;
    public final int sampleSize;
    public final boolean signed;
    public final int channels;
    public WaveFormat(int sampleRate, int sampleSize, boolean signed, int channels){
        this.sampleRate = sampleRate;
        this.sampleSize = sampleSize;
        this.signed = signed;
        this.channels = channels;
    }
}
