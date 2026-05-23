package UI.CreationUI;

import UI.Exceptions.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public abstract class CustomBaseChangingButton extends BaseButton{

    protected Image img;
    protected Image idleImg;
    protected Image clickedImg;
    protected String imgFile;
    protected String clickedImgFile;

    public CustomBaseChangingButton(int width, int height, ButtonType type, String imgFile, String clickedImgFile) throws InvalidUILoadException {
        super(width, height, type);
        this.imgFile = imgFile;
        this.clickedImgFile = clickedImgFile;
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

    public void resetCursor(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImg = toolkit.getImage(getClass().getResource("/Sprites/MainSprites/MAIN_CURSOR.png"));
        Cursor customCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "cursorName");
        super.setCursor(customCursor);
    }

    public void setCursor(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImg = toolkit.getImage(getClass().getResource("/Sprites/TitleScreenSprites/CLICKED_CURSOR.png"));
        Cursor customCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "cursorName");
        super.setCursor(customCursor);
    }

    public void setMouseListener(){
        super.setMouseListener();
    }
}
