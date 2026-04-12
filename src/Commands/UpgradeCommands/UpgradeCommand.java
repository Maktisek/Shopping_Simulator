package Commands.UpgradeCommands;

import Commands.Command;
import Game.GameData;
import Upgrade.UpgradeNames;

public class UpgradeCommand extends Command {

    private final UpgradeNames name;

    public UpgradeCommand(GameData gameData, UpgradeNames name) {
        super(gameData);
        this.name = name;
    }

    @Override
    public String execute() {
        int price = getUpgradeManagement().getUpgradePrice(name);
        if(!getPlayer().canBuy(price)){
            setSuccessful(false);
            return "Not enough money";
        }
        getPlayer().setCurrentBalance(getPlayer().getCurrentBalance() - price);
        getUpgradeManagement().levelUpUpgrade(name);
        return "Skill " + name + " was upgraded";
    }
}
