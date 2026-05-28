package UI.DialogUI.DecisionDialogs;

import Commands.CommandResult;
import Commands.UserInterfaceCommands.TurnOffTheGameCommand;
import Game.GameData;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;

/**
 * This class is an implementation of {@link YesNoDialogUI}.
 * <p>
 *     If yes button is clicked, {@link TurnOffTheGameCommand} is executed.
 * </p>
 * <p>
 *     An instance of {@link MainUI} has to be sent through constructor.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class TurnOffTheGameDialogUI extends YesNoDialogUI {
    private final MainUI mainUI;

    public TurnOffTheGameDialogUI(String message, MainUI mainUI) throws InvalidUILoadException {
        super(message);
        this.mainUI = mainUI;
    }

    @Override
    public void initializeYesButton() {
        CommandResult result = new TurnOffTheGameCommand(mainUI).execute();
        System.out.println(result.getMessage());
    }
}
