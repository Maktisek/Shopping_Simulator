package UI.CreationUI.Buttons;

import UI.Exceptions.InvalidUILoadException;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class represents a classic changing button, which extends {@link CustomBaseChangingButton}.
 * <p>
 *     When this button is clicked, the image switches and stays until the player do not let left mouse button.
 *     After that, {@link #img} switches back to {@link #idleImg}.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class CustomTitleButton extends CustomBaseChangingButton {


    public CustomTitleButton(String imgFile, String clickedImg, int width, int height, ButtonType type) throws InvalidUILoadException {
        super(width, height, type, imgFile, clickedImg);
        this.imgFile = imgFile;
        this.clickedImgFile = clickedImg;
        setImages();
    }


    @Override
    public void setMouseListener(){
        super.setMouseListener();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                CustomTitleButton.this.hoovered = false;
                resetCursor();
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                CustomTitleButton.this.hoovered = true;
                setCursor();
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                CustomTitleButton.this.img = clickedImg;
                CustomTitleButton.this.clicked = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                CustomTitleButton.this.img = idleImg;
                CustomTitleButton.this.clicked = false;
                repaint();
            }
        });
    }
}

