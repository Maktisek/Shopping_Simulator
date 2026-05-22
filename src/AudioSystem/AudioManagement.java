package AudioSystem;

import java.util.ArrayList;

public class AudioManagement {

    private ArrayList<Audio> music;
    private ArrayList<Audio> sounds;
    private ArrayList<Audio> background;
    private boolean mute;


    public void playSound(String title, AudioType type, long startPosition){
        final Thread playThread = new Thread(() -> {
            ArrayList<Audio> temp = getAudioListByType(type);
            if(temp != null && !mute) {
                for (Audio audio : temp) {
                    if(audio.getTitle().equalsIgnoreCase(title)){
                        audio.startAudio(startPosition);
                    }
                }
            }
        });
        playThread.start();
    }

    public void pauseSound(String title, AudioType type){
        final Thread playThread = new Thread(() -> {
            ArrayList<Audio> temp = getAudioListByType(type);
            if(temp != null && !mute) {
                for (Audio audio : temp) {
                    if(audio.getTitle().equalsIgnoreCase(title)){
                        audio.pause();
                    }
                }
            }
        });
        playThread.start();
    }

    public void resumeSound(String title, AudioType type){
        final Thread playThread = new Thread(() -> {
            ArrayList<Audio> temp = getAudioListByType(type);
            if(temp != null && !mute) {
                for (Audio audio : temp) {
                    if(audio.getTitle().equalsIgnoreCase(title)){
                        if(audio.isPaused()){
                            audio.resume();
                        }else {
                            audio.startAudio(0);
                        }
                    }
                }
            }
        });
        playThread.start();
    }

    public void stopSound(String title, AudioType type){
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

    public void initializeSounds(){
        ArrayList<Audio> temp = new ArrayList<>();
        temp.addAll(music);
        temp.addAll(sounds);
        temp.addAll(background);
        for (Audio audio : temp){
            audio.initializeAudio();
        }
    }

    public void stopAll(){
        Thread t = new Thread(() ->{
            stopAllMusic();
            stopAllBackground();
            stopAllSounds();
        });
        t.start();
    }

    private void stopAllMusic(){
        for (Audio audio : music){
            audio.stopAll();
        }
    }

    private void stopAllBackground(){
        for (Audio audio : background){
            audio.stopAll();
        }
    }

    private void stopAllSounds(){
        for (Audio audio : sounds){
            audio.stopAll();
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

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }
}
