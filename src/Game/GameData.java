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

    public GameData(Player player, DayManagement dayManagement, ShopManagement shopManagement, UpgradeManagement upgradeManagement, AchievementManagement achievementManagement) {
        this.player = player;
        this.dayManagement = dayManagement;
        this.shopManagement = shopManagement;
        this.upgradeManagement = upgradeManagement;
        this.achievementManagement = achievementManagement;
    }
}
