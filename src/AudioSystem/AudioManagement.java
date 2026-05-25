package AudioSystem;

import javax.sound.sampled.Clip;
import java.util.ArrayList;

/**
 * This class represents an audio management. All audio files are being stored here and from here they are manipulated.
 * <p>
 *     The audios are divided into two categories - {@link #music} and {@link #sounds}.
 *     {@link #mute} when it is false, then no audio can be played.
 * </p>
 * @author Matěj Pospíšil
 */
public class AudioManagement {

    private ArrayList<Audio> music;
    private ArrayList<Audio> sounds;
    private ArrayList<Audio> paused;
    private boolean mute;


    /**
     * Plays desired sound by its title.
     * <p>
     *     It searches in the audio list, which is selected by {@link AudioType} and if it
     *     founds the sound, then it is played.
     * </p>
     * <p>
     *     {@link #mute} has to be set to false in order to proceed.
     * </p>
     * @param title the audio to be played
     * @param type the audio type to be played
     * @param startPosition from where the audio should start
     */
    public void playSound(String title, AudioType type, long startPosition) {
        final Thread playThread = new Thread(() -> {
            ArrayList<Audio> temp = getAudioListByType(type);
            if (temp != null && !mute) {
                for (Audio audio : temp) {
                    if (audio.getTitle().equalsIgnoreCase(title)) {
                        audio.startAudio(startPosition);
                    }
                }
            }
        });
        playThread.start();
    }

    /**
     * Pauses desired sound by its title.
     * <p>
     *     It searches in the audio list, which is selected by {@link AudioType} and if it
     *     founds the sound, it is paused.
     * </p>
     * <p>
     *     {@link #mute} has to be set to false in order to proceed.
     * </p>
     * @param title the audio to be paused
     * @param type the audio type to be paused
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
     *     It searches in the audio list, which is selected by {@link AudioType} and if it
     *     founds the sound, it is resumed.
     * </p>
     * <p>
     *     {@link #mute} has to be set to false in order to proceed.
     * </p>
     * @param title the audio to be resumed
     * @param type the audio type to be resumed
     */
    public void resumeSound(String title, AudioType type) {
        final Thread playThread = new Thread(() -> {
            ArrayList<Audio> temp = getAudioListByType(type);
            if (temp != null && !mute) {
                for (Audio audio : temp) {
                    if (audio.getTitle().equalsIgnoreCase(title)) {
                        if (audio.isPaused()) {
                            audio.resume();
                        } else {
                            audio.startAudio(0);
                        }
                    }
                }
            }
        });
        playThread.start();
    }

    /**
     * Stops desired sound by its title.
     * <p>
     *     It searches in the audio list, which is selected by {@link AudioType} and if it
     *     founds the sound, it is stopped.
     * </p>
     * <p>
     *     {@link #mute} has to be set to false in order to proceed.
     * </p>
     * @param title the audio to be paused
     * @param type the audio type to be paused
     */
    public void stopSound(String title, AudioType type) {
        final Thread playThread = new Thread(() -> {
            ArrayList<Audio> temp = getAudioListByType(type);
            if (temp != null && !mute) {
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
        Thread t = new Thread(() ->{
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
}
