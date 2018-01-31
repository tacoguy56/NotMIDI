package tacoguy56;
import java.io.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Sampler aa = new Sampler();
        aa.initSoundSystem();
        //WaveSource sine = new SineGenerator();
        //Wave b = sine.getWaveForm(440, 5, 1);
        //b = new Distortion().processWave(b);
        WAVReader miiJazzTheme = new WAVReader(new File("C:\\Users\\Grant Martin\\Downloads\\Nintendo Wii - Mii Channel Theme - Jazz Cover  insaneintherainmusic feat Gabe N  Chris A.wav"));
        //byte[] processedWave = b.convertToBytes(Sampler.BIGENDIAN);
        System.out.println(Arrays.toString(miiJazzTheme.getSample(22050).convertToBytes(Sampler.BIGENDIAN)));
	    aa.closeSoundSystem();
    }
}
