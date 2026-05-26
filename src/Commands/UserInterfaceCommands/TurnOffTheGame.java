package Commands.UserInterfaceCommands;

import AudioSystem.AudioType;
import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import UI.TitleUI.TitleScreenUI;
import Utilities.Important;

/**
 * This command represents a system of turning of the main game and loading back again the title screen.
 * <p>
 * {@link #mainUI} stands for the instance of {@link MainUI}, which should be turned off.
 * </p>
 * If there is a problem with loading the TitleScreenUI then {@link CommandResult} with {@link CommandState#FAILED_ISSUE} is returned
 * <p>
 *     Otherwise {@link CommandResult} with {@link CommandState#DONE} is returned
 * </p>
 * @author Matěj Pospíšil
 */
public class TurnOffTheGame extends Command {

    private final MainUI mainUI;

    public TurnOffTheGame(MainUI mainUI) {
        this.mainUI = mainUI;
    }

    @Override
    public CommandResult execute() {
        mainUI.turnOff();
        Important.getAudioManagement().getPaused().clear();
        TitleScreenUI title;
        try {
            Important.getAudioManagement().stopAll();
            Important.getAudioManagement().playSound("MenuOST", AudioType.MUSIC, 0);
            title = new TitleScreenUI();
        } catch (InvalidUILoadException ex) {
            return new CommandResult("There is a problem with loading TitleScreenUI", CommandState.FAILED_ISSUE);
        }
        title.makeVisible();
        return new CommandResult("The game was successfully turned off", CommandState.DONE);
    }
}
