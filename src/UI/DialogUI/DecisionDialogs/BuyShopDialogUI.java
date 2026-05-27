package UI.DialogUI.DecisionDialogs;

import AudioSystem.AudioType;
import Commands.CommandResult;
import Commands.CommandState;
import Commands.ShopCommands.BuyShopCommand;
import Shops.ShopDirection;
import Game.GameData;
import UI.DialogUI.BasicDialogs.DialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;

public class BuyShopDialogUI extends YesNoDialogUI {

    private final GameData gameData;
    private final ShopDirection shopDirection;

    public BuyShopDialogUI(String message, GameData gameData, ShopDirection shopDirection) throws InvalidUILoadException {
        super(message);
        this.gameData = gameData;
        this.shopDirection = shopDirection;
    }

    @Override
    public void initializeBuyButton() {
        MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
        String previousShop = gameData.getShopManagement().getCurrentShop().getName();
        CommandResult result = new BuyShopCommand(gameData, shopDirection).execute();
        if(result.getState() == CommandState.FAILED_ISSUE){
            try {
                parent.hideDialog();
                parent.showDialog(new DialogUI("/Sprites/UtilityPanels/ISSUE_PANE.png",result.getMessage()));
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
        }else {
            parent.hideDialog();
            Important.getAudioManagement().playSound("Buy", AudioType.SOUNDS, 0, false);
            Important.getAudioManagement().stopSound(previousShop, AudioType.MUSIC);
            Important.getAudioManagement().playSound("ChangeShop", AudioType.SOUNDS, 0, false);
            parent.getShopManagementUI().changeCard(gameData.getShopManagement().getCurrentShop().getName());
        }
    }
}
