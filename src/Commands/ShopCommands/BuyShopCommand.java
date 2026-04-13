package Commands.ShopCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Shops.Shop;

public class BuyShopCommand extends Command {

    private final ShopDirection direction;

    public BuyShopCommand(GameData gameData, ShopDirection direction) {
        super(gameData);
        this.direction = direction;
    }

    @Override
    public CommandResult execute() {
        Shop temp = null;
        switch (direction){
            case LEFT -> temp = getShopManagement().peekLeft();
            case RIGHT -> temp = getShopManagement().peekRight();
        }
        if(temp.getShopKey().isUnlocked()){
            return new CommandResult(temp.getName() + " has been already bought", CommandState.FAILED_ISSUE);
        }

        if(!getPlayer().canBuy(temp.getShopKey().getPrice())){
            return new CommandResult("Not enough money", CommandState.FAILED_ISSUE);
        }

        getPlayer().setCurrentBalance(getPlayer().getCurrentBalance() - temp.getShopKey().getPrice());
        temp.getShopKey().setUnlocked(true);
        getShopManagement().switchLeft();
        return new CommandResult(temp.getName() + " has been bought", CommandState.DONE);


    }
}
