package UI.MainUI;

import UI.BackgroundPanel;
import UI.InvalidUILoadException;

import java.awt.*;

public class BoundPanelUI extends BackgroundPanel {

    private String current;
    private String bound;

    public BoundPanelUI(String imgFile, String current, String bound) throws InvalidUILoadException {
        super(imgFile);
        initialize();
    }

    private void initialize() {
        initializeDimensions();
    }

    private void initializeDimensions() {
        Dimension dimension = new Dimension(96, 32);
        setMinimumSize(new Dimension(dimension));
        setPreferredSize(new Dimension(dimension));
        setMaximumSize(new Dimension(dimension));
    }




}
