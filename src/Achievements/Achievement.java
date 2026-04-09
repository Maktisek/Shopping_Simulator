package Achievements;

public class Achievement {

    private AchievementTypes type;
    private String name;
    private String description;
    private int bound;
    private int current;

    public Achievement() {
    }

    public void changeCurrent(int change){
        int after = this.current + change;
        this.current = Math.min(after, bound);
    }

     public boolean isDone(){
         return current >= bound;
     }

    public AchievementTypes getType() {
        return type;
    }

    public void setType(AchievementTypes type) {
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

    @Override
    public String toString() {
        return "Achievement{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", bound=" + bound +
                ", current=" + current +
                '}';
    }

    public Achievement(AchievementTypes type, int current, int bound, String description, String name) {
        this.type = type;
        this.current = current;
        this.bound = bound;
        this.description = description;
        this.name = name;
    }
}
