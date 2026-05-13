package UI.CreationUI;

import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import UI.TitleUI.TitleScreenUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class CustomButton extends JButton {

    protected Image img;
    protected boolean hoovered;
    protected boolean clicked;

    public CustomButton(){

    }

    public CustomButton(String imgFile, int width, int height) throws InvalidUILoadException {
        super();
        setImages(imgFile);

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);

        setSizeOfButton(width,height);

        setMouseListener();
    }

    public void setImages(String imgFile) throws InvalidUILoadException {
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
                CustomButton.this.hoovered = false;
                MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, CustomButton.this);
                if(parent != null) {
                    parent.resetCursor();
                }
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                CustomButton.this.hoovered = true;
                MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, CustomButton.this);
                if(parent != null) {
                    parent.setCursor();
                }
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                CustomButton.this.clicked = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                CustomButton.this.clicked = false;
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

}
