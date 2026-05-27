package Commands.UpgradeCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Upgrade.Utilities.UpgradeNames;

/**
 * This command represents a system of buying a new upgrade. The amount of bought upgrades is determined by {@link GameData#getAmount()}.
 * <p>
 *     If player has not enough money then {@link CommandResult} with {@link CommandState#FAILED_ISSUE} is returned
 * </p>
 * Otherwise {@link CommandResult} with {@link CommandState#DONE} is returned
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class UpgradeCommand extends Command {

    private final UpgradeNames name;

    public UpgradeCommand(GameData gameData, UpgradeNames name) {
        super(gameData);
        this.name = name;
    }

    @Override
    public CommandResult execute() {
        int price = getUpgradeManagement().getUpgradePrice(name) * getGameData().getAmount();
        if (!getPlayer().canBuy(price)) {
            return new CommandResult("Not enough money for new upgrade", CommandState.FAILED_ISSUE);
        }
        getPlayer().setCurrentBalance(getPlayer().getCurrentBalance() - price);
        getDayManagement().getCurrentDay().incrementDaySpending(price);
        for (int i = 0; i < getGameData().getAmount(); i++) {
            getUpgradeManagement().levelUpUpgrade(name);
        }
        return new CommandResult("Skill " + name + " was upgraded - level: " + getUpgradeManagement().getUpgradeLevel(name),
                CommandState.DONE);
    }
}
