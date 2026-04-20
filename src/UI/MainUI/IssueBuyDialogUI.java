package UI.MainUI;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Commands.ShopCommands.BuyShopCommand;
import Commands.ShopCommands.ShopDirection;
import Game.GameData;
import UI.BackgroundPanel;
import UI.CustomButton;
import UI.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;

public class IssueBuyDialogUI extends IssueDialogUI {

    private GameData gameData;
    private ShopDirection shopDirection;

    public IssueBuyDialogUI(String imgFile, String message, GameData gameData, ShopDirection shopDirection) throws InvalidUILoadException {
        super(imgFile, message);
        this.gameData = gameData;
        this.shopDirection = shopDirection;
        initializeButtons();
    }

    private void initializeButtons() throws InvalidUILoadException{
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        add(Box.createVerticalStrut(20));

        CustomButton ok = new CustomButton("/MainUI/ShopUI/OK_BUTTON.png","/MainUI/ShopUI/OK_BUTTON.png", 130, 75);
        CustomButton buy = new CustomButton("/MainUI/ShopUI/BUY_BUTTON.png", "/MainUI/ShopUI/BUY_BUTTON.png", 130, 75);

        ok.addActionListener(e ->{
            ShopUI parentShop = (ShopUI) SwingUtilities.getAncestorOfClass(ShopUI.class, this);
            parentShop.hideDialog();
        });

        buy.addActionListener(e ->{
            ShopUI parentShop = (ShopUI) SwingUtilities.getAncestorOfClass(ShopUI.class, this);
            CommandResult result = new BuyShopCommand(gameData, shopDirection).execute();
            if(result.getState() == CommandState.FAILED_ISSUE){
                try {
                    parentShop.hideDialog();
                    parentShop.showShopDialog(new IssueFailDialogUI("/MainUI/ShopUI/ISSUE_PANE.png",result.getMessage()));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
            }else {
                ShopManagementUI managementUI = (ShopManagementUI) SwingUtilities.getAncestorOfClass(ShopManagementUI.class, this);
                managementUI.changeCard(gameData.getShopManagement().getCurrentShop().getName().toString());
                parentShop.hideDialog();
            }
        });
        panel.add(ok);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(buy);
        add(panel);
    }
}
