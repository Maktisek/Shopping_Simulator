package Commands.UserInterfaceCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import UI.TitleUI.TitleScreenUI;

public class TurnOfTheGame extends Command {

    private final MainUI mainUI;

    public TurnOfTheGame(MainUI mainUI) {
        this.mainUI = mainUI;
    }

    @Override
    public CommandResult execute() {
        mainUI.turnOff();

        TitleScreenUI title;
        try {
            title = new TitleScreenUI();
        } catch (InvalidUILoadException ex) {
            return new CommandResult("There is a problem with loading TitleScreenUI", CommandState.FAILED_ISSUE);
        }
        title.makeVisible();
        return new CommandResult("The game was successfully turned off", CommandState.DONE);
    }
}
