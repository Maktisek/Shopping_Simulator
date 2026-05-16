package UI.DialogUI;

import UI.Exceptions.InvalidUILoadException;
import UI.InitialUI.MyFrame;

import javax.swing.*;

public class RefreshFrameDialogUI extends DialogUI{

    public RefreshFrameDialogUI(String imgFile, String message) throws InvalidUILoadException {
        super(imgFile, message);
    }

    @Override
    public void buttonAction() {
        MyFrame frame = (MyFrame) SwingUtilities.getAncestorOfClass(MyFrame.class, this);
        try {
            frame.refreshUI();
        } catch (InvalidUILoadException e) {
            throw new RuntimeException(e);
        }
    }
}
