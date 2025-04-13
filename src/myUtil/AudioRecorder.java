package myUtil;

import javax.sound.sampled.*;
import java.io.*;

public class AudioRecorder {
    private TargetDataLine line;
    private File audioFile;
    private AudioFormat format;

    public AudioRecorder() {
        format = new AudioFormat(16000, 16, 1, true, true); // Mono 16bit
    }

    public void startRecording(String filePath) throws LineUnavailableException {
        audioFile = new File(filePath);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        line = (TargetDataLine) AudioSystem.getLine(info);
        line.open(format);
        line.start();

        Thread thread = new Thread(() -> {
            try (AudioInputStream ais = new AudioInputStream(line)) {
                AudioSystem.write(ais, AudioFileFormat.Type.WAVE, audioFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void stopRecording() {
        line.stop();
        line.close();
    }

    public File getAudioFile() {
        return audioFile;
    }
}