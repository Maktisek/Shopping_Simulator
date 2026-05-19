package UI.MainUI.ShopUI.ShopManagement;

import Game.GameData;
import UI.CreationUI.UpdateAble;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.ShopUI.Bounds.BoundPanelUI;
import UI.MainUI.ShopUI.Bounds.BoundTypes;
import Upgrade.Utilities.UpgradeNames;
import Utilities.Important;

import javax.swing.*;

public class ShopManagementSouthUI extends JPanel implements UpdateAble {

    private BoundPanelUI buyBounds;
    private BoundPanelUI sellBounds;
    private final GameData gameData;

    public ShopManagementSouthUI(GameData gameData) throws InvalidUILoadException {
        this.gameData = gameData;

        initialization();
    }

    private void initialization() throws InvalidUILoadException {
        JPanel bounds = new JPanel();
        bounds.setLayout(new BoxLayout(bounds, BoxLayout.Y_AXIS));
        bounds.setOpaque(false);

        addBuyBoundPanel(bounds);
        addSellBoundPanel(bounds);


        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, Important.calculateDimension(27), Important.calculateDimension(10)));
        this.add(Box.createHorizontalStrut(Important.calculateDimension(1600)));
        this.add(bounds);
        this.setOpaque(false);
    }

    private void addBuyBoundPanel(JPanel panel) throws InvalidUILoadException {
        panel.add(Box.createVerticalStrut(Important.calculateDimension(12)));

        this.buyBounds = new BoundPanelUI("/Sprites/UtilityPanels/CURRENT_PANE.png", "/Sprites/IconSprites/BUY_ICON.png", gameData, BoundTypes.BUY_BOUND);

        panel.add(buyBounds);
    }

    private void addSellBoundPanel(JPanel panel) throws InvalidUILoadException {
        panel.add(Box.createVerticalStrut(Important.calculateDimension(12)));
        this.sellBounds = new BoundPanelUI("/Sprites/UtilityPanels/CURRENT_PANE.png", "/Sprites/IconSprites/SELL_ICON.png", gameData, BoundTypes.SELL_BOUND);
        panel.add(sellBounds);
    }

    @Override
    public void update() throws InvalidUILoadException {
        this.buyBounds.update(String.valueOf(gameData.getDayManagement().getCurrentDay().getDayBoughtAmount()), String.valueOf(gameData.getUpgradeManagement().getUpgradeData(UpgradeNames.BUY)));
        this.sellBounds.update(String.valueOf(gameData.getDayManagement().getCurrentDay().getDaySoldAmount()), String.valueOf(gameData.getUpgradeManagement().getUpgradeData(UpgradeNames.SELL)));
    }
}
