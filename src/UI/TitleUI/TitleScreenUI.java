package UI.TitleUI;

import Commands.CommandResult;
import Commands.CommandState;
import Commands.SaveCommands.LoadSaveCommand;
import Game.GameData;
import Game.Initialization;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.CustomTitleButton;
import UI.DialogUI.TitleDialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.InitialUI.MyFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class TitleScreenUI extends JFrame {

    private JLayeredPane layeredPane;
    private BackgroundPanel background;
    private JPanel overlay;

    public TitleScreenUI() throws InvalidUILoadException {
        this.setTitle("Forest Market Launcher");

        this.setSize(600, 600);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(600, 600));
        this.setResizable(false);
        initialize();
    }

    public void makeVisible() {
        this.pack();
        this.setVisible(true);
    }

    private void initialize() throws InvalidUILoadException {
        initializeBackground();
        initializeOverlay();
        initializeLayerPane();
        setCursor();
    }

    private void initializeBackground() throws InvalidUILoadException {
        this.background = new BackgroundPanel("/TitleScreenUI/BACKGROUND.png");
        this.background.setLayout(new BorderLayout());
        initializeTitle();
        initializeMainPanel();
    }

    private void initializeTitle() throws InvalidUILoadException {
        URL imageURL = getClass().getResource("/TitleScreenUI/TITLE_SCREEN_NAME.png");

        if (imageURL == null) {
            throw new InvalidUILoadException("The title screen name image was not found");
        }

        ImageIcon icon = new ImageIcon(imageURL);
        JLabel label = new JLabel(icon, JLabel.CENTER);
        label.setBorder(new EmptyBorder(50, 0, 0, 0));
        label.setOpaque(false);

        this.background.add(label, BorderLayout.NORTH);
    }

    private void initializeMainPanel() throws InvalidUILoadException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 40));

        CustomTitleButton newGame = new CustomTitleButton("/TitleScreenUI/NEW_GAME_BUTTON.png", "/TitleScreenUI/NEW_GAME_BUTTON_CLICKED.png", 200, 100);
        CustomTitleButton loadGame = new CustomTitleButton("/TitleScreenUI/LOAD_BUTTON.png", "/TitleScreenUI/LOAD_BUTTON_CLICKED.png", 200, 100);
        CustomTitleButton quit = new CustomTitleButton("/TitleScreenUI/QUIT_BUTTON.png", "/TitleScreenUI/QUIT_BUTTON_CLICKED.png", 200, 100);

        newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(18));
        panel.add(newGame);
        panel.add(Box.createVerticalStrut(12));
        panel.add(loadGame);
        panel.add(Box.createVerticalStrut(12));
        panel.add(quit);

        this.background.add(panel, BorderLayout.CENTER);

        newGame.addActionListener(e -> {
            try {
                MyFrame myFrame = new MyFrame(new Initialization().getGameData());
                myFrame.show();
                this.dispose();
            } catch (InvalidUILoadException ex) {
                System.err.println(ex.getMessage());
            }
        });

        loadGame.addActionListener(e -> {
            GameData gameData = new GameData();
            CommandResult result = new LoadSaveCommand(gameData).execute();
            if (result.getState() == CommandState.FAILED_ISSUE) {
                try {
                    showDialog(new TitleDialogUI("/TitleScreenUI/TITLE_SCREEN_DIALOG.png", result.getMessage()));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    MyFrame myFrame = new MyFrame(gameData);
                    myFrame.show();
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        quit.addActionListener(e -> {
            this.dispose();
        });
    }

    private void initializeOverlay() {
        this.overlay = new JPanel(new GridBagLayout());
        this.overlay.setOpaque(false);
        this.overlay.setVisible(false);
        this.overlay.addMouseListener(new java.awt.event.MouseAdapter() {
        });
    }

    private void initializeLayerPane() {
        this.layeredPane = new JLayeredPane();
        this.layeredPane.setLayout(new OverlayLayout(layeredPane));

        this.layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
        this.layeredPane.add(overlay, JLayeredPane.MODAL_LAYER);

        this.add(layeredPane, BorderLayout.CENTER);
    }

    public void showDialog(JPanel customContent) {
        overlay.removeAll();
        overlay.add(customContent);
        overlay.setVisible(true);
        this.repaint();
    }

    public void hideDialog() {
        overlay.setVisible(false);
        this.repaint();
    }

    public void setCursor() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImg = toolkit.getImage(getClass().getResource("/MainUI/MAIN_CURSOR.png"));
        Cursor customCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "cursorName");
        this.background.setCursor(customCursor);
    }
}
