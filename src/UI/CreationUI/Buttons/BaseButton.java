package UI.CreationUI.Buttons;

import AudioSystem.AudioType;
import UI.Exceptions.InvalidUILoadException;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class represents an abstract clas made for buttons and it extends {@link JButton}.
 * <p>
 *     All future button classes should extend this class in some way.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public abstract class BaseButton extends JButton {

    protected boolean hoovered;
    protected boolean clicked;

    public BaseButton(int width, int height, ButtonType type) {
        setSizeOfButton(width, height);
        initializeSound(type);
        setMouseListener();
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }

    public BaseButton() {
    }

    /**
     * Based on the input it initializes basic factory click sound.
     * <p>
     *     If the input does not match any of the options in the switch, then no sound is added.
     * </p>
     * @param type the type of the button that determines the sound
     */
    private void initializeSound(ButtonType type) {
        this.addActionListener(e -> {
            switch (type) {
                case EXIT -> Important.getAudioManagement().playSound("ExitClick", AudioType.SOUNDS, 0, false);
                case ENTER -> Important.getAudioManagement().playSound("EnterClick", AudioType.SOUNDS, 0, false);
            }
        });
    }

    /**
     * This abstract method is made to set images of any inherited class.
     * @throws InvalidUILoadException if the image load has not been successful
     */
    public abstract void setImages() throws InvalidUILoadException;

    public void setSizeOfButton(int width, int height) {
        Dimension dimension = new Dimension(Important.calculateDimension(width), Important.calculateDimension(height));
        setMaximumSize(dimension);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
    }

    public void setMouseListener(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                Important.getAudioManagement().playSound("ButtonPoint", AudioType.SOUNDS, 0, false);
            }
        });
    }

    /**
     * Calculates offset of the button.
     * <p>
     *     The higher, the smaller the button is.
     * </p>
     * @return the offset
     */
    public int calculateOffset() {
        if (clicked) {
            return 5;
        }
        if (hoovered) {
            return 0;
        }
        return 5;
    }
}
