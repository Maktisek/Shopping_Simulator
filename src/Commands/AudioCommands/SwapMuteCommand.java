package Commands.AudioCommands;

import AudioSystem.AudioManagement;
import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Utilities.Important;

/**
 * This command represent a system, which mutes and unmutes audios.
 * <p>
 *     It swapes {@link AudioManagement#getPaused()}.
 * </p>
 * <p>
 *     When the audio is muted after the swap then all music is stopped.
 * </p>
 * <p>
 *     When the audio is unmuted after the swap then all music is resumed.
 * </p>
 * @author Matěj Pospíšil
 */
public class SwapMuteCommand extends Command {

    @Override
    public CommandResult execute() {
        Important.getAudioManagement().setMute(!Important.getAudioManagement().isMute());
        if(Important.getAudioManagement().isMute()){
            Important.getAudioManagement().pauseAllMusic();
        }else {
            Important.getAudioManagement().resumeAll();
        }
        return new CommandResult("Swapped mute: " + Important.getAudioManagement().isMute(), CommandState.DONE);
    }
}
