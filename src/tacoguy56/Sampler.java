package tacoguy56;

import javax.sound.sampled.*;
public class Sampler{
    public static final int SAMPLERATE = 22050;
    public static final int SAMPLESIZE = 16;
    public static final int CHANNELS = 1;
    public static final boolean SIGNED = true;
    public static final boolean BIGENDIAN = true;

    private AudioFormat format;
    private TargetDataLine target;
    private DataLine.Info info;
    private SourceDataLine auline;
    public Sampler(){
        format = new AudioFormat(SAMPLERATE, SAMPLESIZE, CHANNELS, SIGNED, BIGENDIAN);
        info = new DataLine.Info(SourceDataLine.class, format);
    }
    public void initSoundSystem(){
        try {
            // Get line to write data to
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);

            auline.start();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void writeSamples(byte[] b, int len) {
        auline.write(b, 0, len);
    }
    public void closeSoundSystem(){
        auline.drain();
        auline.close();
    }
}