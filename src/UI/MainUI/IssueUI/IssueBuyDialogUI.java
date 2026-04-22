package UI.MainUI.IssueUI;

import Commands.CommandResult;
import Commands.CommandState;
import Commands.ShopCommands.BuyShopCommand;
import Commands.ShopCommands.ShopDirection;
import Game.GameData;
import UI.CreationUI.CustomButton;
import UI.InvalidUILoadException;
import UI.MainUI.MainUI;

import javax.swing.*;

public class IssueBuyDialogUI extends IssueDialogUI {

    private final GameData gameData;
    private final ShopDirection shopDirection;

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
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.hideDialog();
        });

        buy.addActionListener(e ->{
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            CommandResult result = new BuyShopCommand(gameData, shopDirection).execute();
            if(result.getState() == CommandState.FAILED_ISSUE){
                try {
                    parent.hideDialog();
                    parent.showShopDialog(new IssueFailDialogUI("/MainUI/ShopUI/ISSUE_PANE.png",result.getMessage()));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
            }else {
                parent.hideDialog();
                parent.getShopManagementUI().changeCard(gameData.getShopManagement().getCurrentShop().getName().toString());
            }
        });
        panel.add(ok);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(buy);
        add(panel);
    }
}
