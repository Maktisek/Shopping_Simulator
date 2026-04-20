package UI.MainUI;

import Game.GameData;
import Shops.Shop;
import UI.InvalidUILoadException;
import Upgrade.UpgradeNames;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShopManagementUI extends Panel {

    private final GameData gameData;
    private CardLayout cardLayout;
    private ArrayList<ShopUI> shopPanels;

    public ShopManagementUI(GameData gameData) throws InvalidUILoadException {
        this.gameData = gameData;
        this.shopPanels = new ArrayList<>();
        initialize();
    }

    private void initialize() throws InvalidUILoadException {
        this.cardLayout = new CardLayout();
        setLayout(cardLayout);

        initializeShops();
        cardLayout.show(this, gameData.getShopManagement().getShops().get(0).toString());

        update();
    }

    private void initializeShops() throws InvalidUILoadException {
        for (int i = 0; i < gameData.getShopManagement().getShops().size(); i++) {
            Shop currentShop = gameData.getShopManagement().getShops().get(i);
            ShopUI shop = new ShopUI(currentShop, gameData);
            this.shopPanels.add(shop);
            add(shop, gameData.getShopManagement().getShops().get(i).toString());
        }
    }

    public void update() {
        for (ShopUI shopPanel : shopPanels) {
            shopPanel.update();
        }
    }
}
