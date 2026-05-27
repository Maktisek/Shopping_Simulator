package Commands.ProductCommands;

import Achievements.AchievementType;
import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Items.ItemDelivery;
import Items.ItemShop;
import Player.Exceptions.InvalidPlayerActionException;
import Upgrade.Utilities.UpgradeNames;

/**
 * This command represents a system of buying a product.
 * <p>
 * {@link #index} stands for the index of the product in the current shop.
 * </p>
 * <p>
 *     This command should be only executed from the current shop, otherwise the index do not correspond with the desired product.
 * </p>
 * <p>
 *    If daily buy limit is reached, stocks limit is reached, the product is out of stocks or player does not have enough money then {@link CommandResult} with {@link CommandState#FAILED_ISSUE} is returned.
 * </p>
 * <p>
 *     If the action was successful, then {@link CommandResult} with {@link CommandState#DONE} is returned.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class BuyProductCommand extends Command {

    private final int index;

    public BuyProductCommand(GameData gameData, int index) {
        super(gameData);
        this.index = index;
    }

    @Override
    public CommandResult execute() {
        int amount = getGameData().getAmount();
        if (!getDayManagement().getCurrentDay().canIncrementDayBoughtAmount(amount, getUpgradeManagement().getUpgradeData(UpgradeNames.BUY))) {
            return new CommandResult("You cannot buy more than " + getUpgradeManagement().getUpgradeData(UpgradeNames.BUY) + " products at one day",
                    CommandState.FAILED_ISSUE);
        }

        ItemShop product = getCurrentShop().getItems()[index];
        int price = getCurrentShop().getItems()[index].getItemBase().getCurrentPrice();
        ItemDelivery itemToDeliver = new ItemDelivery(product.getItemBase().getName(), amount, price, product.getDaysToBeDelivered());

        if (getPlayer().calculateAllStocks() + amount > getUpgradeManagement().getUpgradeData(UpgradeNames.STOCK)) {
            return new CommandResult("You cannot own more than " + getUpgradeManagement().getUpgradeData(UpgradeNames.STOCK) + " products in your warehouse",
                    CommandState.FAILED_ISSUE);
        }

        if(!product.getAmountManager().canDecrement(amount)){
            return new CommandResult(product.getItemBase().getName() +" is out of stocks", CommandState.FAILED_ISSUE);
        }

        try {
            getPlayer().buyItemNew(itemToDeliver);
        } catch (InvalidPlayerActionException e) {
            return new CommandResult(e.getMessage(), CommandState.FAILED_ISSUE);
        }
        getStatsCounter().setBought(getStatsCounter().getBought() + amount);
        getStatsCounter().setCosts(getStatsCounter().getCosts() + (amount * price));
        product.getAmountManager().decrement(amount);
        getCurrentShop().buyItem(index, amount, getUpgradeManagement().getRebirth().getPenalizationMultiplier());
        getDayManagement().getCurrentDay().incrementDayBoughtAmount(amount);
        getDayManagement().getCurrentDay().incrementDaySpending(amount * price);
        getAchievementManagement().updateAchievement(AchievementType.BUY, amount);
        return new CommandResult("Bought " + amount + "x " + product.getItemBase().getName(), CommandState.DONE);
    }
}
