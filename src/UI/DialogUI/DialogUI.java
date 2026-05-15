package UI.DialogUI;

import UI.CreationUI.CustomButton;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;


public class DialogUI extends BaseDialogUI {

    CustomButton button;

    public DialogUI(String imgFile, String message) throws InvalidUILoadException {
        super(imgFile, message);
        initializeButton();
    }

    protected void initializeButton() throws InvalidUILoadException {
        add(Box.createVerticalStrut(Important.calculateDimension(20)));

        button = new CustomButton("/MainUI/ShopUI/OK_BUTTON.png", 130, 75);

        button.addActionListener(e ->{
            buttonAction();
        });

        button.setAlignmentX(CENTER_ALIGNMENT);
        add(button);
    }

    public void buttonAction(){
        MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
        parent.hideDialog();
    }

}
