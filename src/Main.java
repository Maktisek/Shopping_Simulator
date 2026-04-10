import Commands.BuyProductCommand;
import Commands.SellProductCommand;
import Game.GameData;
import Game.Initialization;

public class Main {
    public static void main(String[] args) {

        GameData gameData = new Initialization().getGameData();
        BuyProductCommand command = new BuyProductCommand(gameData, 2, 20);
        System.out.println(command.execute());
        System.out.println(command.isSuccessful());
        System.out.println(gameData);
        System.out.println(gameData.getAchievementManagement().getFreshAchievements());

        gameData.getShopManagement().setNewDays(gameData.getPlayer());
        gameData.getDayManagement().nextDay();

        SellProductCommand command2 = new SellProductCommand(gameData, 0, 1);
        System.out.println(command2.execute());
        System.out.println(command2.isSuccessful());
        System.out.println(gameData);
        System.out.println(gameData.getAchievementManagement().getFreshAchievements());

        gameData.getShopManagement().setNewDays(gameData.getPlayer());
        gameData.getDayManagement().nextDay();

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