package Achievements;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents a management of achievements. It holds all achievements and manipulates with them.
 * All achievements have to cycle through various collections.
 * <p>
 * {@link #freshAchievements} stands for newly achieved achievements
 * </p>
 * {@link #doneAchievements} stands for all done achievements
 * <p>
 * {@link #loadedAchievements} all achievements are firstly loaded here so they can be distributed
 * </p>
 * {@link #possibleAchievements} stands for all unfinished achievements
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class AchievementManagement implements Serializable {

    private final Queue<Achievement> freshAchievements;
    private final HashMap<AchievementType, ArrayList<Achievement>> possibleAchievements;
    private final ArrayList<Achievement> doneAchievements;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final ArrayList<Achievement> loadedAchievements;

    public AchievementManagement() {
        this.freshAchievements = new LinkedList<>();
        this.possibleAchievements = new HashMap<>();
        this.loadedAchievements = new ArrayList<>();
        this.doneAchievements = new ArrayList<>();
    }

    public void loadPossibleAchievements() {
        for (int i = 0; i < AchievementType.values().length; i++) {
            AchievementType currentType = AchievementType.values()[i];
            this.possibleAchievements.put(currentType, findInLoadedAchievements(currentType));
        }
    }

    private ArrayList<Achievement> findInLoadedAchievements(AchievementType type) {
        ArrayList<Achievement> result = new ArrayList<>();
        for (Achievement achievement : loadedAchievements) {
            if (achievement.getType() == type) {
                result.add(achievement);
            }
        }
        return result;
    }

    /**
     * Goes through all achievements and decides which ones are done. If it founds done achievement
     * then it is added into {@link #freshAchievements} and {@link #doneAchievements}
     */
    private void executeDoneAchievements() {
        for (AchievementType key : possibleAchievements.keySet()) {
            ArrayList<Achievement> possible = possibleAchievements.get(key);
            for (Achievement achievement : possible) {
                if (achievement.isDone() && !doneAchievements.contains(achievement)) {
                    freshAchievements.add(achievement);
                    doneAchievements.add(achievement);
                }
            }
        }
    }

    /**
     * Checks if there is any unclaimed achievement among all {@link #possibleAchievements}
     * The game cannot end if there is any unclaimed achievement.
     * @return true if yes, false if not
     */
    public boolean checkForUnclaimed() {
        for (AchievementType key : possibleAchievements.keySet()) {
            ArrayList<Achievement> possible = possibleAchievements.get(key);
            for (Achievement achievement : possible) {
                if (achievement.isDone() && achievement.getReward() != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Updates {@code current} of any achievement of given type.
     * @param type the type of achievements to be updated
     * @param change the amount of which is the {@code current} of all found achievements incremented
     */
    public void updateAchievement(AchievementType type, long change) {
        ArrayList<Achievement> temp = possibleAchievements.get(type);
        if (temp != null) {
            for (Achievement achievement : temp) {
                achievement.changeCurrent(change);
            }
            executeDoneAchievements();
        }
    }

    public void pollAchievement() {
        if (!freshAchievements.isEmpty()) {
            this.freshAchievements.poll();
        }
    }

    public Achievement peekAchievement() {
        if (!freshAchievements.isEmpty()) {
            return this.freshAchievements.peek();
        } else {
            return null;
        }
    }
    public HashMap<AchievementType, ArrayList<Achievement>> getPossibleAchievements() {
        return possibleAchievements;
    }
}
