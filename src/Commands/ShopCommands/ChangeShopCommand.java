package Commands.ShopCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Shops.Shop;

public class ChangeShopCommand extends Command {

    private final ShopDirection direction;

    public ChangeShopCommand(GameData gameData, ShopDirection direction) {
        super(gameData);
        this.direction = direction;
    }

    @Override
    public CommandResult execute() {
        if (getShopManagement().isSwitch(direction)) {
            return new CommandResult("There is no other shop available", CommandState.FAILED_ISSUE);
        }

        if (!getShopManagement().isBought(direction)) {
            Shop temp = getShopManagement().peek(direction);
            return new CommandResult("Do you want to buy " + temp.getName() + " for " + temp.getShopKey().getPrice() + "?",
                    CommandState.FAILED_BUY);
        }

        getShopManagement().switchFromStack(direction);
        return new CommandResult("Shop changed into: " + getShopManagement().getCurrentShop().getName(), CommandState.DONE);

    }
}
