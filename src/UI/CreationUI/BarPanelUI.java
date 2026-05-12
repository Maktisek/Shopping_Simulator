package UI.CreationUI;

import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;

import javax.swing.*;
import java.awt.*;

public class BarPanelUI extends BackgroundPanel {

    public BarPanelUI(String type) throws InvalidUILoadException {
        super("/MainUI/ShopUI/" + type + "_UI_BAR.png");
        initialize();
    }


    private void initialize() throws InvalidUILoadException {
        initializeDimensions();

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 18, 10, 10));

        initializeExitButton();
    }

    private void initializeExitButton() throws InvalidUILoadException {
        CustomButton customButton = new CustomButton("/MainUI/ShopUI/ESCAPE_BUTTON.png", 100, 100);
        customButton.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.switchPanel("Shop");
        });
        add(customButton);
    }

    private void initializeDimensions() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        Dimension dimension = new Dimension(width, 135);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
    }

}
