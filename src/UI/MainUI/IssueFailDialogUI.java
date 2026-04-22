package UI.MainUI;

import UI.CustomButton;
import UI.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;


public class IssueFailDialogUI extends IssueDialogUI {

    public IssueFailDialogUI(String imgFile, String message) throws InvalidUILoadException {
        super(imgFile, message);
        initializeButton();
    }

    private void initializeButton() throws InvalidUILoadException {
        add(Box.createVerticalStrut(20));

        CustomButton button = new CustomButton("/MainUI/ShopUI/OK_BUTTON.png","/MainUI/ShopUI/OK_BUTTON.png", 130, 75);

        button.addActionListener(e ->{
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.hideDialog();
        });

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(button);
    }


}
