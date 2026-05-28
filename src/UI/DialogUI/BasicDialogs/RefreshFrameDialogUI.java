package UI.DialogUI.BasicDialogs;

import UI.Exceptions.InvalidUILoadException;
import UI.InitialUI.MyFrame;
import UI.MainUI.MainUI;

import javax.swing.*;

/**
 * This class is an implementation of {@link DialogUI}
 * <p>
 * This dialog refreshes the frame where it is displayed and starts all timers of {@link MainUI} if it is found.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class RefreshFrameDialogUI extends DialogUI {

    public RefreshFrameDialogUI(String imgFile, String message) throws InvalidUILoadException {
        super(imgFile, message);
    }

    @Override
    public void buttonAction() {
        MyFrame frame = (MyFrame) SwingUtilities.getAncestorOfClass(MyFrame.class, this);
        MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
        if(frame != null) {
            try {
                frame.refreshUI();
            } catch (InvalidUILoadException e) {
                throw new RuntimeException(e);
            }
        }
        if (parent != null) {
            parent.startAllTimers();
        }
    }
}
