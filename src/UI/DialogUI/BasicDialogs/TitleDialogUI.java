package UI.DialogUI.BasicDialogs;

import UI.CreationUI.Buttons.ButtonType;
import UI.CreationUI.Buttons.CustomButton;
import UI.CreationUI.Labels.StrokeLabel;
import UI.Exceptions.InvalidUILoadException;
import UI.TitleUI.TitleScreenUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

/**
 * This class is an implementation of {@link DialogUI}.
 * <p>
 *     It is used in {@link TitleScreenUI} and it does almost exactly the same as {@link DialogUI}, but it is compatible with {@link TitleScreenUI}.
 * </p>
 * The message label has to be rearranged so it will fit within the background image.
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class TitleDialogUI extends DialogUI{

    public TitleDialogUI(String imgFile, String message) throws InvalidUILoadException {
        super(imgFile, message, "Error");
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

        button = new CustomButton("/Sprites/ButtonSprites/OK_BUTTON.png", Important.calculateDimension(110), Important.calculateDimension(62), ButtonType.ENTER);
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
