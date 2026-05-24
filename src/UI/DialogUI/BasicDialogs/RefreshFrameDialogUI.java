package UI.DialogUI.BasicDialogs;

import UI.Exceptions.InvalidUILoadException;
import UI.InitialUI.MyFrame;
import UI.MainUI.MainUI;

import javax.swing.*;

public class RefreshFrameDialogUI extends DialogUI{

    public RefreshFrameDialogUI(String imgFile, String message) throws InvalidUILoadException {
        super(imgFile, message);
    }

    @Override
    public void buttonAction() {
        MyFrame frame = (MyFrame) SwingUtilities.getAncestorOfClass(MyFrame.class, this);
        MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
        try {
            frame.refreshUI();
            parent.startAllTimers();
        } catch (InvalidUILoadException e) {
            throw new RuntimeException(e);
        }
    }
}
