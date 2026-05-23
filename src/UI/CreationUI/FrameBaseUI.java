package UI.CreationUI;

import UI.Exceptions.InvalidUILoadException;
import UI.InitialUI.MyFrame;
import UI.TitleUI.TitleScreenUI;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public abstract class FrameBaseUI extends JFrame {

    private Timer resolutionChecker;
    private int check;

    public FrameBaseUI(String filePath) {
        this.check = Toolkit.getDefaultToolkit().getScreenSize().width * Toolkit.getDefaultToolkit().getScreenSize().height;
        iconLoader(filePath);
        loadResolutionChecker();
    }

    private void iconLoader(String filePath){
        URL iconURL = getClass().getResource(filePath);
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            setIconImage(icon.getImage());
        } else {
            System.err.println("Icon " + filePath + " was not found");
        }
    }

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

    public abstract void refreshUI() throws InvalidUILoadException;

    public Timer getResolutionChecker() {
        return resolutionChecker;
    }

    public void setResolutionChecker(Timer resolutionChecker) {
        this.resolutionChecker = resolutionChecker;
    }
}
