package sample.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer implements Runnable {
    private static boolean muted = false;

    public static boolean isMuted() {
        return muted;
    }

    public static void setMuted(boolean muted) {
        AudioPlayer.muted = muted;
    }

    private Clip clip;

    public AudioPlayer(String sound) {
        try {
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    AudioPlayer.class.getResourceAsStream("../../resource/sound/" + sound + ".wav"));

            clip.open(inputStream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void loop() {
        if (!muted) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    @Override
    public void run() {
        this.loop();
    }

    public void stop() {
        clip.stop();
    }

    public void mute() {
        muted = !muted;
        if (muted) {
            this.stop();
        } else {
            this.loop();
        }
    }
}