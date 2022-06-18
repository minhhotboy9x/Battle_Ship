package sample.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    public static void play(String sound) {
        new Thread(new Runnable() {
            public void run() {
                if (AudioPlayer.isMuted()) {
                    return;
                }
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Sound.class.getResourceAsStream("../../resource/sound/" + sound + ".wav"));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();

    }
}
