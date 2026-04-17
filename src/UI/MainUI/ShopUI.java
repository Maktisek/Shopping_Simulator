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

    private void initialize(){
        this.setLayout(new BorderLayout());
        initializeSouth();
    }

    private void initializeSouth(){
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
    }
}
