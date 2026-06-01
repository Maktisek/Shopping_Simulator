package Commands.UpgradeCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Upgrade.Utilities.UpgradeType;

/**
 * This command represents a system of buying a new upgrade. The amount of bought upgrades is determined by {@link GameData#getAmount()}.
 * <p>
 *     If player has not enough money then {@link CommandResult} with {@link CommandState#FAILED_ISSUE} is returned
 * </p>
 * <p>
 *     If {@link #check} is set to {@code true}, then the system will check whether player will not bankrupt after buying this upgrade.
 *     If yes then {@link CommandResult} with {@link CommandState#FAILED_BUY} is returned.
 * </p>
 * Otherwise {@link CommandResult} with {@link CommandState#DONE} is returned
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class UpgradeCommand extends Command {

    private final UpgradeType name;
    private boolean check;

    public UpgradeCommand(GameData gameData, UpgradeType name, boolean check) {
        super(gameData);
        this.name = name;
        this.check = check;
    }

    @Override
    public CommandResult execute() {
        long price = getUpgradeManagement().getUpgradePrice(name) * getGameData().getAmount();
        if (!getPlayer().canBuy(price)) {
            return new CommandResult("Not enough money for new upgrade", CommandState.FAILED_ISSUE);
        }
        getPlayer().setCurrentBalance(getPlayer().getCurrentBalance() - price);

        if(getPlayer().bankrupt() && check){
            getPlayer().setCurrentBalance(getPlayer().getCurrentBalance() + price);
            return new CommandResult("You may lose, do you want to continue?",
                    CommandState.FAILED_BUY);
        }
        getDayManagement().getCurrentDay().incrementDaySpending(price);
        for (int i = 0; i < getGameData().getAmount(); i++) {
            getUpgradeManagement().levelUpUpgrade(name);
        }
        return new CommandResult("Skill " + name + " was upgraded - level: " + getUpgradeManagement().getUpgradeLevel(name),
                CommandState.DONE);
    }
}
