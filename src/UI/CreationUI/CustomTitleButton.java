package UI.CreationUI;

import UI.Exceptions.InvalidUILoadException;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class CustomTitleButton extends JButton {

    protected Image img;
    protected Image idleImg;
    protected Image clickedImg;
    protected boolean hoovered;
    protected boolean clicked;

    public CustomTitleButton(){

    }

    public CustomTitleButton(String imgFile, String clickedImg, int width, int height) throws InvalidUILoadException {
        super();
        setImages(imgFile, clickedImg);

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);

        setSizeOfButton(width,height);

        setMouseListener();
        setCursor();
    }

    public void setImages(String imgFile, String clickedImg) throws InvalidUILoadException {
        URL imageURL = getClass().getResource(imgFile);
        URL clickedURL = getClass().getResource(clickedImg);

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

    private int calculateOffset(){
        if(clicked){
            return 5;
        }
        if(hoovered){
            return 0;
        }
        return 5;
    }

    protected void setMouseListener(){
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

    private void setSizeOfButton(int width, int height){
        Dimension dimension = new Dimension(Important.calculateDimension(width), Important.calculateDimension(height));
        setMaximumSize(dimension);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
    }

    public void setCursor(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImg = toolkit.getImage(getClass().getResource("/Sprites/TitleScreenSprites/CLICKED_CURSOR.png"));
        Cursor customCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "cursorName");
        this.setCursor(customCursor);
    }
}

