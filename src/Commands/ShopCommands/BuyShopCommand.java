package Commands.ShopCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Items.Exceptions.WrongItemException;
import Shops.Shop;

/**
 * This command represents a system of buying a new shop.
 * <p>
 * {@link #direction} stands for the direction of the shop to be bought.
 * Shops are stored in two stacks - {@link #direction} chooses, from which one the shop should be bought.
 * </p>
 * <p>
 * If there is no shop available in the stack, the shop has been already bought, the rebirth level is too low, player has not
 * enough money, or an unexpected issue occurs then {@link CommandResult} with {@link CommandState#FAILED_ISSUE} is returned.
 * </p>
 * <p>
 * If the action was successful, then {@link CommandResult} with {@link CommandState#DONE} is returned.
 * </p>
 * @author Matěj Pospíšil
 */
public class BuyShopCommand extends Command {

    private final ShopDirection direction;

    public BuyShopCommand(GameData gameData, ShopDirection direction) {
        super(gameData);
        this.direction = direction;
    }

    @Override
    public CommandResult execute() {
        Shop temp = null;
        switch (direction) {
            case LEFT:
                if (!getShopManagement().isSwitchLeft()) {
                    return new CommandResult("There is no other shop available", CommandState.FAILED_ISSUE);
                }
                temp = getShopManagement().peekLeft();
                break;
            case RIGHT:
                if (!getShopManagement().isSwitchRight()) {
                    return new CommandResult("There is no other shop available", CommandState.FAILED_ISSUE);
                }
                temp = getShopManagement().peekRight();
                break;
        }
        if (temp.getShopKey().isUnlocked()) {
            return new CommandResult(temp.getName() + " has been already bought", CommandState.FAILED_ISSUE);
        }

        if (temp.getShopKey().getRebirthLevel() > getUpgradeManagement().getRebirth().getLevel()) {
            return new CommandResult("You need rebirth level " + temp.getShopKey().getRebirthLevel() + " for unlocking " + temp.getName(), CommandState.FAILED_ISSUE);
        }

        if (!getPlayer().canBuy(temp.getShopKey().getPrice())) {
            return new CommandResult("Not enough money", CommandState.FAILED_ISSUE);
        }

        getPlayer().setCurrentBalance(getPlayer().getCurrentBalance() - temp.getShopKey().getPrice());
        temp.getShopKey().setUnlocked(true);
        try {
            getPlayer().loadItems(temp.getItems());
        } catch (WrongItemException e) {
            return new CommandResult("This should not happen", CommandState.FAILED_ISSUE);
        }
        switch (direction) {
            case LEFT -> getShopManagement().switchLeft();
            case RIGHT -> getShopManagement().switchRight();
        }
        return new CommandResult(temp.getName() + " has been bought", CommandState.DONE);
    }
}
