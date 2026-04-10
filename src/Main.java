import Commands.BuyProductCommand;
import Game.GameData;
import Game.Initialization;

public class Main {
    public static void main(String[] args) {

        GameData gameData = new Initialization().getGameData();
        System.out.println(new BuyProductCommand(gameData, 0, 0).execute());
        System.out.println(gameData);
        System.out.println(gameData.getAchievementManagement().getFreshAchievements());
    }
    public static void initTest(){
        GameData gameData = new Initialization().getGameData();
        System.out.println(gameData.toString());
        gameData.getShopManagement().setNewDays(gameData.getPlayer());
        gameData.getDayManagement().nextDay();
        System.out.println("\n"+gameData.toString());
        gameData.getShopManagement().setNewDays(gameData.getPlayer());
        gameData.getDayManagement().nextDay();
        System.out.println("\n"+gameData.toString());
        gameData.getShopManagement().setNewDays(gameData.getPlayer());
        gameData.getDayManagement().nextDay();
        System.out.println("\n"+gameData.toString());
        gameData.getShopManagement().setNewDays(gameData.getPlayer());
        gameData.getDayManagement().nextDay();
        System.out.println("\n"+gameData.toString());
        gameData.getShopManagement().setNewDays(gameData.getPlayer());
        gameData.getDayManagement().nextDay();
        System.out.println("\n"+gameData.toString());
    }
}