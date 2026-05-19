package UI.DialogUI;

import Commands.CommandResult;
import Commands.CommandState;
import Commands.RebirthCommands.NewRebirthCommand;
import Game.GameData;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;

import javax.swing.*;

public class NewRebirthDialog extends BuyDialogUI{

    private final GameData gameData;

    public NewRebirthDialog(String message, GameData gameData) throws InvalidUILoadException {
        super(message);
        this.gameData = gameData;
    }

    @Override
    public void initializeBuyButton() {
        CommandResult result = new NewRebirthCommand(gameData).execute();
        System.out.println(result.getMessage());

        MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
        if(result.getState() == CommandState.FAILED_ISSUE){
            try {
                parent.hideDialog();
                parent.showDialog(new DialogUI("/ShopSprites/ISSUE_PANE.png",result.getMessage()));
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
        }else {
            try {
                parent.hideDialog();
                parent.stopAllTimers();
                parent.showDialog(new RefreshFrameDialogUI("/ShopSprites/ISSUE_PANE.png",result.getMessage()));
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
