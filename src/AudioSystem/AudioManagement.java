package AudioSystem;

import javax.sound.sampled.Clip;
import java.util.ArrayList;
import java.util.Queue;

/**
 * This class represents an audio management. All audio files are being stored here and from here they are manipulated.
 * <p>
 * The audios are divided into two categories - {@link #music} and {@link #sounds}.
 * {@link #mute} when it is false, then no audio can be played.
 * </p>
 * Also, {@link #queue} stacks all sounds that should be played, but were not, because {@link #mute} is false.
 * When the player then unmutes the game all audios in this queue will be polled and played.
 * <p>
 * Sometimes it is needed to clear the queue. Especially when the environment of the game changes. No one wants old sounds to be played
 * on a place where they do not belong.
 * </p>
 * @author Matěj Pospíšil
 */
public class AudioManagement {

    private ArrayList<Audio> music;
    private ArrayList<Audio> sounds;
    private ArrayList<Audio> paused;
    private Queue<Audio> queue;
    private boolean mute;


    /**
     * Plays desired sound by its title.
     * <p>
     * It searches in the audio list, which is selected by {@link AudioType} and if it
     * founds the sound, then it is played.
     * </p>
     * <p>
     * {@link #mute} has to be set to false in order to proceed.
     * </p>
     *
     * @param title         the audio to be played
     * @param type          the audio type to be played
     * @param startPosition from where the audio should start
     */
    public void playSound(String title, AudioType type, long startPosition) {
        final Thread playThread = new Thread(() -> {
            ArrayList<Audio> temp = getAudioListByType(type);
            if (temp != null) {
                for (Audio audio : temp) {
                    if (audio.getTitle().equalsIgnoreCase(title)) {
                        if (!mute) {
                            audio.startAudio(startPosition);
                        } else {
                            addToQueue(title, type);
                        }
                    }
                }
            }
        });
        playThread.start();
    }

    /**
     * Pauses desired sound by its title.
     * <p>
     * It searches in the audio list, which is selected by {@link AudioType} and if it
     * founds the sound, it is paused.
     * </p>
     * <p>
     * {@link #mute} has to be set to false in order to proceed.
     * </p>
     *
     * @param title the audio to be paused
     * @param type  the audio type to be paused
     */
    public void pauseSound(String title, AudioType type) {
        final Thread playThread = new Thread(() -> {
            ArrayList<Audio> temp = getAudioListByType(type);
            if (temp != null && !mute) {
                for (Audio audio : temp) {
                    if (audio.getTitle().equalsIgnoreCase(title)) {
                        audio.pause();
                    }
                }
            }
        });
        playThread.start();
    }

    /**
     * Resumes desired sound by its title.
     * <p>
     * It searches through the audio list, which is selected by {@link AudioType}, and if it
     * founds the sound, those actions may be done:
     * </p>
     * <p>
     * If it is paused it is resumed, if it is not paused and {@link #mute} is {@code true} then it is played from the start or
     * if the {@link #mute} is {@code false}, it is not paused and {@code add} is {@code true}, then it is added into {@link #queue} so it can be replayed later.
     * </p>
     *
     * @param title the audio to be resumed
     * @param type  the audio type to be resumed
     * @param add   decides if the audio can be added into {@link Queue}
     */
    public void resumeSound(String title, AudioType type, boolean add) {
        final Thread playThread = new Thread(() -> {
            ArrayList<Audio> temp = getAudioListByType(type);
            if (temp != null) {
                for (Audio audio : temp) {
                    if (audio.getTitle().equalsIgnoreCase(title)) {
                        if (audio.isPaused() && !mute) {
                            audio.resume();
                        } else {
                            if (!mute) {
                                audio.startAudio(0);
                            } else if (add) {
                                addToQueue(title, type);
                            }
                        }
                    }
                }
            }
        });
        playThread.start();
    }

    /**
     * Sets {@code boolean paused} for audios to {@code false}. It is done, because if the audio muted and then unmuted it should
     * resume all sounds. But because the system is a little bit complicated, some sounds are already gone, and they are not needed.
     * Their pause status still remains {@code true}, but when it is replayed again, it should be again {@code false}.
     * <p>
     *     This method is being run anytime the environment changes.
     * </p>
     */
    public void prepareForLoad() {
        Thread t = new Thread(() -> {
            paused.clear();
            for (Audio audio : music) {
                audio.setPaused(false);
            }
        });
        t.start();
    }

    /**
     * Stops desired sound by its title.
     * <p>
     * It searches in the audio list, which is selected by {@link AudioType} and if it
     * founds the sound, it is stopped.
     * </p>
     *
     * @param title the audio to be paused
     * @param type  the audio type to be paused
     */
    public void stopSound(String title, AudioType type) {
        final Thread playThread = new Thread(() -> {
            ArrayList<Audio> temp = getAudioListByType(type);
            if (temp != null) {
                for (Audio audio : temp) {
                    if (audio.getTitle().equalsIgnoreCase(title)) {
                        audio.stopSound();
                    }
                }
            }
        });
        playThread.start();
    }

    /**
     * Initializes all audios via {@link Audio#initializeAudio()}
     */
    public void initializeSounds() {
        ArrayList<Audio> temp = new ArrayList<>();
        temp.addAll(music);
        temp.addAll(sounds);
        for (Audio audio : temp) {
            audio.initializeAudio();
        }
    }

    /**
     * Stops all audios
     */
    public void stopAll() {
        Thread t = new Thread(() -> {
            stopAllMusic();
            stopAllSounds();
        });
        t.start();
    }

    /**
     * Pauses all music.
     */
    public void pauseAllMusic() {
        Thread t = new Thread(() -> {
            for (Audio audio : music) {
                Clip clip = audio.getCurrentClip();
                if (clip != null && clip.isRunning()) {
                    audio.pause();
                    paused.add(audio);
                }
            }
        });
        t.start();
    }

    /**
     * Resumes all audios from {@link #paused}
     */
    public void resumeAll() {
        Thread t = new Thread(() -> {
            for (Audio audio : paused) {
                audio.resume();
            }
            paused.clear();
        });
        t.start();
    }

    private void stopAllMusic() {
        for (Audio audio : music) {
            audio.stopAll();
        }
    }

    private void stopAllSounds() {
        for (Audio audio : sounds) {
            audio.stopAll();
        }
    }

    /**
     * Closes all clips
     */
    public void closeAll() {
        Thread t = new Thread(() -> {
            ArrayList<Audio> temp = new ArrayList<>();
            temp.addAll(music);
            temp.addAll(sounds);
            for (Audio audio : temp) {
                audio.closeAllClips();
            }
        });
        t.start();
    }


    private ArrayList<Audio> getAudioListByType(AudioType type) {
        switch (type) {
            case MUSIC -> {
                return music;
            }
            case SOUNDS -> {
                return sounds;
            }
        }
        return null;
    }

    /**
     * This method finds and adds an audio into {@link #queue}. If the audio was not found
     * anything happens.
     *
     * @param title the audio to be added
     * @param type  the type of the audio to be added
     */
    public void addToQueue(String title, AudioType type) {
        final Thread playThread = new Thread(() -> {
            ArrayList<Audio> temp = getAudioListByType(type);
            if (temp != null) {
                for (Audio audio : temp) {
                    if (audio.getTitle().equalsIgnoreCase(title)) {
                        queue.add(audio);
                    }
                }
            }
        });
        playThread.start();
    }

    /**
     * Polls and plays all audios from {@link #queue}
     */
    public void pollFromQueue() {
        final Thread playThread = new Thread(() -> {
            while (!queue.isEmpty()) {
                Audio audio = queue.poll();
                audio.startAudio(0);
            }
        });
        playThread.start();
    }

    public ArrayList<Audio> getMusic() {
        return music;
    }

    public ArrayList<Audio> getSounds() {
        return sounds;
    }

    public void setMusic(ArrayList<Audio> music) {
        this.music = music;
    }

    public void setSounds(ArrayList<Audio> sounds) {
        this.sounds = sounds;
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public ArrayList<Audio> getPaused() {
        return paused;
    }

    public void setPaused(ArrayList<Audio> paused) {
        this.paused = paused;
    }

    public Queue<Audio> getQueue() {
        return queue;
    }

    public void setQueue(Queue<Audio> queue) {
        this.queue = queue;
    }
}
