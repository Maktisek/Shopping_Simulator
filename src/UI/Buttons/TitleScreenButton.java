package UI.Buttons;

import UI.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class TitleScreenButton extends JButton {

    private final Image img;

    public TitleScreenButton(String imgFile, int width, int height) throws InvalidUILoadException{
        super();
        URL imageURL = getClass().getResource(imgFile);

        if(imageURL == null){
            throw new InvalidUILoadException("The image "+ imgFile +" was not found");
        }

        this.img = new ImageIcon(imageURL).getImage();

        setContentAreaFilled(false);
        setBorderPainted(false);

        Dimension dimension = new Dimension(width, height);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
    }

    @Override
    protected void paintComponent(Graphics g){
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        super.paintComponent(g);
    }
}
