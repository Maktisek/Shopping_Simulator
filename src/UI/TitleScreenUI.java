package UI;

import javax.swing.*;
import java.awt.*;

public class TitleScreenUI {

    private final JFrame frame;

    public TitleScreenUI() {
        this.frame = new JFrame("Forest Market Launcher");
        this.frame.setSize(600, 600);
        this.frame.setLayout(new BorderLayout());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(600, 600));

    }

    public void show(){
        this.frame.pack();
        this.frame.setVisible(true);
    }
}
