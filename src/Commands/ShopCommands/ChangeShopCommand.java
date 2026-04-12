package Commands.ShopCommands;

import Commands.Command;
import Game.GameData;
import Shops.Shop;
import Shops.ShopNames;

public class ChangeShopCommand extends Command {

    private final ShopNames name;

    public ChangeShopCommand(GameData gameData, ShopNames name) {
        super(gameData);
        this.name = name;
    }

    @Override
    public String execute() {
        Shop foundShop = getShopManagement().findShop(name);
        if(foundShop == null){
            setSuccessful(false);
            return "Shop " + name + " does not exist";
        }

        if(!foundShop.getShopKey().isUnlocked()){
            setSuccessful(false);
            return "Shop " + name + " is locked. Price: " + foundShop.getShopKey().getPrice();
        }

        getShopManagement().setCurrentShop(foundShop);
        return "Shop has been changed into " + name;
    }
}
