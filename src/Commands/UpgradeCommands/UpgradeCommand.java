package Commands.UpgradeCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Upgrade.UpgradeNames;

public class UpgradeCommand extends Command {

    private final UpgradeNames name;

    public UpgradeCommand(GameData gameData, UpgradeNames name) {
        super(gameData);
        this.name = name;
    }

    @Override
    public CommandResult execute() {
        int price = getUpgradeManagement().getUpgradePrice(name);
        if (!getPlayer().canBuy(price)) {
            return new CommandResult("Not enough money", CommandState.FAILED_ISSUE);
        }
        getPlayer().setCurrentBalance(getPlayer().getCurrentBalance() - price);
        getUpgradeManagement().levelUpUpgrade(name);
        return new CommandResult("Skill " + name + " was upgraded - level: " + getUpgradeManagement().getUpgradeLevel(name),
                CommandState.DONE);
    }
}
