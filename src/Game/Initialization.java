package Game;

import Achievements.AchievementManagement;
import DayCycle.DayManagement;
import Items.Exceptions.WrongItemException;
import NPCs.NPCFinder;
import Player.Player;
import Shops.Shop;
import Shops.ShopManagement;
import Taxes.Tax;
import Upgrade.Rebirth.Rebirth;
import Upgrade.Upgrade;
import Upgrade.UpgradeFinder;
import Upgrade.UpgradeManagement;
import Upgrade.UpgradeBasicType;
import com.google.gson.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * This class represents a system of loading a new instance of {@link GameData} from multiple jsons.
 * <p>
 * The process has to be divided into multiple layers each loading a specific area of the game.
 * </p>
 * In the end bonus methods for connecting still unconnected are being executed finishing the whole load process.
 *
 * @author Matěj Pospíšil
 * @since 1.0 - (pre-release version)
 */
public class Initialization {

    private final GameData gameData;
    private NPCFinder[] npcs;
    private UpgradeFinder[] upgrades;

    public Initialization() {
        this.gameData = new GameData();
        initGameData();
    }

    private void initGameData() {
        loadTax();
        loadDayManagement();
        loadShopManagement();
        loadNPCs();
        connectShopAndNPCs();
        loadUpgrades();
        loadUpgradeManagement();
        loadAchievementManagement();
        loadStatsCounter();
        loadPlayer();
        finishInitialization();
    }

    /**
     * This method loads {@link Player} and {@link Rebirth} from json located on {@code res/Jsons/Rebirth.json}.
     * <p>
     * This may look weird, loading player from rebirth, but it is actually pretty simple.
     * The player does not need anything else than starting money. So firstly an instance of {@link Rebirth} is loaded and then
     * its {@code capital} is being used in {@link Player} initialization.
     * </p>
     */
    private void loadPlayer() {
        Gson gson = new Gson();

        try (InputStream is = GameData.class.getResourceAsStream("/Jsons/Rebirth.json")) {
            if (is == null) {
                throw new IllegalStateException("The path for Json: /Jsons/Rebirth.json is invalid and the file could not be found");
            }
            this.gameData.getUpgradeManagement().setRebirth(gson.fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), Rebirth.class));
            this.gameData.setPlayer(new Player());
            this.gameData.getPlayer().setCurrentBalance(this.gameData.getUpgradeManagement().getRebirth().getCapital());
        } catch (Exception e) {
            throw new RuntimeException("There is an mistake withing loading the Json file while loading Player: " + e.getMessage());
        }
    }

    /**
     * This method loads {@link Tax} from json located on {@code res/Jsons/Tax.json}
     */
    private void loadTax() {
        Gson gson = new Gson();

        try (InputStream is = GameData.class.getResourceAsStream("/Jsons/Tax.json")) {
            if (is == null) {
                throw new IllegalStateException("The path for Json: /Jsons/Tax.json is invalid and the file could not be found");
            }
            this.gameData.setTax(gson.fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), Tax.class));
            this.gameData.getTax().initializeK();
        } catch (Exception e) {
            throw new RuntimeException("There is an mistake withing loading the Json file while loading Tax: " + e.getMessage());
        }
    }

    private void loadDayManagement() {
        this.gameData.setDayManagement(new DayManagement());
    }

    /**
     * This method loads {@link ShopManagement} from json located on {@code res/Jsons/ShopManagement.json}
     */
    private void loadShopManagement() {
        Gson gson = new Gson();

        try (InputStream is = GameData.class.getResourceAsStream("/Jsons/ShopManagement.json")) {
            if (is == null) {
                throw new IllegalStateException("The path for Json: /Jsons/ShopManagement.json is invalid and the file could not be found");
            }
            ShopManagement shopManagement = gson.fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), ShopManagement.class);
            shopManagement.loadStacks();
            this.gameData.setShopManagement(shopManagement);
        } catch (Exception e) {
            throw new RuntimeException("There is an mistake withing loading the Json file while loading ShopManagement: " + e.getMessage());
        }
    }

    /**
     * This method loads {@link NPCFinder} from json located on {@code res/Jsons/NPCs.json}
     */
    private void loadNPCs() {
        Gson gson = new Gson();

        try (InputStream is = GameData.class.getResourceAsStream("/Jsons/NPCs.json")) {
            if (is == null) {
                throw new IllegalStateException("The path for Json: /Jsons/NPCs.json is invalid and the file could not be found");
            }
            this.npcs = gson.fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), NPCFinder[].class);
        } catch (Exception e) {
            throw new RuntimeException("There is an mistake withing loading the Json file while loading ShopManagement: " + e.getMessage());
        }
    }

    private void loadStatsCounter() {
        this.gameData.setStatsCounter(new StatsCounter());
    }

    /**
     * Loads all shops with all possible NPCs. If there is less NPCs than shops, then a problem will occur - the NPC will be null.
     */
    private void connectShopAndNPCs() {
        for (Shop shop : gameData.getShopManagement().getShops()) {
            for (NPCFinder npcFinder : npcs) {
                if (npcFinder.getShopName().equalsIgnoreCase(shop.getName())) {
                    shop.setNpc(npcFinder.getNpc());
                }
            }
        }
    }

    /**
     * This method loads {@link #upgrades} from json file located on {@code /Jsons/Upgrades.json}.
     * <p>
     *     Because there is a field of {@link Upgrade} data type in {@link UpgradeFinder}, the Gson do not know how to
     *     create it, since {@link Upgrade} is an interface. This is why there is a code initializing the Gson and telling it what should it actually load based
     *     on {@code type} field in the given json.
     * </p>
     * If the {@code type} is set to {@code basic}, then an instance of {@link UpgradeBasicType} is created.
     * <p>
     *     In the end, a finish method is executed for all instances of {@link UpgradeFinder} in {@link #upgrades}.
     * </p>
     */
    private void loadUpgrades() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Upgrade.class, (JsonDeserializer<Upgrade>) (json, typeOfT, context) -> {
                    JsonObject jsonObject = json.getAsJsonObject();
                    String type = jsonObject.get("type").getAsString();
                    if (type.equals("basic")) {
                        return context.deserialize(json, UpgradeBasicType.class);
                    }
                    return null;
                })
                .create();

        try (InputStream is = GameData.class.getResourceAsStream("/Jsons/Upgrades.json")) {
            if (is == null) {
                throw new IllegalStateException("The path for Json: /Jsons/Upgrades.json is invalid and the file could not be found");
            }
            this.upgrades = gson.fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), UpgradeFinder[].class);
            for (UpgradeFinder upgradeFinder : upgrades) {
                upgradeFinder.finishValue();
            }
        } catch (Exception e) {
            throw new RuntimeException("There is an mistake withing loading the Json file while loading ShopManagement: " + e.getMessage());
        }
    }

    /**
     * Loads {@link #gameData} with an instance of {@link UpgradeManagement} and then
     * it loads the instance with all upgrades, which are found inside {@link #upgrades}.
     */
    private void loadUpgradeManagement() {
        this.gameData.setUpgradeManagement(new UpgradeManagement());
        for (UpgradeFinder upgradeFinder : upgrades) {
            this.gameData.getUpgradeManagement().addUpgrade(upgradeFinder.getKey(), upgradeFinder.getValue());
        }
    }

    /**
     * This method loads {@link AchievementManagement} from json located on {@code res/Jsons/AchievementManagement.json}
     */
    private void loadAchievementManagement() {
        Gson gson = new Gson();

        try (InputStream is = GameData.class.getResourceAsStream("/Jsons/AchievementManagement.json")) {
            if (is == null) {
                throw new IllegalStateException("The path for Json: /Jsons/AchievementManagement.json is invalid and the file could not be found");
            }
            AchievementManagement achievementManagement = gson.fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), AchievementManagement.class);
            achievementManagement.loadPossibleAchievements();
            this.gameData.setAchievementManagement(achievementManagement);
        } catch (Exception e) {
            throw new RuntimeException("There is an mistake withing loading the Json file while loading AchievementManagement: " + e.getMessage());
        }
    }

    /**
     * This method finishes the initialization process.
     * <p>
     * It loads player with all items that are possible to be in the stock, and it loads all NPCs with items and sets their prices.
     * </p>
     */
    private void finishInitialization() {
        try {
            for (Shop shop : this.gameData.getShopManagement().getShops()) {
                if (shop.getShopKey().isUnlocked()) {
                    this.gameData.getPlayer().loadItems(shop.getItems());
                }
            }
            this.gameData.getShopManagement().loadAllNpc(this.getGameData().getPlayer());
        } catch (WrongItemException e) {
            throw new RuntimeException(e);
        }
    }

    public GameData getGameData() {
        return gameData;
    }
}
