package UI.CreationUI.Buttons;

import UI.Exceptions.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * This class represents an abstract class made for changing buttons.
 * <p>
 *     A changing button is a button, which swaps its sprite after every click.
 * </p>
 * It swaps {@link #img} with those:
 * <ul>
 *     <li>{@link #idleImg}</li>
 *     <li>{@link #clickedImg}</li>
 * </ul>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public abstract class CustomBaseChangingButton extends BaseButton {

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

    /**
     * This method resets the cursor for this button back to normal cursor.
     */
    public void resetCursor(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImg = toolkit.getImage(getClass().getResource("/Sprites/MainSprites/MAIN_CURSOR.png"));
        Cursor customCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "cursorName");
        super.setCursor(customCursor);
    }

    /**
     * This method sets the cursor for this button into pointing cursor.
     */
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
