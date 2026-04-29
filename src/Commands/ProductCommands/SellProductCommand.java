package Commands.ProductCommands;

import Achievements.AchievementTypes;
import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Items.ItemNPC;
import Player.InvalidPlayerActionException;
import Upgrade.UpgradeNames;

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
        product.getAmountManager().decrement(amount);
        getDayManagement().getCurrentDay().incrementDaySoldAmount(amount);
        getDayManagement().getCurrentDay().incrementDayIncome(amount * price);
        getAchievementManagement().updateAchievement(AchievementTypes.SELL, amount);
        return new CommandResult("Sold " + amount + "x " + productName, CommandState.DONE);
    }
}
