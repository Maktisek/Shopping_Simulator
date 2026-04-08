package Game;
import Achievements.AchievementManagement;
import DayCycle.DayManagement;
import Player.Player;
import Shops.ShopManagement;
import Upgrade.UpgradeManagement;

public class Initialization {

    public static GameData loadGameData(){
        return new GameData();
    }

    public static Player loadPlayer(){


        return new Player();
    }

    public static DayManagement loadDayManagement(){

        return new DayManagement();
    }

    public static ShopManagement loadShopManagement(){

        return new ShopManagement();
    }

    public static UpgradeManagement loadUpgradeManagement(){

        return new UpgradeManagement();
    }

    public static AchievementManagement loadAchievementManagement(){

        return new AchievementManagement();
    }
}
