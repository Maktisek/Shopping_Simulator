package Commands;

import Game.GameData;
import Upgrade.UpgradeNames;

public class UpgradeCommand extends Command{

    private final UpgradeNames name;

    public UpgradeCommand(GameData gameData, UpgradeNames name) {
        super(gameData);
        this.name = name;
    }

    @Override
    public String execute() {
        if(!getPlayer().canBuy(getUpgradeManagement().getUpgradePrice(name))){
            setSuccessful(false);
            return "Not enough money";
        }

        getUpgradeManagement().levelUpUpgrade(name);
        return "Skill " + name + " was upgraded";
    }
}
