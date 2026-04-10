package Achievements;


import java.util.ArrayList;
import java.util.HashMap;

public class AchievementManagement {

    private ArrayList<Achievement> freshAchievements;
    private HashMap<AchievementTypes, ArrayList<Achievement>> possibleAchievements;
    private HashMap<AchievementTypes, ArrayList<Achievement>> doneAchievements;
    private ArrayList<Achievement> loadedAchievements;

    public AchievementManagement() {
        this.freshAchievements = new ArrayList<>();
        this.possibleAchievements = new HashMap<>();
        this.doneAchievements = new HashMap<>();
        this.loadedAchievements = new ArrayList<>();
    }

    public void loadPossibleAchievements(){
        for (int i = 0; i < AchievementTypes.values().length; i++) {
            AchievementTypes currentType = AchievementTypes.values()[i];
            this.possibleAchievements.put(currentType, findInLoadedAchievements(currentType));
        }
    }

    private ArrayList<Achievement> findInLoadedAchievements(AchievementTypes type){
        ArrayList<Achievement> result = new ArrayList<>();
        for (Achievement achievement : loadedAchievements){
            if(achievement.getType() == type){
                result.add(achievement);
            }
        }
        return result;
    }

    private void executeDoneAchievements(){
        for (AchievementTypes key: possibleAchievements.keySet()){
            ArrayList<Achievement> possible = possibleAchievements.get(key);

            ArrayList<Achievement> done = new ArrayList<>();
            for (Achievement achievement : possible){
                if (achievement.isDone()){
                    freshAchievements.add(achievement);
                    done.add(achievement);
                }
            }

            doneAchievements.computeIfAbsent(key, k -> new ArrayList<>()).addAll(done);

            possible.removeIf(Achievement::isDone);
        }
    }

    public void updateAchievement(AchievementTypes type, int change) {
        ArrayList<Achievement> temp = possibleAchievements.get(type);
        if (temp != null) {
            for (Achievement achievement : temp) {
                achievement.changeCurrent(change);
            }
            executeDoneAchievements();
        }
    }

    public void clearFreshAchievements(){
        this.freshAchievements.clear();
    }

    public ArrayList<Achievement> getLoadedAchievements() {
        return loadedAchievements;
    }

    public void setLoadedAchievements(ArrayList<Achievement> loadedAchievements) {
        this.loadedAchievements = loadedAchievements;
    }

    public HashMap<AchievementTypes, ArrayList<Achievement>> getPossibleAchievements() {
        return possibleAchievements;
    }

    public void setPossibleAchievements(HashMap<AchievementTypes, ArrayList<Achievement>> possibleAchievements) {
        this.possibleAchievements = possibleAchievements;
    }

    public HashMap<AchievementTypes, ArrayList<Achievement>> getDoneAchievements() {
        return doneAchievements;
    }

    public void setDoneAchievements(HashMap<AchievementTypes, ArrayList<Achievement>> doneAchievements) {
        this.doneAchievements = doneAchievements;
    }

    public ArrayList<Achievement> getFreshAchievements() {
        return freshAchievements;
    }

    public void setFreshAchievements(ArrayList<Achievement> freshAchievements) {
        this.freshAchievements = freshAchievements;
    }

    @Override
    public String toString() {
        return "AchievementManagement{" +
                "possibleAchievements=" + possibleAchievements +
                ", doneAchievements=" + doneAchievements +
                ", loadedAchievements=" + loadedAchievements +
                '}';
    }
}
