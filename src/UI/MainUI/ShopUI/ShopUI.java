package UI.MainUI.ShopUI;

import Game.GameData;
import Shops.Shop;
import UI.BackgroundPanel;
import UI.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class ShopUI extends BackgroundPanel {

    private final Shop shop;
    private final GameData gameData;
    private final ArrayList<ItemUI> items;


    public ShopUI(Shop shop, GameData gameData) throws InvalidUILoadException {
        super("/MainUI/ShopUI/MAIN_BACKGROUND_TEST.png");
        this.shop = shop;
        this.gameData = gameData;
        this.items = new ArrayList<>();

        initialize();
    }

    private void initialize() throws InvalidUILoadException {
        this.setLayout(new BorderLayout());

        initializeSouth();
    }

    private void initializeSouth() throws InvalidUILoadException {
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        southPanel.setOpaque(false);
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 40));

        initializeShopItems(southPanel);
        initializeNPCItems(southPanel);

        add(southPanel, BorderLayout.SOUTH);
    }

    private void initializeShopItems(JPanel panel) throws InvalidUILoadException {
        for (int i = 0; i < shop.getItems().length; i++) {
            ItemUI itemUI = new ItemUI("/MainUI/ShopUI/ITEM_FRAME.png", shop.getItems()[i].getItem(), i, gameData, ItemSpecification.SHOP);

            panel.add(itemUI);
            panel.add(Box.createHorizontalStrut(40));
            this.items.add(itemUI);
        }
    }

    private void initializeNPCItems(JPanel panel) throws InvalidUILoadException {
        panel.add(Box.createHorizontalStrut(40));
        for (int i = 0; i < shop.getNpc().getDemand().length; i++) {
            ItemUI itemUI = new  ItemUI("/MainUI/ShopUI/ITEM_FRAME.png", shop.getNpc().getDemand()[i], i, gameData, ItemSpecification.NPC);

            panel.add(itemUI);
            panel.add(Box.createHorizontalStrut(40));
            this.items.add(itemUI);
        }
    }


    public void update() {
        for (ItemUI itemUI : items){
            itemUI.update();
        }
    }

}
