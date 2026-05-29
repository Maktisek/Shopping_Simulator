package UI.DialogUI.DecisionDialogs;

import AudioSystem.AudioType;
import Commands.CommandResult;
import Commands.CommandState;
import Commands.RebirthCommands.NewRebirthCommand;
import Commands.UpgradeCommands.UpgradeCommand;
import Game.GameData;
import UI.DialogUI.BasicDialogs.DialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Upgrade.Utilities.UpgradeType;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

/**
 * This class is an implementation of {@link YesNoDialogUI}.
 * <p>
 *     If yes button is clicked, {@link UpgradeCommand} with check set on {@code false} is executed.
 * </p>
 * <p>
 *     An instance of {@link GameData} has to be sent through constructor.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class ContinueUpgradeDialogUI extends YesNoDialogUI {

    private final GameData gameData;
    private final UpgradeType name;

    public ContinueUpgradeDialogUI(String message, GameData gameData, UpgradeType name) throws InvalidUILoadException {
        super(message);
        this.gameData = gameData;
        this.name = name;
        Important.getAudioManagement().playSound("Error", AudioType.SOUNDS,0, false);
    }

    @Override
    public void initializeYesButton() {
        CommandResult result = new UpgradeCommand(gameData, name, false).execute();
        System.out.println(result.getMessage());
        MainUI parentShop = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
        if (result.getState() == CommandState.FAILED_ISSUE) {
            try {
                parentShop.showDialog(new DialogUI("/Sprites/UtilityPanels/ISSUE_PANE.png", result.getMessage(), "Error"));
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
        } else if (result.getState() == CommandState.DONE) {
            parentShop.hideDialog();
            Important.getAudioManagement().playSound("NewUpgrade", AudioType.SOUNDS, 0, false);
        }
    }
}
