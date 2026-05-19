package UI.CreationUI;

import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

public class BarPanelUI extends BackgroundPanel {


    public BarPanelUI(String type) throws InvalidUILoadException {
        super("/Sprites/BarSprites/" + type + "_UI_BAR.png");
        initialize();
    }


    private void initialize() throws InvalidUILoadException {
        initializeDimensions();

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(Important.calculateDimension(5), Important.calculateDimension(18), Important.calculateDimension(10), Important.calculateDimension(10)));

        initializeExitButton();
    }

    private void initializeExitButton() throws InvalidUILoadException {
        CustomButton customButton = new CustomButton("/Sprites/ButtonSprites/ESCAPE_BUTTON.png", Important.calculateDimension(100), Important.calculateDimension(100), ButtonType.EXIT);
        customButton.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.switchPanel("Shop");
        });
        add(customButton);
    }

    private void initializeDimensions() {
        Dimension dimension = new Dimension(Important.calculateDimension(Important.getWidth()), Important.calculateDimension(135));
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
    }

}
