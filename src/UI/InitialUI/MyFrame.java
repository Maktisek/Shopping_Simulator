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

/**
 * This class represents the main frame where the main game is being displayed.
 * <p>
 *     It extends {@link FrameBaseUI} so it behaves like a {@link JFrame}.
 * </p>
 * For initializing {@link #mainUI} a proper instance of {@link GameData} has to be inserted.
 * If the given instance of {@link GameData} is not filled by data, the window will not open.
 */
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

    /**
     * This method initializes a window state listener.
     * <p>
     *     This window state listener listens to any behaviour of this frame.
     *     If there is any action with the frame and the action is not minimizing the window,
     *     method {@link #refreshUI()} is executed.
     * </p>
     */
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

    /**
     * This method refreshes the window into "fullscreen", even tough it is not actual fullscreen.
     * <p>
     *     Well, I was trying to find a solution, how to simulate fullscreen without it actually being full screen, because when
     *     actual full screen was on, when changing resolution it was making a mess.
     * </p>
     * This solution works pretty well. When the window is minimized and then again opened, the window resets its position
     * and spreads across the whole screen. Otherwise, the window would be unopenable after minimizing it.
     */
    private void refreshFullscreen() {
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void makeVisible() {
        setVisible(true);
    }
}
