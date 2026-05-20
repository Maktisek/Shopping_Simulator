package UI.TitleUI;

import Commands.CommandResult;
import Commands.CommandState;
import Commands.SaveCommands.LoadSaveCommand;
import Game.GameData;
import Game.Initialization;
import Game.InitializationType;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.ButtonType;
import UI.CreationUI.CustomTitleButton;
import UI.CreationUI.FrameBaseUI;
import UI.DialogUI.TitleDialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.InitialUI.MyFrame;
import Utilities.Important;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class TitleScreenUI extends FrameBaseUI {

    private BackgroundPanel background;
    private JPanel overlay;

    public TitleScreenUI() throws InvalidUILoadException {
        this.setTitle("Forest Market Launcher");
        this.setSize(Important.calculateDimension(600), Important.calculateDimension(600));
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(Important.calculateDimension(600), Important.calculateDimension(600)));
        this.setResizable(false);
        initialize();
    }

    @Override
    public void refreshUI() throws InvalidUILoadException {
        this.getContentPane().removeAll();
        initialize();
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }

    public void makeVisible() {
        this.pack();
        this.setVisible(true);
    }

    public void initializeFirstGameData(){
        new Initialization(InitializationType.SOUNDS);
    }

    private void initialize() throws InvalidUILoadException {
        initializeBackground();
        initializeOverlay();
        initializeLayerPane();
        setCursor();
    }

    private void initializeBackground() throws InvalidUILoadException {
        this.background = new BackgroundPanel("/Sprites/TitleScreenSprites/BACKGROUND.png");
        this.background.setLayout(new BorderLayout());
        initializeTitle();
        initializeMainPanel();
    }

    private void initializeTitle() throws InvalidUILoadException {
        URL imageURL = getClass().getResource("/Sprites/TitleScreenSprites/TITLE_SCREEN_NAME.png");

        if (imageURL == null) {
            throw new InvalidUILoadException("The title screen name image was not found");
        }

        ImageIcon icon = new ImageIcon(imageURL);
        Image scaledImage = icon.getImage().getScaledInstance(Important.calculateDimension(320), Important.calculateDimension(160), Image.SCALE_SMOOTH);
        icon.setImage(scaledImage);
        JLabel label = new JLabel(icon, JLabel.CENTER);
        label.setBorder(new EmptyBorder(Important.calculateDimension(50), 0, 0, 0));
        label.setOpaque(false);

        this.background.add(label, BorderLayout.NORTH);
    }

    private void initializeMainPanel() throws InvalidUILoadException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, Important.calculateDimension(40), Important.calculateDimension(20), Important.calculateDimension(40)));

        CustomTitleButton newGame = new CustomTitleButton("/Sprites/TitleScreenSprites/NEW_GAME_BUTTON.png", "/Sprites/TitleScreenSprites/NEW_GAME_BUTTON_CLICKED.png", 200, 100, ButtonType.ENTER);
        CustomTitleButton loadGame = new CustomTitleButton("/Sprites/TitleScreenSprites/LOAD_BUTTON.png", "/Sprites/TitleScreenSprites/LOAD_BUTTON_CLICKED.png", 200, 100, ButtonType.ENTER);
        CustomTitleButton quit = new CustomTitleButton("/Sprites/TitleScreenSprites/QUIT_BUTTON.png", "/Sprites/TitleScreenSprites/QUIT_BUTTON_CLICKED.png", 200, 100, ButtonType.EXIT);

        newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);


        this.background.add(panel, BorderLayout.CENTER);

        newGame.addActionListener(e -> {
            try {
                MyFrame myFrame = new MyFrame(new Initialization(InitializationType.ALL).getGameData());
                myFrame.makeVisible();
                this.stopTimer();
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
                    showDialog(new TitleDialogUI("/Sprites/TitleScreenSprites/TITLE_SCREEN_DIALOG.png", result.getMessage()));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                try {
                    MyFrame myFrame = new MyFrame(gameData);
                    myFrame.makeVisible();
                    this.stopTimer();
                    this.dispose();
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        quit.addActionListener(e -> {
            this.stopTimer();
            this.dispose();
        });

        panel.add(Box.createVerticalStrut(Important.calculateDimension(18)));
        panel.add(newGame);
        panel.add(Box.createVerticalStrut(Important.calculateDimension(12)));
        panel.add(loadGame);
        panel.add(Box.createVerticalStrut(Important.calculateDimension(12)));
        panel.add(quit);
    }

    private void initializeOverlay() {
        this.overlay = new JPanel(new GridBagLayout());
        this.overlay.setOpaque(false);
        this.overlay.setVisible(false);
        this.overlay.addMouseListener(new java.awt.event.MouseAdapter() {
        });
    }

    private void initializeLayerPane() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));

        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(overlay, JLayeredPane.MODAL_LAYER);

        this.getContentPane().add(layeredPane, BorderLayout.CENTER);
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
        Image cursorImg = toolkit.getImage(getClass().getResource("/Sprites/MainSprites/MAIN_CURSOR.png"));
        Cursor customCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "cursorName");
        this.background.setCursor(customCursor);
    }
}
