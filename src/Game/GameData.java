package Game;

import Achievements.AchievementManagement;
import DayCycle.DayManagement;
import Player.Player;
import Shops.ShopManagement;
import Upgrade.UpgradeManagement;

public class GameData {

    private Player player;
    private DayManagement dayManagement;
    private ShopManagement shopManagement;
    private UpgradeManagement upgradeManagement;
    private AchievementManagement achievementManagement;


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public AchievementManagement getAchievementManagement() {
        return achievementManagement;
    }

    public void setAchievementManagement(AchievementManagement achievementManagement) {
        this.achievementManagement = achievementManagement;
    }

    public UpgradeManagement getUpgradeManagement() {
        return upgradeManagement;
    }

    public void setUpgradeManagement(UpgradeManagement upgradeManagement) {
        this.upgradeManagement = upgradeManagement;
    }

    public ShopManagement getShopManagement() {
        return shopManagement;
    }

    public void setShopManagement(ShopManagement shopManagement) {
        this.shopManagement = shopManagement;
    }

    public DayManagement getDayManagement() {
        return dayManagement;
    }

    public void setDayManagement(DayManagement dayManagement) {
        this.dayManagement = dayManagement;
    }
}
