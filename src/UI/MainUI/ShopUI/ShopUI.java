package UI.MainUI.ShopUI;

import Game.GameData;
import Items.ItemNPC;
import Shops.Shop;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.UpdateAble;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.ShopUI.Items.ItemSpecification;
import UI.MainUI.ShopUI.Items.ItemUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class ShopUI extends BackgroundPanel implements UpdateAble {

    private final Shop shop;
    private final GameData gameData;
    private final ArrayList<ItemUI> items;
    private final ItemUI[] demandUI;


    public ShopUI(Shop shop, GameData gameData) throws InvalidUILoadException {
        super("/MainUI/ShopUI/" + shop.getName() + "_SHOP.png");
        this.shop = shop;
        this.gameData = gameData;
        this.items = new ArrayList<>();
        this.demandUI = new ItemUI[2];

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
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, Important.calculateDimension(40), Important.calculateDimension(40), Important.calculateDimension(40)));

        initializeShopItems(southPanel);
        initializeNPCItems(southPanel);

        add(southPanel, BorderLayout.SOUTH);
    }

    private void initializeShopItems(JPanel panel) throws InvalidUILoadException {
        for (int i = 0; i < shop.getItems().length; i++) {
            ItemUI itemUI = new ItemUI("/MainUI/ShopUI/ITEM_FRAME.png", shop.getItems()[i], i, gameData, ItemSpecification.SHOP);

            panel.add(itemUI);
            panel.add(Box.createHorizontalStrut(Important.calculateDimension(40)));
            this.items.add(itemUI);
        }
    }

    private void initializeNPCItems(JPanel panel) throws InvalidUILoadException {
        panel.add(Box.createHorizontalStrut(Important.calculateDimension(40)));
        for (int i = 0; i < shop.getNpc().getDemand().length; i++) {
            ItemUI itemUI = new ItemUI("/MainUI/ShopUI/ITEM_FRAME.png", shop.getNpc().getDemand()[i], i, gameData, ItemSpecification.NPC);
            this.demandUI[i] = itemUI;
            panel.add(itemUI);
            panel.add(Box.createHorizontalStrut(Important.calculateDimension(40)));
        }
    }

    private void updateNPCItems() throws InvalidUILoadException {
        for (int i = 0; i < shop.getNpc().getDemand().length; i++) {
            ItemNPC item = shop.getNpc().getDemand()[i];
            this.demandUI[i].setItem(item);
            this.demandUI[i].update();
        }
    }


    @Override
    public void update() throws InvalidUILoadException {
        for (ItemUI itemUI : items) {
            itemUI.update();
        }
        updateNPCItems();
    }

}
