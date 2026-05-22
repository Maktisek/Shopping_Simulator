package UI.DialogUI;

import AudioSystem.AudioType;
import Commands.CommandResult;
import Commands.UserInterfaceCommands.TurnOfTheGame;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

public class TurnOffTheGameDialogUI extends YesNoDialogUI {
    private final MainUI mainUI;

    public TurnOffTheGameDialogUI(String message, MainUI mainUI) throws InvalidUILoadException {
        super(message);
        this.mainUI = mainUI;
    }

    @Override
    public void initializeBuyButton() {
        CommandResult result = new TurnOfTheGame(mainUI).execute();
        Important.getAudioManagement().stopAll();
        Important.getAudioManagement().playSound("MenuOST", AudioType.MUSIC, 0);
        System.out.println(result.getMessage());
    }
}
