package UI.DialogUI.DecisionDialogs;

import UI.CreationUI.Buttons.ButtonType;
import UI.CreationUI.Buttons.CustomButton;
import UI.DialogUI.BasicDialogs.BaseDialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;

/**
 * This class is an abstract class extending {@link BaseDialogUI}.
 * <p>
 *     Dialogs extending this class works as option dialogs. Always featuring two buttons
 *     <ul>
 *         <li>One that closes the dialog</li>
 *         <li>Second that performs the desired action</li>
 *     </ul>
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public abstract class YesNoDialogUI extends BaseDialogUI {

    public YesNoDialogUI(String message) throws InvalidUILoadException {
        super("/Sprites/UtilityPanels/ISSUE_PANE.png", message);
        initializeButtons();
    }

    private void initializeButtons() throws InvalidUILoadException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        add(Box.createVerticalStrut(Important.calculateDimension(20)));

        CustomButton no = new CustomButton("/Sprites/ButtonSprites/NO_BUTTON.png", 130, 75, ButtonType.EXIT);
        CustomButton yes = new CustomButton("/Sprites/ButtonSprites/YES_BUTTON.png", 130, 75, ButtonType.NONE);

        no.addActionListener(e ->{
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.hideDialog();
        });

        yes.addActionListener(e ->{
            initializeYesButton();
        });
        panel.add(no);
        panel.add(Box.createHorizontalStrut(Important.calculateDimension(20)));
        panel.add(yes);
        add(panel);
    }

    /**
     * This method is performed when clicking the {@code yes} button.
     */
    public abstract void initializeYesButton();
}
