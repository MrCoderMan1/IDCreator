import javax.management.ConstructorParameters;
import javax.sound.sampled.*;
import java.io.File;

public class SoundPlayer implements Runnable {
    private Clip clip;

    @ConstructorParameters("audioFile")
    public SoundPlayer(File audioFie) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioFie);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void playClip() {
        if (clip != null) {
            clip.start();
            // Wait for the clip to finish playing
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close(); // Close the clip when it finishes playing
                }
            });
        }
    }

    @Override
    public void run() {
        playClip();
    }
}