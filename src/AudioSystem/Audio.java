package AudioSystem;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * This class stands for playing individual wav audio files. Those files have to be implemented into vložte cestu.
 * It uses Clip interface, which is responsible for the whole audio system logic.
 * <p>
 * {@link #filePath} stands for path, from where the .wav file can be loaded. <p>
 * {@link #initialVolume} stands for the volume of the whole audio clip. <p>
 * {@link #paused} is true if the audio is currently paused and false if not. <p>
 * <p>
 * The concept of this whole class was taken from Matěj Chaloupka. But I made tons of changes during the time.
 * Used ChatGPT to help me understand whole the Clip interface.
 *
 * @author Matěj Pospíšil, Matěj Chaloupka, ChatGPT
 */
public class Audio {

    private String filePath;
    private String title;
    private boolean music;
    private int soundPool;

    private transient Clip currentClip;
    private ArrayList<Clip> clips;
    private boolean infiniteLoop;
    private transient long pausePosition;
    private transient boolean paused;
    private float initialVolume;

    public Audio() {
    }

    /**
     * This method implements an audio. It creates a clip and loads it with .wav file from {@link #filePath}.
     * Then it uses {@link #initializeLoop(boolean)} to start looping the audio.
     * <p>
     * This system was originally taken from Matěj Chaloupka, but implemented in a different way.
     *
     */
    public void initializeAudio() {
        this.clips = new ArrayList<>();
        try {
            for (int i = 0; i < soundPool; i++) {
                InputStream input = Audio.class.getResourceAsStream(this.filePath);
                if (input == null) {
                    throw new RuntimeException("Audio file " + title + " not found!");
                }
                final AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(input));
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                setVolume(this.initialVolume - 15, clip);
                clips.add(clip);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        initializeLoop(infiniteLoop);
    }

    public Clip findClip() {
        for (Clip clip : clips) {
            if (!clip.isRunning()) {
                return clip;
            }
        }
        return null;
    }

    /**
     * Method which starts a sound or music via thread.
     * The clip has not to be null to play.
     * <p>
     *
     * @param startPosition stands for from where the sound should start
     */
    public void startAudio(long startPosition) {
        startClip(startPosition);
    }

    /**
     * Method which stops the sound.
     */
    public void stopSound() {
        if (currentClip != null) {
            currentClip.loop(0);
            currentClip.setMicrosecondPosition(0);
            currentClip.stop();
        }
    }

    /**
     * Method which pauses the audio.
     * There is a boolean value {@link #paused} which holds an information if the audio is paused.
     * This value has to be false in order to proceed. Also, the clip has to be initialized. If it's null then it can't be stopped.
     * <p>
     * Sets {@link #pausePosition} to current microsecond position, so the audio can be resumed later.
     */
    public void pause() {
        if (currentClip != null && !paused) {
            pausePosition = currentClip.getMicrosecondPosition();
            paused = true;
            currentClip.stop();
        }
    }

    /**
     * Method which resumes the audio
     * There is a boolean value {@link #paused} which holds an information if the audio is paused.
     * This value has to be true in order to proceed. Also, the clip has to be initialized. If it's null then it can't be resumed.
     * <p>
     * Uses {@link #pausePosition} to save current microsecond position, so the audio can be resumed later, where it stopped.
     * <p>
     * Uses {@link #fadeIn(long)}} method for cleaner transition.
     */
    public void resume() {
        if (currentClip != null && paused) {
            Thread t = new Thread(() -> {
                currentClip.setMicrosecondPosition(pausePosition);
                fadeIn(20);
                paused = false;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (currentClip != null) {
                    currentClip.start();
                }
            });
            t.start();
        }
    }

    /**
     * Loops the audio if requested.
     * Uses {@link #currentClip} method addLineListener to attach new lineListener.
     * The listener reacts to STOP events by restarting the audio.
     * <p>
     * If the event.getType() is LineEvent.Type.STOP then it checks if the STOP state was because of end of the audio file.
     * If yes, then it resets the audio via setting the microsecond position to 0.
     * <p>
     * Also, starts the audio initially, do not call clip.start() before this method.
     *
     * @param loop is true if the audio should be looped.
     */
    public void initializeLoop(boolean loop) {
        for (Clip clip : clips) {
            if (loop) {
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        if (clip.getMicrosecondPosition() >= clip.getMicrosecondLength()) {
                            clip.setMicrosecondPosition(0);
                            clip.start();
                        }
                    }
                });
            }
        }
    }

    private void startClip(long startPosition) {
        this.currentClip = findClip();
        Thread t = new Thread(() -> {
            if (currentClip != null) {
                currentClip.setMicrosecondPosition(startPosition);
                if (music) {
                    if (startPosition != 0) {
                        setVolume(this.initialVolume, this.currentClip);
                        currentClip.start();
                    } else {
                        setVolume(-50, this.currentClip);
                        fadeIn(20);
                    }
                } else {
                    setVolume(this.initialVolume, this.currentClip);
                    currentClip.start();
                }
            }
        });
        t.start();
    }

    /**
     * Sets the desired volume in decibels.
     * <p>
     * Uses FloatControl.TYPE.MASTER_GAIN to control volume gain.
     * The clip has to be initialized and the clip has to support the MASTER_GAIN FloatControl.
     * <p>
     * Gets the clip’s MASTER_GAIN FloatControl and sets its value.
     *
     * @param db requested volume in decibels (-80.0 to cca 6.0 accepted)
     */
    public void setVolume(float db, Clip clip) {
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gain.setValue(db);
        }
    }

    /**
     * Fades in audio from {@link #initialVolume} - 10 to {@link #initialVolume}.
     * Uses {@link #setVolume(float, Clip)}} to set the current volume level.
     *
     * @param milliseconds the desired time that the thread will wait until updating the volume again.
     *                     More millisecond the more time the fade will take, but the less will be cleaner.
     */
    public void fadeIn(long milliseconds) {
        float penalization = 0;
        float penalizationStep = 0.12f;
        float steps = 100;
        float start = this.initialVolume - 10;
        float end = this.initialVolume + (penalizationStep * steps);
        float stepSize = (end - start) / steps;


        currentClip.start();
        for (float f = 0; f <= steps; f++) {
            setVolume(start + (stepSize * f) - penalization, this.currentClip);
            if (f == 0) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            penalization += penalizationStep;
        }
    }


    public String getFilePath() {
        return filePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPausePosition() {
        return pausePosition;
    }

    public void setPausePosition(long pausePosition) {
        this.pausePosition = pausePosition;
    }

    public Clip getCurrentClip() {
        return currentClip;
    }

    public void setCurrentClip(Clip currentClip) {
        this.currentClip = currentClip;
    }

    public boolean isInfiniteLoop() {
        return infiniteLoop;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setInfiniteLoop(boolean infiniteLoop) {
        this.infiniteLoop = infiniteLoop;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public float getInitialVolume() {
        return initialVolume;
    }

    public void setInitialVolume(float initialVolume) {
        this.initialVolume = initialVolume;
    }

    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }

    public int getSoundPool() {
        return soundPool;
    }

    public void setSoundPool(int soundPool) {
        this.soundPool = soundPool;
    }

    public ArrayList<Clip> getClips() {
        return clips;
    }

    public void setClips(ArrayList<Clip> clips) {
        this.clips = clips;
    }
}
