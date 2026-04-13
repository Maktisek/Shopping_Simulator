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
            case LEFT:
                if(!getShopManagement().isSwitchLeft()){
                    return new CommandResult("There is no other shop available", CommandState.FAILED_ISSUE);
                }
                temp = getShopManagement().peekLeft();
                break;
            case RIGHT:
                if(!getShopManagement().isSwitchRight()){
                    return new CommandResult("There is no other shop available", CommandState.FAILED_ISSUE);
                }
                temp = getShopManagement().peekRight();
                break;
        }
        if(temp.getShopKey().isUnlocked()){
            return new CommandResult(temp.getName() + " has been already bought", CommandState.FAILED_ISSUE);
        }

        if(!getPlayer().canBuy(temp.getShopKey().getPrice())){
            return new CommandResult("Not enough money", CommandState.FAILED_ISSUE);
        }

        getPlayer().setCurrentBalance(getPlayer().getCurrentBalance() - temp.getShopKey().getPrice());
        temp.getShopKey().setUnlocked(true);
        switch (direction){
            case LEFT -> getShopManagement().switchLeft();
            case RIGHT -> getShopManagement().switchRight();
        }
        return new CommandResult(temp.getName() + " has been bought", CommandState.DONE);
    }
}
