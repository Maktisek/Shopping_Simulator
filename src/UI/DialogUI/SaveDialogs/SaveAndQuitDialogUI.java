package UI.DialogUI.SaveDialogs;

import AudioSystem.AudioType;
import Commands.CommandResult;
import Commands.SaveCommands.WriteSaveCommand;
import Game.GameData;
import UI.DialogUI.TurnOffTheGameDialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;

public class SaveAndQuitDialogUI extends SaveBaseDialogUI{

    public SaveAndQuitDialogUI(String imgFile, String message, GameData gameData) throws InvalidUILoadException {
        super(imgFile, message, gameData);
    }

    @Override
    public void loadSaveButton() {
        CommandResult result = new WriteSaveCommand(super.gameData).execute();
        MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
        try {
            Important.getAudioManagement().playSound("Save", AudioType.SOUNDS, 0);
            parent.hideDialog();
            parent.showDialog(new TurnOffTheGameDialogUI("/Sprites/UtilityPanels/ISSUE_PANE.png", result.getMessage(), parent));
        } catch (InvalidUILoadException ex) {
            throw new RuntimeException(ex);
        }
    }
}
