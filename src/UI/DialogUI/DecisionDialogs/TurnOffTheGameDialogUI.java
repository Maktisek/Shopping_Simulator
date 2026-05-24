package UI.DialogUI.DecisionDialogs;

import Commands.CommandResult;
import Commands.UserInterfaceCommands.TurnOfTheGame;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;

public class TurnOffTheGameDialogUI extends YesNoDialogUI {
    private final MainUI mainUI;

    public TurnOffTheGameDialogUI(String message, MainUI mainUI) throws InvalidUILoadException {
        super(message);
        this.mainUI = mainUI;
    }

    @Override
    public void initializeBuyButton() {
        CommandResult result = new TurnOfTheGame(mainUI).execute();
        System.out.println(result.getMessage());
    }
}
