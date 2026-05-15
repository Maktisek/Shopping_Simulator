package UI.DialogUI.SaveDialogs;

import Commands.CommandResult;
import Commands.SaveCommands.WriteSaveCommand;
import Game.GameData;
import UI.CreationUI.CustomButton;
import UI.DialogUI.BaseDialogUI;
import UI.DialogUI.DialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;

public class SaveDialogUI extends BaseDialogUI {

    private final GameData gameData;

    public SaveDialogUI(String imgFile, String message, GameData gameData) throws InvalidUILoadException {
        super(imgFile, message);
        this.gameData = gameData;
        initializeButtons();
    }

    private void initializeButtons() throws InvalidUILoadException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        add(Box.createVerticalStrut(20));

        CustomButton no = new CustomButton("/MainUI/ShopUI/NO_BUTTON.png", Important.calculateDimension(130), Important.calculateDimension(75));
        CustomButton save = new CustomButton("/MainUI/ShopUI/YES_BUTTON.png", Important.calculateDimension(130), Important.calculateDimension(75));

        no.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.hideDialog();
        });

        save.addActionListener(e -> {
            CommandResult result = new WriteSaveCommand(gameData).execute();
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            try {
                parent.hideDialog();
                parent.showDialog(new DialogUI("/MainUI/ShopUI/ISSUE_PANE.png", result.getMessage()));
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
        });
        panel.add(no);
        panel.add(Box.createHorizontalStrut(Important.calculateDimension(20)));
        panel.add(save);
        add(panel);
    }
}
