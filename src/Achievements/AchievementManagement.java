package Achievements;


import java.util.ArrayList;
import java.util.HashMap;

public class AchievementManagement {

    private HashMap<AchievementTypes, ArrayList<Achievement>> possibleAchievements;
    private HashMap<AchievementTypes, ArrayList<Achievement>> doneAchievements;
    private ArrayList<Achievement> loadedAchievements;

    public AchievementManagement() {
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
}
