package UI.InitialUI;

import Game.GameData;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{

    private final GameData gameData;
    private Timer resolutionChecker;
    private int check;
    private MainUI mainUI;

    public MyFrame(GameData gameData) throws InvalidUILoadException{
        setTitle("Forest Market");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        this.gameData = gameData;
        this.check = Toolkit.getDefaultToolkit().getScreenSize().width *  Toolkit.getDefaultToolkit().getScreenSize().height;
        initialize();
    }

    private void initializeResolutionChecker(){
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

    private void refreshUI() throws InvalidUILoadException {
        this.getContentPane().removeAll();
        this.mainUI.stopAllTimers();
        this.mainUI = new MainUI(this.gameData);
        this.getContentPane().add(mainUI, BorderLayout.CENTER);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }

    private void initialize() throws InvalidUILoadException {
        this.mainUI = new MainUI(gameData);
        getContentPane().add(this.mainUI, BorderLayout.CENTER);
        initializeResolutionChecker();
    }

    public void makeVisible() {
        setVisible(true);
    }

    public Timer getResolutionChecker() {
        return resolutionChecker;
    }
}
