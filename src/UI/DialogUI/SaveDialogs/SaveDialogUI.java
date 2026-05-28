package UI.DialogUI.SaveDialogs;

import Commands.CommandResult;
import Commands.SaveCommands.WriteSaveCommand;
import Game.GameData;
import UI.DialogUI.BasicDialogs.DialogUI;
import UI.DialogUI.DecisionDialogs.YesNoDialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;

import javax.swing.*;

public class SaveDialogUI extends YesNoDialogUI {

    private final GameData gameData;

    public SaveDialogUI(String message, GameData gameData) throws InvalidUILoadException {
        super(message);
        this.gameData = gameData;
    }

    @Override
    public void initializeYesButton() {
        CommandResult result = new WriteSaveCommand(gameData).execute();
        MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
        try {
            parent.hideDialog();
            parent.showDialog(new DialogUI("/Sprites/UtilityPanels/ISSUE_PANE.png", result.getMessage(), "Save"));
        } catch (InvalidUILoadException ex) {
            throw new RuntimeException(ex);
        }
    }
}
