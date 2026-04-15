package UI;

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

    private void initializeMainPanel(){

    }
}
