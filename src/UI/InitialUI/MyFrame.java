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
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
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
     *     Well, I was trying to find a solution, how to simulate fullscreen without it actually being fullscreen, because when
     *     actual fullscreen was on and the player changed the resolution it made a mess.
     * </p>
     * This solution works pretty well. When the window is minimized and then opened, the window resets its position
     * and spreads across the whole screen. But the OS of the computer
     * do not count this window as a fullscreen window.
     * <p>
     *     If this solution was not applied those things would happen:
     *     <ul>
     *         <li>After minimizing, the window would not be able to be opened again (when no solution applied)</li>
     *         <li>After changing resolution, the whole OS UI may go pretty broken, making hard reset the only solution (if the window
     *         would be set as a fullscreen window through {@link GraphicsEnvironment})</li>
     *     </ul>
     * </p>
     */
    private void refreshFullscreen() {
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void makeVisible() {
        setVisible(true);
    }
}
