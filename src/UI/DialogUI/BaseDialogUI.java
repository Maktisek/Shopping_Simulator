package UI.DialogUI;

import UI.CreationUI.BackgroundPanel;
import UI.Exceptions.InvalidUILoadException;
import UI.CreationUI.StrokeLabel;

import javax.swing.*;
import java.awt.*;

public abstract class BaseDialogUI extends BackgroundPanel {

    protected final String message;

    public BaseDialogUI(String imgFile, String message) throws InvalidUILoadException {
        super(imgFile);
        this.message = message;
        initialize();
    }

    private void initialize() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Dimension dimension = new Dimension(720, 160);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
        initializeLabel();
    }

    protected void initializeLabel() {
        add(Box.createVerticalStrut(35));
        StrokeLabel label = new StrokeLabel(message, 12.0f);

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);
    }

}
