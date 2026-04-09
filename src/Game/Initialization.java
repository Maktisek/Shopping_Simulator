package Game;
import Achievements.AchievementManagement;
import DayCycle.DayManagement;
import Items.WrongItemException;
import Player.Player;
import Shops.ShopManagement;
import Upgrade.UpgradeManagement;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Initialization {

    private final GameData gameData;

    public Initialization() {
        this.gameData = new GameData();

    }

    private void initGameData(){
        loadPlayer();
        loadDayManagement();
        loadShopManagement();
        loadUpgradeManagement();
        loadAchievementManagement();
        finishInitialization();
    }

    private void loadPlayer(){
        Gson gson = new Gson();

        try (InputStream is = GameData.class.getResourceAsStream("/Jsons/Player.json")){
            if(is == null){
                throw new IllegalStateException("The path for Json: /Jsons/Player.json is invalid and the file could not be found");
            }
            this.gameData.setPlayer(gson.fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), Player.class));
        }catch (Exception e){
            throw new RuntimeException("There is an mistake withing loading the Json file while loading Player: " + e.getMessage());
        }
    }

    private void loadDayManagement(){
        this.gameData.setDayManagement(new DayManagement());
    }

    private void loadShopManagement(){
        Gson gson = new Gson();

        try (InputStream is = GameData.class.getResourceAsStream("/Jsons/ShopManagement.json")){
            if(is == null){
                throw new IllegalStateException("The path for Json: /Jsons/ShopManagement.json is invalid and the file could not be found");
            }
            this.gameData.setShopManagement(gson.fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), ShopManagement.class));
        }catch (Exception e){
            throw new RuntimeException("There is an mistake withing loading the Json file while loading ShopManagement: " + e.getMessage());
        }
    }

    private void loadUpgradeManagement(){
        Gson gson = new Gson();

        try (InputStream is = GameData.class.getResourceAsStream("/Jsons/UpgradeManagement.json")){
            if(is == null){
                throw new IllegalStateException("The path for Json: /Jsons/UpgradeManagement.json is invalid and the file could not be found");
            }
            this.gameData.setUpgradeManagement(gson.fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), UpgradeManagement.class));
        }catch (Exception e){
            throw new RuntimeException("There is an mistake withing loading the Json file while loading UpgradeManagement: " + e.getMessage());
        }
    }

    private void loadAchievementManagement(){
        Gson gson = new Gson();

        try (InputStream is = GameData.class.getResourceAsStream("/Jsons/AchievementManagement.json")){
            if(is == null){
                throw new IllegalStateException("The path for Json: /Jsons/AchievementManagement.json is invalid and the file could not be found");
            }
            this.gameData.setAchievementManagement(gson.fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), AchievementManagement.class));
        }catch (Exception e){
            throw new RuntimeException("There is an mistake withing loading the Json file while loading AchievementManagement: " + e.getMessage());
        }
    }

    private void finishInitialization(){
        try {
            this.gameData.getPlayer().loadItems(this.gameData.getShopManagement().getShops());

        } catch (WrongItemException e) {
            throw new RuntimeException(e);
        }
    }

    public GameData getGameData() {
        return gameData;
    }
}
