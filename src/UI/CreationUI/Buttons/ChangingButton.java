package UI.CreationUI.Buttons;

import AudioSystem.AudioType;
import Game.GameData;
import UI.Exceptions.InvalidUILoadException;
import Utilities.Important;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class represents a classic changing button, which extends {@link CustomBaseChangingButton}.
 * <p>
 *     When this button is clicked, the image switches.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class ChangingButton extends CustomBaseChangingButton {


    public ChangingButton(String filePathOne, String filePathTwo,int width, int height) throws InvalidUILoadException {
        super(width, height, ButtonType.ENTER, filePathOne, filePathTwo);
        initialization();
    }

    @Override
    public void setMouseListener() {
        super.setMouseListener();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                    Important.getAudioManagement().playSound("ButtonPoint", AudioType.SOUNDS, 0, false);
                    setCursor();
                    ChangingButton.super.hoovered = true;
                    repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                resetCursor();
                ChangingButton.super.hoovered = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                ChangingButton.super.clicked = !ChangingButton.super.clicked;
            }
        });
    }

    /**
     * Swapes {@link #img} to the first image.
     * <p>
     *     Called when {@link #clicked} was true.
     * </p>
     */
    public void changeToFirst(){
        this.clicked = false;
        this.img = super.idleImg;
        repaint();
    }

    /**
     * Swapes {@link #img} to the second image.
     * <p>
     *     Called when {@link #clicked} was false.
     * </p>
     */
    public void changeToSecond(){
        super.img = clickedImg;
        super.clicked = true;
        repaint();
    }

    /**
     * Adds initial action listener, which operates with {@link #img}.
     * <p>
     *     Basically it switches the image after every click.
     * </p>
     */
    public void initialization(){
        addActionListener(e ->{
            if(clicked){
                changeToSecond();
            }else {
                changeToFirst();
            }
        });
    }

    @Override
    public int calculateOffset() {
        if (hoovered) {
            return 0;
        }
        return 5;
    }
}
