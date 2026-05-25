package AudioSystem;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * This class stands for playing individual wav audio files. Those files have to be implemented into res//Audio//Music or Sounds.
 * It uses Clip interface, which is responsible for the whole audio system logic and implements it in audio pool system.
 * This class was originally taken from my game "Last Memory", but it went through deep changes (linear volume, better fading and sound pool system).
 * <p>
 * {@link #filePath} stands for path, from where the .wav file can be loaded. <p>
 * {@link #initialVolume} stands for the volume of the whole audio clip (0.0 - 1.0) <p>
 * {@link #paused} is true if the audio is currently paused. <p>
 * {@link #shifting} is true when the audio is able to slowly shift the volume.<p>
 * {@link #clips} stands for audio pool, since one clip can be played just once in a moment, it is needed to have more individual clips of same sound.
 * <p>
 * Used Stack Overflow to help me understand the whole decibels into linearity transfer system.
 * Used Gemini to help me find bugs and fix issues.
 *
 * @author Matěj Pospíšil, Gemini, Tim (Cool dude on Stack Overflow)
 */
public class Audio {

    private String filePath;
    private String title;
    private boolean music;
    private int soundPool;

    private Clip currentClip;
    private ArrayList<Clip> clips;
    private boolean infiniteLoop;
    private long pausePosition;
    private boolean paused;
    private float initialVolume;
    private boolean shifting;

    public Audio() {
    }

    /**
     * This method implements an audio. It creates {@link #soundPool} amount of clips and loads them with wav file located on {@link #filePath}.
     * Then it uses {@link #initializeLoop(boolean)} to initialize the loop of all clips in the pool.
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
                setVolume(this.initialVolume, clip);
                clips.add(clip);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        initializeLoop(infiniteLoop);
    }

    /**
     * Starts an audio via thread.
     *
     * @param startPosition stands for from where the sound should start
     */
    public void startAudio(long startPosition) {
        startClip(startPosition);
    }

    /**
     * Finds first non-running clip and returns it.
     *
     * @return non-running clip if there is, if not then it returns null
     */
    public Clip findClip() {
        for (Clip clip : clips) {
            if (!clip.isRunning()) {
                return clip;
            }
        }
        return null;
    }

    /**
     * Method which pauses {@link #currentClip}.
     * There is a boolean value {@link #paused} which holds an information if the audio is paused.
     * This value has to be false in order to proceed. Also, the {@link #currentClip} has not to be null. If it's null then it can't be stopped.
     * <p>
     * It shifts {@link #shifting} to false - this is done, because the fading should be stopped (otherwise it gets very buggy).
     * </p>
     * <p>
     * If more sounds from {@link #clips} are playing, then it pauses the last one played.
     * </p>
     * Sets {@link #pausePosition} to current microsecond position, so the audio can be resumed later.
     */
    public void pause() {
        if (currentClip != null && !paused) {
            pausePosition = currentClip.getMicrosecondPosition();
            paused = true;
            shifting = false;
            currentClip.stop();
        }
    }

    /**
     * Method which resumes the {@link #currentClip}
     * There is a boolean value {@link #paused} which holds an information if the audio is paused.
     * This value has to be true in order to proceed. Also, {@link #currentClip} has to be initialized and set. If it's null then it can't be resumed.
     * <p>
     * Uses {@link #pausePosition} to set new current microsecond position, from which the audio will start.
     * <p>
     * Shifts {@link #shifting} to true so the {@link #fadeIn(long)} can be executed.
     * </p>
     * <p>
     * Uses {@link #fadeIn(long)} method for cleaner transition.
     * </p>
     */
    public void resume() {
        if (currentClip != null && paused) {
            Thread t = new Thread(() -> {
                currentClip.setMicrosecondPosition(pausePosition);
                paused = false;
                shifting = true;
                if (currentClip != null) {
                    fadeIn(20);
                }
            });
            t.start();
        }
    }

    /**
     * Method which stops {@link #currentClip}.
     * <p>
     * It does not close the clip, so it can be replayed again in the future.
     * </p>
     */
    public void stopSound() {
        if (currentClip != null) {
            currentClip.stop();
            shifting = false;
        }
    }

    /**
     * Stops all clips in {@link #clips}.
     * <p>
     * Great for being sure that no wrong sound will be playing in the background.
     * </p>
     */
    public void stopAll() {
        for (Clip clip : clips) {
            clip.stop();
        }
    }

    /**
     * Sets the volume into desired value. It transfers the linear value into decibels.
     *
     * @param value the desired value (interval from 0.0001 to 1.0 included)
     * @param clip  the clip whose volume will be changed - via {@link #setVolumeDB(float, Clip)}
     */
    private void setVolume(double value, Clip clip) {
        value = (value <= 0.0) ? 0.0001 : (Math.min(value, 1.0));
        float dB = (float) (Math.log(value) / Math.log(10.0) * 20.0);
        setVolumeDB(dB, clip);
    }

    /**
     * Shifts slowly the volume from current to desired.
     * <p>
     * It represents fade in/out - it can do both.
     * </p>
     *
     * @param desiredValue the desired value (interval from 0.0001 to 1.0 included)
     * @param clip         the clip whose volume will be slowly changed
     * @param milliSeconds represents the time between tweaks - recommended are 20 milliseconds
     */
    private void shiftVolume(double desiredValue, Clip clip, long milliSeconds) {
        desiredValue = (desiredValue <= 0.0) ? 0.0001 : (Math.min(desiredValue, 1.0));
        float targetDB = (float) (Math.log(desiredValue) / Math.log(10.0) * 20.0);
        FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float currDB = gain.getValue();
        float fadePerStep = .1F;

        if (currDB > targetDB) {
            if (!paused) {
                while (currDB > targetDB && shifting) {
                    currDB -= fadePerStep;
                    gain.setValue(currDB);
                    try {
                        Thread.sleep(milliSeconds);
                    } catch (Exception ignored) {
                    }
                }
            }
        } else if (currDB < targetDB) {
            if (!paused) {
                while (currDB < targetDB && shifting) {
                    currDB += fadePerStep;
                    gain.setValue(currDB);
                    try {
                        Thread.sleep(milliSeconds);
                    } catch (Exception ignored) {
                    }
                }
            }
        }
    }

    /**
     * Fades in sound via {@link #shiftVolume(double, Clip, long)}
     * <p>
     * It sets the options of the {@link #currentClip} so it can be faded in.
     * </p>
     *
     * @param milliSeconds represents the time between tweaks - recommended are 20 milliseconds
     */
    public void fadeIn(long milliSeconds) {
        setVolume(initialVolume - 0.4, currentClip);
        currentClip.start();
        shiftVolume(initialVolume, currentClip, milliSeconds);
    }

    /**
     * Set loop of all clips in {@link #clips} if requested.
     * Uses addLineListener method to attach new lineListener.
     * The listener reacts to STOP events by restarting the audio.
     * <p>
     * If the event.getType() is LineEvent.Type.STOP then it checks if the STOP state was because of end of the audio file.
     * If yes, then it resets the audio via setting the microsecond position to 0 and uses {@link #fadeIn(long)}
     * <p>
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
                            fadeIn(20);
                        }
                    }
                });
            }
        }
    }

    /**
     * Finds non-running clip via {@link #findClip()} and starts it.
     * <p>
     * If {@link #music} is true and {@code startPosition} is 0 then {@link #fadeIn(long)} is used, otherwise it is just started and the volume is set to {@link #initialVolume}.
     * </p>
     *
     * @param startPosition from where the audio should start
     */
    private void startClip(long startPosition) {
        this.currentClip = findClip();
        Thread t = new Thread(() -> {
            if (currentClip != null) {
                currentClip.setMicrosecondPosition(startPosition);
                shifting = true;
                if (music && startPosition == 0) {
                    fadeIn(20);
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
     * {@code clip} has to be initialized, also it has to support the MASTER_GAIN FloatControl.
     * <p>
     * Gets the clip’s MASTER_GAIN FloatControl and sets its value.
     *
     * @param db requested volume in decibels (-80.0 to cca 6.0 accepted)
     * @param clip the clip whose volume will be set
     */
    public void setVolumeDB(float db, Clip clip) {
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gain.setValue(db);
        }
    }

    /**
     * Closes all clips.
     * <p>
     *     They cannot be replayed later on (great for closing the game, because it will destroy all clip threads in the memory)
     * </p>
     */
    public void closeAllClips() {
        for (Clip clip : clips) {
            this.shifting = false;
            clip.stop();
            clip.close();
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
