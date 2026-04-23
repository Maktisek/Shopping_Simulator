package Commands.DayCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Player.InvalidPlayerActionException;

public class NewDayCommand extends Command {

    public NewDayCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public CommandResult execute() {
        if(getPlayer().bankrupt()){
            return new CommandResult("GAME OVER - BANKRUPT", CommandState.FAILED_END);
        }
        try {
            getPlayer().updateUndelivered();
        }catch (InvalidPlayerActionException e){
            return new CommandResult(e.getMessage(), CommandState.FAILED_ISSUE);
        }
        getShopManagement().setNewDays(getPlayer());
        getDayManagement().nextDay();
        return new CommandResult("Set new day - " + getDayManagement().getCurrentDay().getDayName(), CommandState.DONE);

    }
}
