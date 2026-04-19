package UI.MainUI;

import UI.BackgroundPanel;
import UI.CustomButton;
import UI.InvalidUILoadException;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;


public class IssueDialogUI extends BackgroundPanel {

    private final String message;

    public IssueDialogUI(String imgFile, String message) throws InvalidUILoadException {
        super(imgFile);
        this.message = message;
        initialize();
    }

    private void initialize() throws InvalidUILoadException{
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Dimension dimension = new Dimension(720, 160);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
        initializeLabel();
        initializeButton();
    }

    private void initializeLabel(){
        add(Box.createVerticalStrut(30));
        StrokeLabel label = new StrokeLabel(message);
        Font font = Important.loadFont("/Fonts/Daydream.otf");
        label.setFont(font);
        label.setOpaque(false);
        label.setFont(font.deriveFont(Font.BOLD,12.0f));
        label.setForeground(Color.WHITE);

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
//        label.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        add(label);
    }

    private void initializeButton() throws InvalidUILoadException {
        add(Box.createVerticalStrut(20));

        CustomButton button = new CustomButton("/MainUI/ShopUI/OK_BUTTON.png","/MainUI/ShopUI/OK_BUTTON.png", 130, 75);

        button.addActionListener(e ->{
            ShopUI parentShop = (ShopUI) SwingUtilities.getAncestorOfClass(ShopUI.class, this);
            parentShop.hideDialog();
        });

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(button);
    }


}
