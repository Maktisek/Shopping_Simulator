package Achievements;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class AchievementManagement implements Serializable {

    private Queue<Achievement> freshAchievements;
    private HashMap<AchievementTypes, ArrayList<Achievement>> possibleAchievements;
    private ArrayList<Achievement> loadedAchievements;

    public AchievementManagement() {
        this.freshAchievements = new LinkedList<>();
        this.possibleAchievements = new HashMap<>();
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
            for (Achievement achievement : possible){
                if (achievement.isDone()){
                    freshAchievements.add(achievement);
                }
            }
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

    public Achievement pollAchievement(){
       if(!freshAchievements.isEmpty()){
           return this.freshAchievements.poll();
       }else {
           return null;
       }
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

    public Queue<Achievement> getFreshAchievements() {
        return freshAchievements;
    }

    public void setFreshAchievements(Queue<Achievement> freshAchievements) {
        this.freshAchievements = freshAchievements;
    }
}
