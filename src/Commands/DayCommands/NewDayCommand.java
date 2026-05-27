package Commands.DayCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Player.Exceptions.InvalidPlayerActionException;

/**
 * This command represents a system, which sets new day.
 * <p>
 *     If the player is bankrupt then {@link CommandResult} with {@link CommandState#FAILED_END} is returned.
 * </p>
 * <p>
 *     If any unexpected mistake happens during player undelivered update process then {@link CommandResult} with {@link CommandState#FAILED_END} is returned.
 * </p>
 * <p>
 *     If the whole process was successful then {@link CommandResult} with {@link CommandState#DONE} is returned
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class NewDayCommand extends Command {

    public NewDayCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public CommandResult execute() {
        getPlayer().setCurrentBalance(getPlayer().getCurrentBalance() - getTax().getCurrent());
        if(getPlayer().bankrupt() && getAchievementManagement().checkForUnclaimed()){
            return new CommandResult("GAME OVER - BANKRUPT", CommandState.FAILED_END);
        }
        try {
            getPlayer().updateUndelivered();
        }catch (InvalidPlayerActionException e){
            return new CommandResult(e.getMessage(), CommandState.FAILED_ISSUE);
        }
        getShopManagement().setNewDays(getPlayer(), getUpgradeManagement().getRebirth().getPenalizationMultiplier());
        getDayManagement().nextDay();
        getTax().incrementDayNumber();
        getTax().calculateNewDay();
        return new CommandResult("Set new day - " + getDayManagement().getCurrentDay().getDayName(), CommandState.DONE);
    }
}
