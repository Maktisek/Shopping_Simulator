package UI.CreationUI.Scrollers;

import UI.Exceptions.InvalidUILoadException;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.net.URL;

/**
 * This class represents an implementation of {@link BasicScrollBarUI}.
 * <p>
 *     This class sets and renders images for scrollers.
 * </p>
 * There are:
 * <ul>
 *     <li>{@link #thumbImage} that stands for the "thumb"</li>
 *     <li>{@link #trackImage} that stands for the "track" </li>
 * </ul>
 * Both images are set in constructor and
 * file path does not have to be inserted to the constructors parameters.
 * @author Google Gemini
 * @since   1.0 - (pre-release version)
 */
public class CustomScrollBarUI extends BasicScrollBarUI {

    private final Image thumbImage;
    private final Image trackImage;

    public CustomScrollBarUI() throws InvalidUILoadException {
        this.thumbImage = loadImage("/Sprites/MiscSprites/SCROLL.png");
        this.trackImage = loadImage("/Sprites/MiscSprites/BAR.png");
    }

    private Image loadImage(String filePath) throws InvalidUILoadException {
        URL imageURL = getClass().getResource(filePath);

        if (imageURL == null) {
            throw new InvalidUILoadException("The image " + filePath + " was not found");
        }

        return new ImageIcon(imageURL).getImage();

    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbImage != null) {
            g.drawImage(thumbImage, thumbBounds.x, thumbBounds.y,
                    thumbBounds.width, thumbBounds.height, null);
        }
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        if (trackImage != null) {
            g.drawImage(trackImage, trackBounds.x, trackBounds.y,
                    trackBounds.width, trackBounds.height, null);
        }
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        return button;
    }
}