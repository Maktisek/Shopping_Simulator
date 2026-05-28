package UI.CreationUI.Frames;

import UI.Exceptions.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

/**
 * This class represents an abstract class, from which all classes managing frame should extend.
 * <p>
 *     This class extends {@link JFrame} itself, but adds one new function:
 * </p>
 * Resolution check system, which makes the game adapt to current monitor resolution settings.
 * It is not perfect, but it works.
 * <p>
 *     {@link #check} stands for a value, which is used to check if the resolution has not changed. It is basically
 *     width of the screen times height of the screen.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public abstract class FrameBaseUI extends JFrame {

    private Timer resolutionChecker;
    private int check;

    public FrameBaseUI(String filePath) {
        this.check = Toolkit.getDefaultToolkit().getScreenSize().width * Toolkit.getDefaultToolkit().getScreenSize().height;
        iconLoader(filePath);
        loadResolutionChecker();
    }

    /**
     * Sets the window icon from file path input
     * @param filePath the file path to the icon to be set
     */
    private void iconLoader(String filePath){
        URL iconURL = getClass().getResource(filePath);
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            setIconImage(icon.getImage());
        } else {
            System.err.println("Icon " + filePath + " was not found");
        }
    }

    /**
     * This method initializes {@link #resolutionChecker}, which every {@code 500} milliseconds check if the resolution has not changed.
     * It checks if the resolution has changed by comparing {@link #check} with new calculated value.
     * <p>
     *     If the resolution has changed, then {@link #refreshUI()} is executed.
     * </p>
     * <p>
     *     Also there is initial delay, because when the window is opening itself it could detect a change and do some buggy stuff.
     *     The initial delay is set to {@code 2000} milliseconds
     * </p>
     */
    private void loadResolutionChecker() {
        this.resolutionChecker = new Timer(500, e -> {
            int newValue = Toolkit.getDefaultToolkit().getScreenSize().width * Toolkit.getDefaultToolkit().getScreenSize().height;
            if (check != newValue) {
                this.check = newValue;
                try {
                    refreshUI();
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        this.resolutionChecker.setInitialDelay(2000);
        this.resolutionChecker.start();
    }

    public void stopTimer() {
        this.resolutionChecker.stop();
    }

    /**
     * This abstract method represents a system, which refreshes the frame.
     * <p>
     *     Any extending class has to implement this method and implement it in a way that
     *     the frame is refreshed.
     * </p>
     * @throws InvalidUILoadException if there is some inner issue (loading back assets and more)
     */
    public abstract void refreshUI() throws InvalidUILoadException;
}
