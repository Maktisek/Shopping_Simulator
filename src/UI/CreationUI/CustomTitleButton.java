package UI.CreationUI;

import UI.Exceptions.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;


public class CustomTitleButton extends BaseButton {

    protected Image img;
    protected Image idleImg;
    protected Image clickedImg;
    protected String imgFile;
    protected String clickedImgFile;

    public CustomTitleButton(){

    }

    public CustomTitleButton(String imgFile, String clickedImg, int width, int height, ButtonType type) throws InvalidUILoadException {
        super(width, height, type);
        this.imgFile = imgFile;
        this.clickedImgFile = clickedImg;
        setImages();
    }

    @Override
    public void setImages() throws InvalidUILoadException {
        URL imageURL = getClass().getResource(imgFile);
        URL clickedURL = getClass().getResource(clickedImgFile);

        if(imageURL == null){
            throw new InvalidUILoadException("The image "+ imgFile +" was not found");
        }

        if(clickedURL == null){
            throw new InvalidUILoadException("The image "+ imgFile +" was not found");
        }

        this.idleImg = new ImageIcon(imageURL).getImage();
        this.clickedImg = new ImageIcon(clickedURL).getImage();

        this.img = idleImg;
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

