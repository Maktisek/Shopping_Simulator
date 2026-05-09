package UI.DialogUI;

import UI.CreationUI.CustomButton;
import UI.CreationUI.StrokeLabel;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import UI.TitleUI.TitleScreenUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TitleDialogUI extends DialogUI{

    public TitleDialogUI(String imgFile, String message) throws InvalidUILoadException {
        super(imgFile, message);
        initializeDimensions();
        setOpaque(false);
    }

    private void initializeDimensions(){
        Dimension dimension = new Dimension(540, 180);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
    }

    @Override
    protected void initializeButton() throws InvalidUILoadException {
        add(Box.createVerticalStrut(10));

        button = new CustomButton("/MainUI/ShopUI/OK_BUTTON.png", 110, 62);
        button.setAlignmentX(CENTER_ALIGNMENT);
        add(button);

        button.addActionListener(e -> {
            TitleScreenUI parent = (TitleScreenUI) SwingUtilities.getAncestorOfClass(TitleScreenUI.class, this);
            parent.hideDialog();
        });
    }

    @Override
    protected void initializeLabel() {
        add(Box.createVerticalStrut(50));
        StrokeLabel label = new StrokeLabel(super.message, 12.0f);

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);
    }
}
