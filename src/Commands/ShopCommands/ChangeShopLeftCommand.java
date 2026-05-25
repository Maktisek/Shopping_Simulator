package Commands.ShopCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Shops.Shop;

/**
 * This command represents a system of changing the current shop from left stack in {@link Shops.ShopManagement}
 * <p>
 *     If the stack is empty then {@link CommandResult} with {@link CommandState#FAILED_ISSUE} is returned.
 * </p>
 * <p>
 *     If the shop has not been bought then {@link CommandResult} with {@link CommandState#FAILED_BUY} is returned.
 * </p>
 * If the action was successful, then {@link CommandResult} with {@link CommandState#DONE} is returned.
 * @author Matěj Pospíšil
 */
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
            return new CommandResult("Do you want to buy " + temp.getName() + " for " + temp.getShopKey().getPrice() + "?",
                    CommandState.FAILED_BUY);
        }

        getShopManagement().switchLeft();
        return new CommandResult("Shop changed into: " + getShopManagement().getCurrentShop().getName(), CommandState.DONE);
    }
}
