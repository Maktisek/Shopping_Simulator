package Commands.ProductCommands;

import Achievements.AchievementType;
import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Items.ItemNPC;
import Player.Exceptions.InvalidPlayerActionException;
import Upgrade.Utilities.UpgradeNames;

/**
 * This command represents a system of selling a product.
 * <p>
 * {@link #index} stands for the index of the product, which is located in {@link NPCs.NPC} in current shop.
 * </p>
 * <p>
 * This command should be only executed from the current shop, otherwise the index do not correspond with the desired product.
 * </p>
 * <p>
 * If daily sell limit is reached, the product is out of demand or an unexpected issue occurs then {@link CommandResult} with {@link CommandState#FAILED_ISSUE} is returned.
 * </p>
 * <p>
 * If the action was successful, then {@link CommandResult} with {@link CommandState#DONE} is returned.
 * </p>
 */
public class SellProductCommand extends Command {

    private final int index;

    public SellProductCommand(GameData gameData, int index) {
        super(gameData);
        this.index = index;
    }

    @Override
    public CommandResult execute() {
        int amount = getGameData().getAmount();
        if (!getDayManagement().getCurrentDay().canIncrementDaySoldAmount(amount, getUpgradeManagement().getUpgradeData(UpgradeNames.SELL))) {
            return new CommandResult("You cannot sell more than " + getUpgradeManagement().getUpgradeData(UpgradeNames.SELL) + " products at one day",
                    CommandState.FAILED_ISSUE);
        }
        ItemNPC product = getCurrentShop().getNpc().getDemand()[index];
        String productName = product.getItem().getName();
        int price = product.getItem().getCurrentPrice();
        if (!product.getAmountManager().canDecrement(amount)) {
            return new CommandResult("Buyer does not need " + productName + " anymore", CommandState.FAILED_ISSUE);
        }
        try {
            getPlayer().sellItem(productName, amount, price);
        } catch (InvalidPlayerActionException e) {
            return new CommandResult(e.getMessage(), CommandState.FAILED_ISSUE);
        }
        getStatsCounter().setShipped(getStatsCounter().getShipped() + amount);
        getStatsCounter().setEarnings(getStatsCounter().getEarnings() + (amount * price));
        product.getAmountManager().decrement(amount);
        getDayManagement().getCurrentDay().incrementDaySoldAmount(amount);
        int income = amount * price;
        getDayManagement().getCurrentDay().incrementDayIncome(income);
        getAchievementManagement().updateAchievement(AchievementType.SELL, amount);
        getAchievementManagement().updateAchievement(AchievementType.MONEY, income);
        return new CommandResult("Sold " + amount + "x " + productName, CommandState.DONE);
    }
}
