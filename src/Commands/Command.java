package Commands;

import Achievements.AchievementManagement;
import DayCycle.DayManagement;
import Game.GameData;
import Game.StatsCounter;
import Player.Player;
import Shops.Shop;
import Shops.ShopManagement;
import Taxes.Tax;
import Upgrade.UpgradeManagement;

/**
 * This class represents an abstract class, which is responsible for all commands in the system.
 * <p>
 *     {@link #gameData} is needed for almost every single command, that is why it is originally declared here
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public abstract class Command {

    private GameData gameData;


    public Command(GameData gameData) {
        this.gameData = gameData;
    }

    public Command() {
    }

    /**
     * This abstract method represents an execution of a command.
     * <p>
     *     Every single command gets his own {@code execute()} method implementation.
     * </p>
     * It is just the base of the command design pattern.
     * @return an instance of {@link CommandResult} with all information about the process.
     */
    public abstract CommandResult execute();


    public GameData getGameData() {
        return gameData;
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

    public Tax getTax(){
        return this.gameData.getTax();
    }

    public StatsCounter getStatsCounter(){
        return this.gameData.getStatsCounter();
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

}
