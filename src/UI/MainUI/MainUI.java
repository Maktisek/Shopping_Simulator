package UI.MainUI;

import UI.BackgroundPanel;
import UI.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;

public class MainUI {
    private final JFrame frame;
    private final BackgroundPanel background;

    public MainUI() throws InvalidUILoadException {
        this.frame = new JFrame("Forest Market");

        this.background = new BackgroundPanel("/MainUI/ShopUI/MAIN_BACKGROUND_TEST.png");
        this.frame.setContentPane(background);

        this.frame.setLayout(new CardLayout());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public void show(){
        this.frame.pack();
        this.frame.setVisible(true);
    }
}
