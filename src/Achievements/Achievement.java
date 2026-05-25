package Achievements;

import java.io.Serializable;

/**
 * This class represents an achievement. The game calls it "goal" because of space limitation.
 * <p>
 *     Every single achievement has its own type which is represented by {@link AchievementType} enum.
 *     The achievement holds its current state and its bound. If {@code current} equals, or it is larger than {@code bound},
 *     then the achievement is marked as done.
 * </p>
 * @author Matěj Pospíšil
 */
public class Achievement implements Serializable {

    private AchievementType type;
    private String name;
    private String description;
    private int bound;
    private int current;
    private int reward;

    public Achievement() {
    }

    public void changeCurrent(int change) {
        int after = this.current + change;
        this.current = Math.min(after, bound);
    }

    public int returnReward(){
        int preReward = this.reward;
        this.reward = 0;
        return preReward;
    }

    public int calculatePercent(){
        return (int) (((float) current / (float) bound) * 100);
    }

    public boolean isDone() {
        return current >= bound;
    }

    public AchievementType getType() {
        return type;
    }

    public void setType(AchievementType type) {
        this.type = type;
    }

    public int getBound() {
        return bound;
    }

    public void setBound(int bound) {
        this.bound = bound;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }
}
