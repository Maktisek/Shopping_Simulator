package UI.DialogUI;

import Commands.CommandResult;
import Commands.CommandState;
import Commands.ShopCommands.BuyShopCommand;
import Commands.ShopCommands.ShopDirection;
import Game.GameData;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;

import javax.swing.*;

public class BuyShopDialogUI extends BuyDialogUI {

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
            parent.getShopManagementUI().changeCard(gameData.getShopManagement().getCurrentShop().getName().toString());
        }
    }
}
