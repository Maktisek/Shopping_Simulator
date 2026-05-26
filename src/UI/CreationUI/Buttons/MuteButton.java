package UI.CreationUI.Buttons;

import AudioSystem.AudioType;
import Commands.AudioCommands.SwapMuteCommand;
import Commands.CommandResult;
import Game.GameData;
import UI.Exceptions.InvalidUILoadException;
import Utilities.Important;

public class MuteButton {

    private ChangingButton muteButton;
    private boolean special;
    private final String title;


    public MuteButton(boolean special, String title) throws InvalidUILoadException {
        this.special = special;
        this.title = title;
        initialize();
    }

    private void initialize() throws InvalidUILoadException {
        if (Important.getAudioManagement().isMute()) {
            muteButton = new ChangingButton("/Sprites/ButtonSprites/MUTE_BUTTON.png", "/Sprites/ButtonSprites/UNMUTE_BUTTON.png", 100, 100);
        } else {
            muteButton = new ChangingButton("/Sprites/ButtonSprites/UNMUTE_BUTTON.png", "/Sprites/ButtonSprites/MUTE_BUTTON.png", 100, 100);
        }

        muteButton.addActionListener(e -> {
            if (this.special) {
                this.special = false;
                Important.getAudioManagement().playSound(title, AudioType.MUSIC, 0);
            }
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
