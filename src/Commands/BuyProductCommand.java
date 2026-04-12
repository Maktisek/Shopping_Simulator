package Commands;

import Achievements.AchievementTypes;
import Game.GameData;
import Items.ItemNames;
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
    public String execute() {
        if (!getDayManagement().getCurrentDay().canIncrementDayBoughtAmount(this.amount, getUpgradeManagement().getUpgradeData(UpgradeNames.BUY))) {
            super.setSuccessful(false);
            return "You cannot buy more than " + getUpgradeManagement().getUpgradeData(UpgradeNames.BUY) + " products at one day";
        }
        ItemNames product = getCurrentShop().getItems()[index].getItem().getName();
        int price = getCurrentShop().getItems()[index].getItem().getCurrentPrice();
        try {
            getPlayer().buyItem(product, amount,price);
        }catch (InvalidPlayerActionException e){
            super.setSuccessful(false);
            return e.getMessage();
        }
        getCurrentShop().buyItem(index, amount);
        getDayManagement().getCurrentDay().incrementDayBoughtAmount(amount);
        getDayManagement().getCurrentDay().incrementDaySpending(amount * price);
        getAchievementManagement().updateAchievement(AchievementTypes.BUY, amount);

        return "Bought " + amount + "x " + product;
    }
}
