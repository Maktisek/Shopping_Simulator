package UI.DialogUI.SaveDialogs;

import AudioSystem.AudioType;
import Commands.CommandResult;
import Commands.SaveCommands.WriteSaveCommand;
import Game.GameData;
import UI.DialogUI.DecisionDialogs.TurnOffTheGameDialogUI;
import UI.DialogUI.DecisionDialogs.YesNoDialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;

public class SaveAndQuitDialogUI extends YesNoDialogUI {

    private final GameData gameData;

    public SaveAndQuitDialogUI(String message, GameData gameData) throws InvalidUILoadException {
        super(message);
        this.gameData = gameData;
    }

    @Override
    public void initializeYesButton() {
        CommandResult result = new WriteSaveCommand(gameData).execute();
        MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
        try {
            Important.getAudioManagement().playSound("Save", AudioType.SOUNDS, 0, false);
            parent.hideDialog();
            parent.showDialog(new TurnOffTheGameDialogUI(result.getMessage(), parent));
        } catch (InvalidUILoadException ex) {
            throw new RuntimeException(ex);
        }
    }
}
