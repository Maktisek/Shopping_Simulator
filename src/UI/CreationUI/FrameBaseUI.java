package UI.CreationUI;

import UI.Exceptions.InvalidUILoadException;
import UI.InitialUI.MyFrame;
import UI.TitleUI.TitleScreenUI;

import javax.swing.*;
import java.awt.*;

public abstract class FrameBaseUI extends JFrame{

    private Timer resolutionChecker;
    private int check;

    public FrameBaseUI() {
        this.check = Toolkit.getDefaultToolkit().getScreenSize().width *  Toolkit.getDefaultToolkit().getScreenSize().height;
        loadResolutionChecker();
    }

    private void loadResolutionChecker(){
            this.resolutionChecker = new Timer(500, e -> {
                int newValue = Toolkit.getDefaultToolkit().getScreenSize().width *  Toolkit.getDefaultToolkit().getScreenSize().height;
                if (check != newValue){
                    this.check = newValue;
                    try {
                        refreshUI();
                    } catch (InvalidUILoadException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            this.resolutionChecker.start();
    }

    public void stopTimer(){
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
