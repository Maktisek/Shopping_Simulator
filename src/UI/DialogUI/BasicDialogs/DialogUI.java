package UI.DialogUI.BasicDialogs;

import AudioSystem.AudioType;
import Game.GameData;
import UI.CreationUI.Buttons.ButtonType;
import UI.CreationUI.Buttons.CustomButton;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;

/**
 * This class represents a basic dialog.
 * <p>
 *     It features an exit button, which closes the dialog.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class DialogUI extends BaseDialogUI {

    protected CustomButton button;

    /**
     * This constructor represents the basic constructor
     * @param imgFile stands for the file path to the background image
     * @param message represents the message that will be displayed
     * @throws InvalidUILoadException if there is any problem while loading the image
     */
    public DialogUI(String imgFile, String message) throws InvalidUILoadException {
        super(imgFile, message);
        initializeButton();
    }

    /**
     * This constructor represents the constructor with a sound.
     * <p>
     *     If it is needed from the dialog to make a sound, then use this constructor.
     *     It is important to be aware of that dialog is not able to play music - it can only play sounds.
     * </p>
     * @param imgFile stands for the file path to the background image
     * @param message represents the message that will be displayed
     * @param sound is a title of the sound to be played.
     * @throws InvalidUILoadException if there is any problem while loading the image
     */
    public DialogUI(String imgFile, String message, String sound) throws InvalidUILoadException {
        super(imgFile, message);
        Important.getAudioManagement().playSound(sound, AudioType.SOUNDS, 0, false);
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

    /**
     * Initializes the action the button action.
     */
    public void buttonAction(){
        MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
        parent.hideDialog();
    }

}
