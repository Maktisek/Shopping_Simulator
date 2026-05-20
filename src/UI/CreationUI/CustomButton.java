package UI.CreationUI;

import AudioSystem.AudioType;
import Game.GameData;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class CustomButton extends BaseButton {

    protected String imgFile;
    protected Image img;

    public CustomButton(){
        super();
    }

    public CustomButton(String imgFile, int width, int height) throws InvalidUILoadException {
        super(width, height);
        this.imgFile = imgFile;
        setImages();
    }

    public CustomButton(String imgFile, int width, int height, ButtonType buttonType) throws InvalidUILoadException {
        super(width, height, buttonType);
        this.imgFile = imgFile;
        setImages();
    }

    @Override
    public void setImages() throws InvalidUILoadException {
        URL imageURL = getClass().getResource(imgFile);

        if(imageURL == null){
            throw new InvalidUILoadException("The image "+ imgFile +" was not found");
        }

        this.img = new ImageIcon(imageURL).getImage();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        int w = getWidth();
        int h = getHeight();

        int offset2 = calculateOffset();
        g.drawImage(img, offset2, offset2, w - (offset2 * 2), h - (offset2 * 2), this);


    }

    @Override
    public void setMouseListener(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                CustomButton.super.hoovered = false;
                MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, CustomButton.this);
                if(parent != null) {
                    parent.resetCursor();
                }
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                CustomButton.super.hoovered = true;
                MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, CustomButton.this);
                if(parent != null) {
                    parent.setCursor();
                }
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                CustomButton.super.clicked = true;
                MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, CustomButton.this);
                if(parent != null) {
                    parent.resetCursor();
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                CustomButton.super.clicked = false;
                repaint();
            }
        });
    }

    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }
}
