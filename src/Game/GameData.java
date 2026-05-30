package Game;

import Achievements.AchievementManagement;
import DayCycle.DayManagement;
import Player.Player;
import Shops.ShopManagement;
import Taxes.Tax;
import Upgrade.UpgradeManagement;
import Utilities.Important;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class represents the main place, where all game data are being stored
 * <p>
 * All the data can be accessed from here making this the most efficient way how to manipulate with them.
 * </p>
 * If any new feature is added, it should be accessible from here (except static stuff)
 *
 * @author Matěj Pospíšil
 * @since 1.0 - (pre-release version)
 */
public class GameData implements Serializable {

    private Player player;
    private DayManagement dayManagement;
    private ShopManagement shopManagement;
    private UpgradeManagement upgradeManagement;
    private AchievementManagement achievementManagement;
    private Tax tax;
    private StatsCounter statsCounter;
    private transient int amount;

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public StatsCounter getStatsCounter() {
        return statsCounter;
    }

    public void setStatsCounter(StatsCounter statsCounter) {
        this.statsCounter = statsCounter;
    }


    /**
     * This method writes {@link GameData} version into pre-inserted path.
     * <p>
     * The path always points to current user folder, and it creates its own folder named {@code ForestMarketSave}.
     * </p>
     * <p>
     * The save can be only one and its name is {@code save}
     * </p>
     *
     * @throws IOException if the folder cannot not be created or the output stream cannot be opened
     */
    public void writeToFile() throws IOException {
        Path path = Paths.get(System.getProperty("user.home"), "ForestMarketSave", "save" + ".dat");
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            throw new IOException("There is a problem with creating the save folder.");
        }
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) {
            out.writeObject(this);
        } catch (IOException e) {
            throw new IOException(e.getMessage() + " cannot be serialized.");
        }
    }

    /**
     * This static methods loads {@link GameData} from pre-inserted path.
     * <p>
     * The path is same as in {@link #writeToFile()} because of compatibility.
     * </p>
     * <p>
     * Use when creating new instance of {@link GameData} - {@code GameData gameData = GameData.readFromFile();}
     * </p>
     *
     * @return the loaded instance of {@link GameData}
     * @throws IOException if there is no save being stored on the path, if the input stream cannot be opened or if the given instance is not instance
     *                     of {@link GameData}
     */
    public static GameData readFromFile() throws IOException {
        Path path = Paths.get(System.getProperty("user.home"), "ForestMarketSave", "save" + ".dat");
        if (!Files.exists(path)) {
            throw new IOException("There is no save available");
        }
        try (ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(path))) {
            return (GameData) stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * This method is needed for compatibility with the rest of the UI system.
     * <p>
     * The process of loading {@link GameData} is being executed in a command {@link Commands.SaveCommands.LoadSaveCommand}.
     * The instance of {@link GameData} is being sent there, not as a new object itself, but as a reference to the object.
     * If we change the reference through {@link #readFromFile()} the rest of the system do not know about it. And this is why this method
     * comes in handy.
     * </p>
     * The newly created instance of {@link GameData} gives its data to the old instance of {@link GameData}. The reference stays for both same, but
     * the data changes.
     *
     * @param gameData the newly created instance o {@link GameData} - through {@link #readFromFile()}
     */
    public void copyFromLoaded(GameData gameData) {
        this.setPlayer(gameData.getPlayer());
        this.setAchievementManagement(gameData.getAchievementManagement());
        this.setDayManagement(gameData.getDayManagement());
        this.setShopManagement(gameData.getShopManagement());
        this.setUpgradeManagement(gameData.getUpgradeManagement());
        this.setStatsCounter(gameData.getStatsCounter());
        this.setTax(gameData.getTax());
    }

    /**
     * This method works similar to {@link #copyFromLoaded(GameData)}, but it is used when player buys new rebirth.
     * <p>
     * Again it just rewrites data of the original reference, but unlike {@link #copyFromLoaded(GameData)} it keeps some of them or specifically change them.
     * </p>
     *
     * @param gameData fresh new instance of {@link GameData}, from which the data will be taken
     */
    public void copyFromRebirth(GameData gameData) {
        this.setDayManagement(gameData.getDayManagement());
        this.dayManagement.getCurrentDay().setDayBoughtAmount(0);
        this.dayManagement.getCurrentDay().setDaySoldAmount(0);
        this.setStatsCounter(gameData.getStatsCounter());
        this.setTax(gameData.getTax());
        this.getTax().updateAfterRebirth();
        this.getUpgradeManagement().setRebirth(gameData.getUpgradeManagement().getRebirth());
        this.getPlayer().setCurrentBalance(this.getUpgradeManagement().getRebirth().getCapital());
    }

    @Override
    public String toString() {
        return "Number of days:" + getDayManagement().getNumberOfDays() + "\n" +
                "Current rebirth:" + getUpgradeManagement().getRebirth().getLevel() + "\n" +
                "Total earnings:" + Important.parseMoney(getStatsCounter().getEarnings()) + " FR" + "\n" +
                "Total shipped:" + Important.parseMoney(getStatsCounter().getShipped()) + "\n" +
                "Total bought:" + Important.parseMoney(getStatsCounter().getBought()) + "\n" +
                "Avg. sell price:" + Important.parseMoney(getStatsCounter().averageShipPrice()) + " FR" + "\n" +
                "Avg. buy price:" + Important.parseMoney(getStatsCounter().averageBuyPrice()) + " FR" + "\n" +
                "Favorite product:" + getPlayer().findFavorite();
    }
}
