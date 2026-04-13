package Commands.ShopCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Shops.Shop;

public class ChangeShopLeftCommand extends Command {

    public ChangeShopLeftCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public CommandResult execute() {
        if (!getShopManagement().isSwitchLeft()){
            return new CommandResult("There is no other shop available", CommandState.FAILED_ISSUE);
        }

        if(!getShopManagement().boughtLeft()){
            Shop temp = getShopManagement().peekLeft();
            return new CommandResult("Do you want to buy " + temp.getName() + " for " + temp.getShopKey().getPrice() + " ?",
                    CommandState.FAILED_BUY);
        }

        getShopManagement().switchLeft();
        return new CommandResult("Shop changed into: " + getShopManagement().getCurrentShop().getName(), CommandState.DONE);
    }
}
