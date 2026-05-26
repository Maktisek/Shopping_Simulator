package UI.MainUI.ShopUI.ShopManagement;

import AudioSystem.AudioType;
import Commands.CommandResult;
import Commands.ShopCommands.ChangeShopCommand;
import Commands.ShopCommands.ShopDirection;
import Game.GameData;
import UI.CreationUI.Buttons.ButtonType;
import UI.CreationUI.Buttons.CustomButton;
import UI.DialogUI.DecisionDialogs.BuyShopDialogUI;
import UI.DialogUI.BasicDialogs.DialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

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
            Important.getAudioManagement().pauseSound(gameData.getShopManagement().getCurrentShop().getName(), AudioType.MUSIC);
            Important.getAudioManagement().resumeSound(card+"OST", AudioType.MUSIC, false);
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
            if(Important.getAudioManagement().isMute()){
                Important.getAudioManagement().prepareForLoad();
                Important.getAudioManagement().getQueue().clear();
            }
            String previousShop = gameData.getShopManagement().getCurrentShop().getName();
            CommandResult result = new ChangeShopCommand(gameData, ShopDirection.LEFT).execute();
            proceedCommandResult(result, ShopDirection.LEFT, previousShop);
        });

        next.addActionListener(e -> {
            if(Important.getAudioManagement().isMute()){
                Important.getAudioManagement().prepareForLoad();
                Important.getAudioManagement().getQueue().clear();
            }
            String previousShop = gameData.getShopManagement().getCurrentShop().getName();
            CommandResult result = new ChangeShopCommand(gameData, ShopDirection.RIGHT).execute();
            proceedCommandResult(result, ShopDirection.RIGHT, previousShop);
        });

        add(previous);
        add(Box.createVerticalStrut(20));
        add(next);
        add(Box.createVerticalStrut(20));
    }

    private void proceedCommandResult(CommandResult result, ShopDirection shopDirection, String previousShop) {
        System.out.println(result.getMessage());
        MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
        ShopManagementUI shopUI = (ShopManagementUI) SwingUtilities.getAncestorOfClass(ShopManagementUI.class, this);
        switch (result.getState()) {
            case DONE: {
                Important.getAudioManagement().stopSound(previousShop, AudioType.MUSIC);
                Important.getAudioManagement().playSound("ChangeShop", AudioType.SOUNDS, 0, false);
                shopUI.changeCard(gameData.getShopManagement().getCurrentShop().getName());
                break;
            }
            case FAILED_ISSUE: {
                try {
                    Important.getAudioManagement().playSound("Error", AudioType.SOUNDS, 0, false);
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
