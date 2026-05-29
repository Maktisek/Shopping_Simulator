package UI.CreationUI.Utilities;

import javax.swing.*;

/**
 * This interface represents a system, which lets classes to implement dialog system.
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public interface DialogAble {
    /**
     * Shows the given instance of {@link JPanel} as an overlay.
     * @param customContent the instance of {@link JPanel} to be displayed
     */
    void showDialog(JPanel customContent);

    /**
     * Hides currently displayed dialog.
     */
    void hideDialog();
}
