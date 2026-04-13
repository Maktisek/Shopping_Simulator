package Commands.ShopCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Shops.Shop;

public class ChangeShopRightCommand extends Command {

    public ChangeShopRightCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public CommandResult execute() {
        if (!getShopManagement().isSwitchRight()){
            return new CommandResult("There is no other shop available", CommandState.FAILED_ISSUE);
        }

        if(!getShopManagement().boughtRight()){
            Shop temp = getShopManagement().peekRight();
            return new CommandResult("Do you want to buy " + temp.getName() + " for " + temp.getShopKey().getPrice() + "?",
                    CommandState.FAILED_BUY);
        }

        getShopManagement().switchRight();
        return new CommandResult("Shop changed into: " + getShopManagement().getCurrentShop().getName(), CommandState.DONE);
    }
}
