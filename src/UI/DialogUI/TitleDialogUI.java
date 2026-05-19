package UI.DialogUI;

import UI.CreationUI.CustomButton;
import UI.CreationUI.StrokeLabel;
import UI.Exceptions.InvalidUILoadException;
import UI.TitleUI.TitleScreenUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

public class TitleDialogUI extends DialogUI{

    public TitleDialogUI(String imgFile, String message) throws InvalidUILoadException {
        super(imgFile, message);
        initializeDimensions();
        setOpaque(false);
    }

    private void initializeDimensions(){
        Dimension dimension = new Dimension(Important.calculateDimension(540), Important.calculateDimension(180));
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
    }

    @Override
    protected void initializeButton() throws InvalidUILoadException {
        add(Box.createVerticalStrut(Important.calculateDimension(10)));

        button = new CustomButton("/ShopSprites/OK_BUTTON.png", Important.calculateDimension(110), Important.calculateDimension(62));
        button.setAlignmentX(CENTER_ALIGNMENT);
        add(button);

        button.addActionListener(e -> {
            TitleScreenUI parent = (TitleScreenUI) SwingUtilities.getAncestorOfClass(TitleScreenUI.class, this);
            parent.hideDialog();
        });
    }

    @Override
    protected void initializeLabel() {
        add(Box.createVerticalStrut(Important.calculateDimension(50)));
        StrokeLabel label = new StrokeLabel(super.message, 12);

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);
    }
}
