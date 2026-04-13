package Commands.ProductCommands;

import Achievements.AchievementTypes;
import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Items.ItemNames;
import Player.InvalidPlayerActionException;
import Upgrade.UpgradeNames;

public class SellProductCommand extends Command {

    private final int amount;
    private final int index;

    public SellProductCommand(GameData gameData, int index, int amount) {
        super(gameData);
        this.amount = amount;
        this.index = index;
    }

    @Override
    public CommandResult execute() {
        if (!getDayManagement().getCurrentDay().canIncrementDaySoldAmount(this.amount, getUpgradeManagement().getUpgradeData(UpgradeNames.SELL))) {
            return new CommandResult("You cannot sell more than " + getUpgradeManagement().getUpgradeData(UpgradeNames.SELL) + " products at one day",
                    CommandState.FAILED_ISSUE);
        }
        ItemNames product = getCurrentShop().getNpc().getDemand()[index].getName();
        int price = getCurrentShop().getNpc().getDemand()[index].getCurrentPrice();
        try {
            getPlayer().sellItem(product, amount, price);
        } catch (InvalidPlayerActionException e) {
            return new CommandResult(e.getMessage(), CommandState.FAILED_ISSUE);
        }
        getDayManagement().getCurrentDay().incrementDaySoldAmount(amount);
        getDayManagement().getCurrentDay().incrementDayIncome(amount * price);
        getAchievementManagement().updateAchievement(AchievementTypes.SELL, amount);
        return new CommandResult("Sold " + amount + "x " + product, CommandState.DONE);
    }
}
