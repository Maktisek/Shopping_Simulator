package Commands;

import Achievements.AchievementManagement;
import DayCycle.DayManagement;
import Game.GameData;
import Player.Player;
import Shops.Shop;
import Upgrade.UpgradeManagement;

public abstract class Command {

    private boolean successful;
    private final GameData gameData;

    public Command(GameData gameData) {
        this.successful = true;
        this.gameData = gameData;
    }

    public abstract String execute();

    public boolean isSuccessful(){
        return successful;
    }


    public GameData getGameData() {
        return gameData;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public Player getPlayer(){
        return this.gameData.getPlayer();
    }

    public DayManagement getDayManagement(){
        return this.gameData.getDayManagement();
    }

    public UpgradeManagement getUpgradeManagement(){
        return this.gameData.getUpgradeManagement();
    }

    public Shop getCurrentShop(){
        return this.gameData.getShopManagement().getCurrentShop();
    }

    public AchievementManagement getAchievementManagement(){
        return this.gameData.getAchievementManagement();
    }

}
