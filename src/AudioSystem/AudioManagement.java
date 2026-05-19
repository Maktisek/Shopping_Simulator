package AudioSystem;

import java.util.ArrayList;

public class AudioManagement {

    private ArrayList<Audio> music;
    private ArrayList<Audio> sounds;
    private ArrayList<Audio> background;


    public void playSound(String title, AudioType type, long startPosition){
        ArrayList<Audio> temp = getAudioListByType(type);
        if(temp != null) {
            for (Audio audio : temp) {
                if(audio.getTitle().equalsIgnoreCase(title)){
                    audio.startAudio(startPosition);
                }
            }
        }
    }

    public void stopSound(String title, AudioType type){
        ArrayList<Audio> temp = getAudioListByType(type);
        if(temp != null) {
            for (Audio audio : temp) {
                if(audio.getTitle().equalsIgnoreCase(title)){
                    audio.stopSound();
                }
            }
        }
    }

    public void pauseSound(String title, AudioType type){
        ArrayList<Audio> temp = getAudioListByType(type);
        if(temp != null) {
            for (Audio audio : temp) {
                if(audio.getTitle().equalsIgnoreCase(title)){
                    audio.pause();
                }
            }
        }
    }

    public void resumeSound(String title, AudioType type){
        ArrayList<Audio> temp = getAudioListByType(type);
        if(temp != null) {
            for (Audio audio : temp) {
                if(audio.getTitle().equalsIgnoreCase(title)){
                    audio.resume();
                }
            }
        }
    }

    private ArrayList<Audio> getAudioListByType(AudioType type){
        switch (type){
            case MUSIC -> {
                return music;
            }
            case SOUNDS -> {
                return sounds;
            }
            case BACKGROUND -> {
                return background;
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

    public ArrayList<Audio> getBackground() {
        return background;
    }

    public void setMusic(ArrayList<Audio> music) {
        this.music = music;
    }

    public void setSounds(ArrayList<Audio> sounds) {
        this.sounds = sounds;
    }

    public void setBackground(ArrayList<Audio> background) {
        this.background = background;
    }

    @Override
    public String toString() {
        return String.valueOf(music.size());
    }
}
