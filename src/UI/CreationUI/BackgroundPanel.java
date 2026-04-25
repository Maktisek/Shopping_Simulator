package UI.CreationUI;

import UI.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BackgroundPanel extends JPanel {

    private Image img;

    public BackgroundPanel(){
        super();
        setCursor();
    }

    public BackgroundPanel(String imgFile) throws InvalidUILoadException {
        super();
        loadImage(imgFile);
        setCursor();
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

    private void setCursor(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImg = toolkit.getImage(getClass().getResource("/MainUI/MAIN_CURSOR.png"));
        Cursor customCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "cursorName");
        this.setCursor(customCursor);
    }

    public void setImg(String image) throws InvalidUILoadException{
        loadImage(image);
    }
}
