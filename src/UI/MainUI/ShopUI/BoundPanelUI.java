package UI.MainUI.ShopUI;

import UI.CreationUI.BackgroundPanel;
import UI.InvalidUILoadException;
import UI.MainUI.Utilities.StrokeLabel;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BoundPanelUI extends BackgroundPanel {

    private String current;
    private String bound;
    private ImageIcon img;

    private StrokeLabel label;

    public BoundPanelUI(String imgFile, String current, String bound, String iconFile) throws InvalidUILoadException {
        super(imgFile);
        this.current = current;
        this.bound = bound;


        loadIcon(iconFile);
        initialize();
    }

    private void initialize() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        initializeDimensions();
        initializeIconLabel();
        initializeTextLabel();
    }

    private void initializeDimensions() {
        Dimension dimension = new Dimension(270, 90);
        setMinimumSize(new Dimension(dimension));
        setPreferredSize(new Dimension(dimension));
        setMaximumSize(new Dimension(dimension));
    }

    private void initializeIconLabel() {
        add(Box.createHorizontalStrut(10));
        JLabel label = new JLabel(img, JLabel.CENTER);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);
    }

    private void initializeTextLabel() {
        label = new StrokeLabel(current + "/" + bound, 24.0f);

        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(label);
    }


    private void loadIcon(String fileName) throws InvalidUILoadException {
        URL imageURL = getClass().getResource(fileName);

        if (imageURL == null) {
            throw new InvalidUILoadException("The icon" + fileName + " could not be found");
        }

        this.img = new ImageIcon(imageURL);
    }

    public void update(String current, String bound) {
        this.current = current;
        this.bound = bound;
        if(label != null){
            label.setText(this.current + "/" + this.bound);
        }
    }
}
