package UI.CreationUI.Buttons;

import Commands.AudioCommands.SwapMuteCommand;
import Commands.CommandResult;
import UI.Exceptions.InvalidUILoadException;
import Utilities.Important;

/**
 * This class represents a special way how to implement {@link ChangingButton} as a mute button.
 * <p>
 *     It is basically possible to write this directly in any of the UI classes, but in my opinion
 *     the code was little too long, so creating a class just for the creation looked like a good idea.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version
 */
public class MuteButton {

    private ChangingButton muteButton;

    public MuteButton() throws InvalidUILoadException {
        initialize();
    }

    /**
     * Initializes {@link #muteButton} based on the fact if the audio has been already paused.
     * <p>
     *     For instance, this can happen, when the sound had been paused in the menu and then the game was loaded.
     * </p>
     * @throws InvalidUILoadException if the process of creating an instance of {@link ChangingButton} went unsuccessful
     */
    private void initialize() throws InvalidUILoadException {
        if (Important.getAudioManagement().isMute()) {
            muteButton = new ChangingButton("/Sprites/ButtonSprites/MUTE_BUTTON.png", "/Sprites/ButtonSprites/UNMUTE_BUTTON.png", 100, 100);
        } else {
            muteButton = new ChangingButton("/Sprites/ButtonSprites/UNMUTE_BUTTON.png", "/Sprites/ButtonSprites/MUTE_BUTTON.png", 100, 100);
        }

        muteButton.addActionListener(e -> {
            CommandResult result = new SwapMuteCommand().execute();
            System.out.println(result.getMessage());
        });
    }

    public ChangingButton getMuteButton() {
        return muteButton;
    }
}
