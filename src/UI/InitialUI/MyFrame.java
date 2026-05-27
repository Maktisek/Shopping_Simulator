package UI.InitialUI;

import Game.GameData;
import UI.CreationUI.Frames.FrameBaseUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public class MyFrame extends FrameBaseUI {

    private final GameData gameData;
    private MainUI mainUI;

    public MyFrame(GameData gameData) throws InvalidUILoadException {
        super("/Sprites/IconSprites/MAIN_ICON.png");
        setTitle("Forest Market");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);


        this.gameData = gameData;
        initialize();
    }

    @Override
    public void refreshUI() throws InvalidUILoadException {
        Important.getAudioManagement().pauseAllMusic();
        this.getContentPane().removeAll();
        this.mainUI.stopAllTimers();
        this.mainUI = new MainUI(this.gameData);
        this.getContentPane().add(mainUI, BorderLayout.CENTER);
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }

    private void initialize() throws InvalidUILoadException {
        initializeWindowOperations();
        this.mainUI = new MainUI(gameData);
        getContentPane().add(this.mainUI, BorderLayout.CENTER);
    }

    private void initializeWindowOperations() {
        this.addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if(e.getNewState() != 0) {
                    refreshFullscreen();
                }
            }
        });
    }

    private void refreshFullscreen() {
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void makeVisible() {
        setVisible(true);
    }
}
