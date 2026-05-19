package UI.MainUI.ShopUI.ShopManagement;

import Commands.CommandResult;
import Commands.ShopCommands.ChangeShopLeftCommand;
import Commands.ShopCommands.ChangeShopRightCommand;
import Commands.ShopCommands.ShopDirection;
import Game.GameData;
import UI.CreationUI.ButtonType;
import UI.CreationUI.CustomButton;
import UI.DialogUI.BuyShopDialogUI;
import UI.DialogUI.DialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;

import javax.swing.*;
import java.awt.*;

public class ShopManagementWestUI extends JPanel {


    private final GameData gameData;

    public ShopManagementWestUI(GameData gameData) throws InvalidUILoadException {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false);
        this.add(Box.createVerticalGlue());

        this.gameData = gameData;

        initialization();
    }

    private void initialization() throws InvalidUILoadException {
        initializeChangeShopButtons();
        initializeChangeButton("STOCK");
        initializeChangeButton("ACHIEVEMENTS");
        initializeChangeButton("STATS");
        add(Box.createVerticalGlue());
    }

    private void initializeChangeButton(String card) throws InvalidUILoadException {
        CustomButton change = new CustomButton("/Sprites/ButtonSprites/" + card + "_BUTTON.png", 110, 110, ButtonType.ENTER);
        change.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.switchPanel(card);
        });
        change.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(change);
        add(Box.createVerticalStrut(20));
    }


    private void initializeChangeShopButtons() throws InvalidUILoadException {
        CustomButton previous = new CustomButton("/Sprites/ButtonSprites/PREVIOUS_SHOP_BUTTON.png", 110, 110, ButtonType.ENTER);
        CustomButton next = new CustomButton("/Sprites/ButtonSprites/NEXT_SHOP_BUTTON.png", 110, 110, ButtonType.ENTER);

        previous.setAlignmentX(Component.CENTER_ALIGNMENT);
        next.setAlignmentX(Component.CENTER_ALIGNMENT);

        previous.addActionListener(e -> {
            CommandResult result = new ChangeShopLeftCommand(gameData).execute();
            proceedCommandResult(result, ShopDirection.LEFT);
        });

        next.addActionListener(e -> {
            CommandResult result = new ChangeShopRightCommand(gameData).execute();
            proceedCommandResult(result, ShopDirection.RIGHT);
        });

        add(previous);
        add(Box.createVerticalStrut(20));
        add(next);
        add(Box.createVerticalStrut(20));
    }

    private void proceedCommandResult(CommandResult result, ShopDirection shopDirection) {
        System.out.println(result.getMessage());
        MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
        ShopManagementUI shopUI = (ShopManagementUI) SwingUtilities.getAncestorOfClass(ShopManagementUI.class, this);
        switch (result.getState()) {
            case DONE: {
                shopUI.changeCard(gameData.getShopManagement().getCurrentShop().getName());
                break;
            }
            case FAILED_ISSUE: {
                try {
                    parent.showDialog(new DialogUI("/Sprites/UtilityPanels/ISSUE_PANE.png", result.getMessage()));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            }
            case FAILED_BUY: {
                try {
                    parent.showDialog(new BuyShopDialogUI(result.getMessage(), gameData, shopDirection));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            }
        }
    }

}
