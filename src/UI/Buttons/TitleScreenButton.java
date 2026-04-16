package UI.Buttons;

import UI.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class TitleScreenButton extends JButton {

    private Image img;
    private final Image idleImg;
    private final Image clickedImg;
    private boolean hoovered;

    public TitleScreenButton(String imgFile,String clickedImg, int width, int height) throws InvalidUILoadException{
        super();
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

        setContentAreaFilled(false);
        setBorderPainted(false);

        setSizeOfButton(width,height);

        setMouseListener();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        int w = getWidth();
        int h = getHeight();

        if (hoovered) {
            g.drawImage(img, 0, 0, w, h, this);
        } else {
            int offset = 5;
            g.drawImage(img, offset, offset, w - (offset * 2), h - (offset * 2), this);
        }
    }

    private void setMouseListener(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                TitleScreenButton.this.hoovered = false;
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                TitleScreenButton.this.hoovered = true;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                TitleScreenButton.this.img = clickedImg;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                TitleScreenButton.this.img = idleImg;
                repaint();
            }
        });
    }

    private void setSizeOfButton(int width, int height){
        Dimension dimension = new Dimension(width, height);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
    }
}
