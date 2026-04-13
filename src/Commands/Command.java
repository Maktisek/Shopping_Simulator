package Commands;

import Achievements.AchievementManagement;
import DayCycle.DayManagement;
import Game.GameData;
import Player.Player;
import Shops.Shop;
import Shops.ShopManagement;
import Upgrade.UpgradeManagement;

public abstract class Command {

    private final GameData gameData;
    private CommandResult result;

    public Command(GameData gameData) {
        this.result = null;
        this.gameData = gameData;
    }

    public abstract CommandResult execute();


    public GameData getGameData() {
        return gameData;
    }

    public CommandResult getResult() {
        return result;
    }

    public void setResult(CommandResult result) {
        this.result = result;
    }

    public Player getPlayer() {
        return this.gameData.getPlayer();
    }

    public DayManagement getDayManagement() {
        return this.gameData.getDayManagement();
    }

    public UpgradeManagement getUpgradeManagement() {
        return this.gameData.getUpgradeManagement();
    }

    public Shop getCurrentShop() {
        return this.gameData.getShopManagement().getCurrentShop();
    }

    public AchievementManagement getAchievementManagement() {
        return this.gameData.getAchievementManagement();
    }

    public ShopManagement getShopManagement() {
        return this.gameData.getShopManagement();
    }

}
