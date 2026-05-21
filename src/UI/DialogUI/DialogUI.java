package UI.DialogUI;

import AudioSystem.AudioType;
import UI.CreationUI.ButtonType;
import UI.CreationUI.CustomButton;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;


public class DialogUI extends BaseDialogUI {

    protected CustomButton button;

    public DialogUI(String imgFile, String message) throws InvalidUILoadException {
        super(imgFile, message);
        initializeButton();
    }

    public DialogUI(String imgFile, String message, String sound) throws InvalidUILoadException {
        super(imgFile, message);
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
        MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
        parent.hideDialog();
    }

}
