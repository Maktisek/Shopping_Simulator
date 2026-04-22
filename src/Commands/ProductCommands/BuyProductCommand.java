package Commands.ProductCommands;

import Achievements.AchievementTypes;
import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Items.ItemNames;
import Items.ItemShop;
import Player.InvalidPlayerActionException;
import Upgrade.UpgradeNames;

public class BuyProductCommand extends Command {

    private final int amount;
    private final int index;

    public BuyProductCommand(GameData gameData, int index, int amount) {
        super(gameData);
        this.amount = amount;
        this.index = index;
    }

    @Override
    public CommandResult execute() {
        if (!getDayManagement().getCurrentDay().canIncrementDayBoughtAmount(this.amount, getUpgradeManagement().getUpgradeData(UpgradeNames.BUY))) {
            return new CommandResult("You cannot buy more than " + getUpgradeManagement().getUpgradeData(UpgradeNames.BUY) + " products at one day",
                    CommandState.FAILED_ISSUE);
        }

        ItemShop product = getCurrentShop().getItems()[index];
        int price = getCurrentShop().getItems()[index].getItem().getCurrentPrice();

        if(getPlayer().calculateStocks() > getUpgradeManagement().getUpgradeData(UpgradeNames.STOCK)){
            return new CommandResult("You cannot own more than " + getUpgradeManagement().getUpgradeData(UpgradeNames.STOCK) + " products in your warehouse",
                    CommandState.FAILED_ISSUE);
        }

        try {
            getPlayer().buyItem(product.getItem().getName(), amount,price);
            product.updatePenalization(0.02);
        }catch (InvalidPlayerActionException e){
            return new CommandResult(e.getMessage(), CommandState.FAILED_ISSUE);
        }
        getCurrentShop().buyItem(index, amount);
        getDayManagement().getCurrentDay().incrementDayBoughtAmount(amount);
        getDayManagement().getCurrentDay().incrementDaySpending(amount * price);
        getAchievementManagement().updateAchievement(AchievementTypes.BUY, amount);
        return new CommandResult("Bought " + amount + "x " + product.getItem().getName(), CommandState.DONE);
    }
}
