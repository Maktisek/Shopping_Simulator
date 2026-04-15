package UI;

import Game.GameData;
import Game.Initialization;
import UI.Buttons.TitleScreenButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class TitleScreenUI {

    private final JFrame frame;


    public TitleScreenUI() throws InvalidUILoadException {
        this.frame = new JFrame("Forest Market Launcher");
        this.frame.setSize(600, 600);
        this.frame.setLayout(new BorderLayout());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setMinimumSize(new Dimension(600, 600));
        this.frame.setResizable(false);
        initialize();
    }

    public void show(){
        this.frame.pack();
        this.frame.setVisible(true);
    }

    private void initialize() throws InvalidUILoadException{
        initializeTitle();
        initializeMainPanel();
    }

    private void initializeTitle() throws InvalidUILoadException{
        URL imageURL = getClass().getResource("/TitleScreenUI/TITLE_SCREEN_NAME.png");

        if(imageURL == null){
            throw new InvalidUILoadException("The title screen name image was not found");
        }

        ImageIcon icon = new ImageIcon(imageURL);
        JLabel label = new JLabel(icon, JLabel.CENTER);
        label.setBorder(new EmptyBorder(50, 0, 0, 0));
        label.setOpaque(false);

        this.frame.add(label, BorderLayout.NORTH);
    }

    private void initializeMainPanel() throws InvalidUILoadException{
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 40));

        TitleScreenButton newGame = new TitleScreenButton("/TitleScreenUI/NEW_GAME_BUTTON.png", 200, 100);
        TitleScreenButton loadGame = new TitleScreenButton("/TitleScreenUI/LOAD_BUTTON.png", 200, 100);
        TitleScreenButton quit = new TitleScreenButton("/TitleScreenUI/QUIT_BUTTON.png", 200, 100);

        newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(18));
        panel.add(newGame);
        panel.add(Box.createVerticalStrut(12));
        panel.add(loadGame);
        panel.add(Box.createVerticalStrut(12));
        panel.add(quit);

        this.frame.add(panel, BorderLayout.CENTER);

        newGame.addActionListener(e ->{
            GameData gameData = new Initialization().getGameData();
            System.out.println(gameData);
            this.frame.dispose();
        });

        loadGame.addActionListener(e ->{
            this.frame.dispose();
        });

        quit.addActionListener(e ->{
            this.frame.dispose();
        });
    }
}
