package tacoguy56;
import javax.sound.sampled.*;
import java.io.*;
import java.util.Arrays;

public class WAVReader {
    private File file;
    private AudioInputStream audioInputStream;
    private WaveFormat format;
    public WAVReader(){}
    public WAVReader(File toRead) {
        file = toRead;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(toRead);
            format = new WaveFormat((int)audioInputStream.getFormat().getSampleRate(), audioInputStream.getFormat().getSampleSizeInBits(), true, audioInputStream.getFormat().getChannels());
        } catch(Exception e){
            System.out.println(e);
        }
    }
    public Wave getSample(int frameCount){
        byte[] nextSamples = new byte[frameCount*format.sampleSize/8];
        try {
            audioInputStream.read(nextSamples);
            System.out.println(Arrays.toString(nextSamples));
        } catch(Exception e){
            System.out.println("something went wrong while reading the wav file named " + file.getName() + " with exception " + e);
        }
        return Wave.byteToWave(nextSamples, format, audioInputStream.getFormat().isBigEndian());
    }
}
