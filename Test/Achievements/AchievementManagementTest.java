package Achievements;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AchievementManagementTest {

    AchievementManagement achievementManagement;

    @Before
    public void setUp() throws Exception {
        achievementManagement = new AchievementManagement();
        achievementManagement.getLoadedAchievements().add(new Achievement(AchievementTypes.BUY, 0, 10, "Buy 10 products", "My first job"));
        achievementManagement.getLoadedAchievements().add(new Achievement(AchievementTypes.BUY, 0, 100, "Buy 100 products", "I am feeling great"));
        achievementManagement.getLoadedAchievements().add(new Achievement(AchievementTypes.BUY, 0, 1000, "Buy 1000 products", "I am a professional"));
        achievementManagement.getLoadedAchievements().add(new Achievement(AchievementTypes.SELL, 0, 10, "Sell 10 products", "How does the market work?"));
        achievementManagement.getLoadedAchievements().add(new Achievement(AchievementTypes.SELL, 0, 1000, "Buy 10 products", "Multi million"));
    }

    @Test
    public void loadTest(){
        assertEquals(0, achievementManagement.getPossibleAchievements().size());

        achievementManagement.loadPossibleAchievements();

        assertEquals(2, achievementManagement.getPossibleAchievements().size());

        assertEquals(3, achievementManagement.getPossibleAchievements().get(AchievementTypes.BUY).size());
        assertEquals(2, achievementManagement.getPossibleAchievements().get(AchievementTypes.SELL).size());
    }

    @Test
    public void updateTest(){
        achievementManagement.loadPossibleAchievements();

        assertEquals(0, achievementManagement.getPossibleAchievements().get(AchievementTypes.BUY).getFirst().getCurrent());

        achievementManagement.updateAchievement(AchievementTypes.BUY, 9);

        assertEquals(9, achievementManagement.getPossibleAchievements().get(AchievementTypes.BUY).getFirst().getCurrent());
        assertEquals(3, achievementManagement.getPossibleAchievements().get(AchievementTypes.BUY).size());

        achievementManagement.updateAchievement(AchievementTypes.BUY, 1);
        assertEquals(10, achievementManagement.getPossibleAchievements().get(AchievementTypes.BUY).getFirst().getCurrent());
        assertEquals(2, achievementManagement.getPossibleAchievements().get(AchievementTypes.BUY).size());
        assertEquals(1, achievementManagement.getDoneAchievements().get(AchievementTypes.BUY).size());

        achievementManagement.updateAchievement(AchievementTypes.BUY, 1000);
        assertEquals(3, achievementManagement.getDoneAchievements().get(AchievementTypes.BUY).size());
        assertEquals(0, achievementManagement.getPossibleAchievements().get(AchievementTypes.BUY).size());

        assertEquals(10, achievementManagement.getDoneAchievements().get(AchievementTypes.BUY).getFirst().getCurrent());
    }

}