package Commands;

import Achievements.AchievementTypes;
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
    public String execute() {
        if (!getDayManagement().getCurrentDay().canIncrementDaySoldAmount(this.amount, getUpgradeManagement().getUpgradeData(UpgradeNames.SELL))) {
            super.setSuccessful(false);
            return "You cannot sell more than " + getUpgradeManagement().getUpgradeData(UpgradeNames.SELL) + " products at one day";
        }
        ItemNames product = getCurrentShop().getNpc().getDemand()[index].getName();
        try {
            getPlayer().sellItem(product, amount, getCurrentShop().getNpc().getDemand()[index].getCurrentPrice());
        } catch (InvalidPlayerActionException e) {
            super.setSuccessful(false);
            return e.getMessage();
        }
        getDayManagement().getCurrentDay().incrementDaySoldAmount(amount);
        getAchievementManagement().updateAchievement(AchievementTypes.SELL, amount);
        return "Sold " + amount + "x " + product;
    }
}
