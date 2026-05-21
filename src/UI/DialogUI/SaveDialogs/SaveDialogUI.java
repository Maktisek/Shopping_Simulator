package UI.DialogUI.SaveDialogs;

import Commands.CommandResult;
import Commands.SaveCommands.WriteSaveCommand;
import Game.GameData;
import UI.DialogUI.DialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;

import javax.swing.*;

public class SaveDialogUI extends SaveBaseDialogUI {


    public SaveDialogUI(String imgFile, String message, GameData gameData) throws InvalidUILoadException {
        super(imgFile, message, gameData);
    }


    @Override
    public void loadSaveButton() {
        CommandResult result = new WriteSaveCommand(super.gameData).execute();
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            try {
                parent.hideDialog();
                parent.showDialog(new DialogUI("/Sprites/UtilityPanels/ISSUE_PANE.png", result.getMessage(), "Save"));
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
    }
}
