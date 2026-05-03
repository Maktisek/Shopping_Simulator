package UI.DialogUI;

import UI.CreationUI.CustomButton;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;

import javax.swing.*;


public class DialogUI extends BaseDialogUI {

    public DialogUI(String imgFile, String message) throws InvalidUILoadException {
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

        button.setAlignmentX(CENTER_ALIGNMENT);
        add(button);
    }


}
