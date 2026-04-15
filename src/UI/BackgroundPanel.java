package UI;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BackgroundPanel extends JPanel {

    private Image img;

    public BackgroundPanel(String imgFile) throws InvalidUILoadException{
        super();
        loadImage(imgFile);
    }

    private void loadImage(String imgFile) throws InvalidUILoadException{
        URL imageURL = getClass().getResource(imgFile);

        if(imageURL == null){
            throw new InvalidUILoadException("The image "+ imgFile +" was not found");
        }

        this.img = new ImageIcon(imageURL).getImage();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }
}
