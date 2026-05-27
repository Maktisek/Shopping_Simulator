package Commands.ShopCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Shops.Shop;
import Shops.ShopDirection;

/**
 * This command represents a system of changing shops.
 * <p>
 *     {@link #direction} determines in which direction should the shop be changed.
 * </p>
 * If there is no shop in the given direction then {@link CommandResult} with {@link CommandState#FAILED_ISSUE} is returned.
 * <p>
 *     If the shop in the given direction has not been bought then {@link CommandResult} with {@link CommandState#FAILED_BUY} is returned.
 * </p>
 * Otherwise {@link CommandResult} with {@link CommandState#DONE} is returned.
 * @author Matěj Pospíšil
 */
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
