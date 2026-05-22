package UI.DialogUI;

import AudioSystem.AudioType;
import Game.GameData;
import UI.CreationUI.ButtonType;
import UI.CreationUI.CustomButton;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;


public class DialogUI extends BaseDialogUI {

    protected CustomButton button;
    private GameData gameData;

    public DialogUI(String imgFile, String message) throws InvalidUILoadException {
        super(imgFile, message);
        initializeButton();
    }


    public DialogUI(String imgFile, String message, String sound) throws InvalidUILoadException {
        super(imgFile, message);
        Important.getAudioManagement().playSound(sound, AudioType.SOUNDS, 0);
        initializeButton();
    }

    public DialogUI(String imgFile, String message, String sound, GameData gameData) throws InvalidUILoadException {
        super(imgFile, message);
        this.gameData = gameData;
        Important.getAudioManagement().playSound(sound, AudioType.SOUNDS, 0);
        initializeButton();
    }

    protected void initializeButton() throws InvalidUILoadException {
        add(Box.createVerticalStrut(Important.calculateDimension(20)));

        button = new CustomButton("/Sprites/ButtonSprites/OK_BUTTON.png", 130, 75, ButtonType.EXIT);

        button.addActionListener(e ->{
            buttonAction();
        });

        button.setAlignmentX(CENTER_ALIGNMENT);
        add(button);
    }

    public void buttonAction(){
        if(gameData != null){
            Important.getAudioManagement().resumeSound(gameData.getShopManagement().getCurrentShop().getName(), AudioType.MUSIC);
        }
        MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
        parent.hideDialog();
    }

}
