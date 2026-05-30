package Commands.RebirthCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Game.Initialization;

/**
 * This command represents a system of buying new rebirth.
 *<p>
 *      If the player has not enough money, then {@link CommandResult} with {@link CommandState#FAILED_ISSUE} is returned.
 *</p>
 * <p>
 *     If the action was successful, then {@link CommandResult} with {@link CommandState#DONE} is returned.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class NewRebirthCommand extends Command {


    public NewRebirthCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public CommandResult execute() {
        long price = getUpgradeManagement().getRebirth().getPrice();
        if(!getPlayer().canBuy(price)){
            return new CommandResult("Not enough money for buying new rebirth", CommandState.FAILED_ISSUE);
        }

        getPlayer().setCurrentBalance(getPlayer().getCurrentBalance() - price);
        getUpgradeManagement().setNewRebirth();

        GameData temp = new Initialization().getGameData();
        temp.copyFromRebirth(this.getGameData());
        this.getGameData().copyFromLoaded(temp);

        return new CommandResult("New rebirth - level " + getUpgradeManagement().getRebirth().getLevel(), CommandState.DONE);
    }
}
