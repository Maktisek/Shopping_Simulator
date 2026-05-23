package UI.InitialUI;

import Game.GameData;
import UI.CreationUI.FrameBaseUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends FrameBaseUI {

    private final GameData gameData;
    private MainUI mainUI;

    public MyFrame(GameData gameData) throws InvalidUILoadException{
        super("/Sprites/IconSprites/MAIN_ICON.png");
        setTitle("Forest Market");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        this.gameData = gameData;
        initialize();
    }

    @Override
    public void refreshUI() throws InvalidUILoadException {
        Important.getAudioManagement().pauseAll();
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
    }

    public void makeVisible() {
        setVisible(true);
    }
}
