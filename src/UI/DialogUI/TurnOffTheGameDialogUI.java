package UI.DialogUI;

import AudioSystem.AudioType;
import Commands.CommandResult;
import Commands.UserInterfaceCommands.TurnOfTheGame;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

public class TurnOffTheGameDialogUI extends DialogUI {
    private final MainUI mainUI;

    public TurnOffTheGameDialogUI(String imgFile, String message, MainUI mainUI) throws InvalidUILoadException {
        super(imgFile, message);
        this.mainUI = mainUI;
    }

    @Override
    public void buttonAction() {
        CommandResult result = new TurnOfTheGame(mainUI).execute();
        System.out.println(result.getMessage());
    }
}
