package UI.MainUI;

import Game.GameData;
import Shops.Shop;
import UI.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;

public class ShopManagementUI extends Panel {

    private final GameData gameData;
    private CardLayout cardLayout;

    public ShopManagementUI(GameData gameData) throws InvalidUILoadException{
        this.gameData = gameData;
        initialize();
    }

    private void initialize() throws InvalidUILoadException{
        this.cardLayout = new CardLayout();
        setLayout(cardLayout);

        initializeShops();
        cardLayout.show(this, gameData.getShopManagement().getShops().get(0).toString());
    }

    private void initializeShops() throws InvalidUILoadException {
        for (int i = 0; i < gameData.getShopManagement().getShops().size(); i++) {
            Shop currentShop = gameData.getShopManagement().getShops().get(i);
            ShopUI shop = new ShopUI(currentShop, gameData);
            add(shop, gameData.getShopManagement().getShops().get(i).toString());
        }
    }


}
