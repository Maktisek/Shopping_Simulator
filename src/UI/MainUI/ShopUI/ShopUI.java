package UI.MainUI.ShopUI;

import Game.GameData;
import Items.Item;
import Shops.Shop;
import UI.CreationUI.BackgroundPanel;
import UI.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class ShopUI extends BackgroundPanel {

    private final Shop shop;
    private final GameData gameData;
    private final ArrayList<ItemUI> items;
    private JPanel southPanel;
    private ItemUI[] demand;


    public ShopUI(Shop shop, GameData gameData) throws InvalidUILoadException {
        super("/MainUI/ShopUI/MAIN_BACKGROUND_TEST.png");
        this.shop = shop;
        this.gameData = gameData;
        this.items = new ArrayList<>();
        this.demand = new ItemUI[2];

        initialize();
    }

    private void initialize() throws InvalidUILoadException {
        this.setLayout(new BorderLayout());

        initializeSouth();
    }

    private void initializeSouth() throws InvalidUILoadException {
        southPanel = new JPanel();
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
            this.demand[i] = itemUI;
            panel.add(itemUI);
            panel.add(Box.createHorizontalStrut(40));
        }
    }

    private void updateNPCItems(){
        for (int i = 0; i < shop.getNpc().getDemand().length; i++) {
            Item item = shop.getNpc().getDemand()[i];
            this.demand[i].updateNPC(item.getName().toString(), String.valueOf(item.getCurrentPrice()));
            this.demand[i].setItem(item);
        }
    }


    public void update() {
        for (ItemUI itemUI : items){
            itemUI.updateShop();
        }
        updateNPCItems();
    }

}
