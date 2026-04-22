package UI.MainUI;

import Game.GameData;
import UI.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;

public class MyFrame {

    private final JFrame frame;
    private final GameData gameData;

    public MyFrame(GameData gameData) throws InvalidUILoadException{
        this.frame = new JFrame("Forest Market");
        this.frame.setLayout(new BorderLayout());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.gameData = gameData;
        initialize();
    }

    private void initialize() throws InvalidUILoadException {
        this.frame.add(new MainUI(gameData), BorderLayout.CENTER);
    }

    public void show() {
        this.frame.setVisible(true);
    }
}
