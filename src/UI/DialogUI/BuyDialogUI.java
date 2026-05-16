package UI.DialogUI;

import Commands.CommandResult;
import Commands.CommandState;
import Commands.ShopCommands.BuyShopCommand;
import Game.GameData;
import UI.CreationUI.CustomButton;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;

public abstract class BuyDialogUI extends BaseDialogUI {

    public BuyDialogUI(String message) throws InvalidUILoadException {
        super("/MainUI/ShopUI/ISSUE_PANE.png", message);
    }

    private void initializeButtons() throws InvalidUILoadException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        add(Box.createVerticalStrut(Important.calculateDimension(20)));

        CustomButton ok = new CustomButton("/MainUI/ShopUI/NO_BUTTON.png", 130, 75);
        CustomButton buy = new CustomButton("/MainUI/ShopUI/BUY_BUTTON.png", 130, 75);

        ok.addActionListener(e ->{
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.hideDialog();
        });

        buy.addActionListener(e ->{

        });
        panel.add(ok);
        panel.add(Box.createHorizontalStrut(Important.calculateDimension(20)));
        panel.add(buy);
        add(panel);
    }

    public abstract void initializeBuyButton();
}
