package Commands.AudioCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Utilities.Important;

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
