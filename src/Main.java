import Commands.BuyProductCommand;
import Game.GameData;
import Game.Initialization;

public class Main {
    public static void main(String[] args) {

        GameData gameData = new Initialization().getGameData();
        System.out.println(new BuyProductCommand(gameData, 0, 2).execute());
        System.out.println(gameData);
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