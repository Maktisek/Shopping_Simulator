package Commands.ShopCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Shops.Shop;
import Shops.ShopNames;

public class BuyShopCommand extends Command {

    private final ShopNames name;

    public BuyShopCommand(GameData gameData, ShopNames name) {
        super(gameData);
        this.name = name;
    }

    @Override
    public CommandResult execute() {
        Shop foundShop = getShopManagement().findShop(name);
        if(foundShop == null){
            return new CommandResult("Shop " + name + " does not exist", CommandState.FAILED_ISSUE);
        }

        if(foundShop.getShopKey().isUnlocked()){
            return new CommandResult("Shop " + name + " is already unlocked", CommandState.FAILED_ISSUE);
        }

        if(!getPlayer().canBuy(foundShop.getShopKey().getPrice())){
            return new CommandResult("Not enough money", CommandState.FAILED_ISSUE);
        }

        foundShop.getShopKey().setUnlocked(true);
        getPlayer().setCurrentBalance(getPlayer().getCurrentBalance() - foundShop.getShopKey().getPrice());
        return new CommandResult("Bought new shop: " + name, CommandState.DONE);
    }
}
