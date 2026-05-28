package UI.DialogUI.DecisionDialogs;

import Commands.CommandResult;
import Commands.UserInterfaceCommands.TurnOffTheGame;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;

public class TurnOffTheGameDialogUI extends YesNoDialogUI {
    private final MainUI mainUI;

    public TurnOffTheGameDialogUI(String message, MainUI mainUI) throws InvalidUILoadException {
        super(message);
        this.mainUI = mainUI;
    }

    @Override
    public void initializeYesButton() {
        CommandResult result = new TurnOffTheGame(mainUI).execute();
        System.out.println(result.getMessage());
    }
}
