package UI.InitialUI;

import Game.GameData;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{

    private final GameData gameData;

    public MyFrame(GameData gameData) throws InvalidUILoadException{
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

    private void initialize() throws InvalidUILoadException {
        add(new MainUI(gameData), BorderLayout.CENTER);
    }

    public void makeVisible() {
        setVisible(true);
    }
}
