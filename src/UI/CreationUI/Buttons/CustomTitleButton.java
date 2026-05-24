package UI.CreationUI.Buttons;

import UI.Exceptions.InvalidUILoadException;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


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
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                CustomTitleButton.this.hoovered = true;
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

    public void setCursor(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImg = toolkit.getImage(getClass().getResource("/Sprites/TitleScreenSprites/CLICKED_CURSOR.png"));
        Cursor customCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "cursorName");
        this.setCursor(customCursor);
    }
}

