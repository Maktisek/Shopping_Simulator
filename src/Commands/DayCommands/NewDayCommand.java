package Commands.DayCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;

public class NewDayCommand extends Command {

    public NewDayCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public CommandResult execute() {
        if(getPlayer().bankrupt()){
            return new CommandResult("GAME OVER - BANKRUPT", CommandState.FAILED_END);
        }
        getShopManagement().setNewDays(getPlayer());
        getDayManagement().nextDay();
        return new CommandResult("Set new day - " + getDayManagement().getCurrentDay().getDayName(), CommandState.DONE);

    }
}
