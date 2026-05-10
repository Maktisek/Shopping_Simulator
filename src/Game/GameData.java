package Game;

import Achievements.AchievementManagement;
import DayCycle.DayManagement;
import Player.Player;
import Shops.ShopManagement;
import Upgrade.UpgradeManagement;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GameData implements Serializable {

    private Player player;
    private DayManagement dayManagement;
    private ShopManagement shopManagement;
    private UpgradeManagement upgradeManagement;
    private AchievementManagement achievementManagement;
    private int amount;

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

    public static GameData readFromFile() throws IOException {
        Path path = Paths.get(System.getProperty("user.home"), "ForestMarketSave", "save" + ".dat");
        if (!Files.exists(path)) {
            throw new IOException("There is no save available");
        }
        try (ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(path))) {
            GameData gameData = (GameData) stream.readObject();
            return gameData;
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException(e.getMessage());
        }
    }

    public void copyFromLoaded(GameData gameData){
        this.setPlayer(gameData.getPlayer());
        this.setAchievementManagement(gameData.getAchievementManagement());
        this.setDayManagement(gameData.getDayManagement());
        this.setShopManagement(gameData.getShopManagement());
        this.setUpgradeManagement(gameData.getUpgradeManagement());
    }

    @Override
    public String toString() {
        return "GameData{" +
                "player=" + player +
                ", dayManagement=" + dayManagement +
                ", shopManagement=" + shopManagement +
                ", upgradeManagement=" + upgradeManagement +
                ", achievementManagement=" + achievementManagement +
                ", amount=" + amount +
                '}';
    }
}
