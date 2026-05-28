package UI.CreationUI.Panels;

import UI.Exceptions.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * This class represents an implementation of {@link JPanel}.
 * <p>
 * It is designed that it draws an imagine as the background. The file path to the image is given in the constructor.
 * </p>
 * The image can be changed throughout the gameplay via {@link #setImg(String)} and this practise is commonly used.
 * <p>
 *     Use anytime creating a panel with a background image. If the image is not wanted, use classic {@link JPanel} or use
 *     empty constructor.
 * </p>
 * @author Michaela Meitnerová, Matěj Pospíšl
 * @since   1.0 - (pre-release version)
 */
public class BackgroundPanel extends JPanel {

    private Image img;

    public BackgroundPanel() {
        super();
    }

    public BackgroundPanel(String imgFile) throws InvalidUILoadException {
        super();
        loadImage(imgFile);
    }

    /**
     * This method loads {@link #img} through file path.
     * @param imgFile the file path to that image
     * @throws InvalidUILoadException if the image cannot be found
     */
    private void loadImage(String imgFile) throws InvalidUILoadException {
        URL imageURL = getClass().getResource(imgFile);

        if (imageURL == null) {
            throw new InvalidUILoadException("The image " + imgFile + " was not found");
        }

        this.img = new ImageIcon(imageURL).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    public void setImg(String image) throws InvalidUILoadException {
        loadImage(image);
    }

    /**
     * Sets the cursor to clicked cursor.
     * <p>
     *     This practise can look a bit misleading, when we are already changing the cursor in the button classes.
     *     But do not forget that this whole UI is made in swing and that sometimes the panel has higher priority than the button itself.
     *     Or even sometimes, when {@link JLayeredPane} is used, they overlap each other. It is must-have to change the cursor for the entire
     *     panel, otherwise the cursor may not be visible.
     * </p>
     */
    public void setCursor() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImg = toolkit.getImage(getClass().getResource("/Sprites/TitleScreenSprites/CLICKED_CURSOR.png"));
        Cursor customCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "cursorName");
        this.setCursor(customCursor);
    }

    /**
     * Sets the cursor to normal cursor.
     * <p>
     *     Used when exiting a button.
     * </p>
     */
    public void resetCursor() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImg = toolkit.getImage(getClass().getResource("/Sprites/MainSprites/MAIN_CURSOR.png"));
        Cursor customCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "cursorName");
        this.setCursor(customCursor);
    }

}
