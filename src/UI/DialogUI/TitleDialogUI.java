package UI.DialogUI;

import UI.Exceptions.InvalidUILoadException;
import UI.TitleUI.TitleScreenUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TitleDialogUI extends DialogUI{

    public TitleDialogUI(String imgFile, String message) throws InvalidUILoadException {
        super(imgFile, message);
        initializeDimensions();
    }

    private void initializeDimensions(){
        Dimension dimension = new Dimension(560, 180);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
    }

    @Override
    protected void initializeButton() throws InvalidUILoadException {
        super.initializeButton();

        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }

        button.addActionListener(e -> {
            TitleScreenUI parent = (TitleScreenUI) SwingUtilities.getAncestorOfClass(TitleScreenUI.class, this);
            parent.hideDialog();
        });
    }
}
