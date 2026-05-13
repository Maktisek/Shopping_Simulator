package UI.DialogUI;

import Commands.CommandResult;
import Commands.CommandState;
import Commands.SaveCommands.WriteSaveCommand;
import Commands.ShopCommands.BuyShopCommand;
import Game.GameData;
import UI.CreationUI.CustomButton;
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

        CustomButton ok = new CustomButton("/MainUI/ShopUI/OK_BUTTON.png", Important.calculateDimension(130), Important.calculateDimension(75));
        CustomButton save = new CustomButton("/MainUI/ShopUI/YES_BUTTON.png", Important.calculateDimension(130), Important.calculateDimension(75));

        ok.addActionListener(e -> {
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
        panel.add(ok);
        panel.add(Box.createHorizontalStrut(Important.calculateDimension(20)));
        panel.add(save);
        add(panel);
    }
}
