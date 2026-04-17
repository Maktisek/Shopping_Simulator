package UI.MainUI;

import Game.GameData;
import Shops.Shop;
import UI.BackgroundPanel;
import UI.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;


public class ShopUI extends BackgroundPanel {

    private Shop shop;
    private GameData gameData;


    public ShopUI(Shop shop, GameData gameData) throws InvalidUILoadException {
        super("/MainUI/ShopUI/MAIN_BACKGROUND_TEST.png");
        this.shop = shop;
        this.gameData = gameData;

        initialize();
    }

    private void initialize() throws InvalidUILoadException{
        this.setLayout(new BorderLayout());
        initializeSouth();
    }

    private void initializeSouth() throws InvalidUILoadException{
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        southPanel.setOpaque(false);
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 30, 40));

        initializeItems(southPanel);

        add(southPanel, BorderLayout.SOUTH);
    }

    private void initializeItems(JPanel panel) throws InvalidUILoadException{
        for (int i = 0; i < shop.getItems().length; i++) {
            panel.add(new ItemUI("/MainUI/ShopUI/ITEM_FRAME.png", shop.getItems()[i]));
            panel.add(Box.createHorizontalStrut(40));
        }
    }
}
