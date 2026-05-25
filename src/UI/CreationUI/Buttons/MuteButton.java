package UI.CreationUI.Buttons;

import Commands.AudioCommands.SwapMuteCommand;
import Commands.CommandResult;
import UI.Exceptions.InvalidUILoadException;
import Utilities.Important;

public class MuteButton{

    private ChangingButton muteButton;

    public MuteButton() throws InvalidUILoadException {
        initialize();
    }

    private void initialize() throws InvalidUILoadException {
        if(Important.getAudioManagement().isMute()){
            muteButton = new ChangingButton("/Sprites/ButtonSprites/MUTE_BUTTON.png", "/Sprites/ButtonSprites/UNMUTE_BUTTON.png", 100, 100);
        }else {
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

    public void setMuteButton(ChangingButton muteButton) {
        this.muteButton = muteButton;
    }
}
