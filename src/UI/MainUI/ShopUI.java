package UI.MainUI;

import Game.GameData;
import Shops.Shop;
import UI.BackgroundPanel;
import UI.InvalidUILoadException;
import Upgrade.UpgradeNames;

import javax.swing.*;
import java.awt.*;


public class ShopUI extends BackgroundPanel {

    private final Shop shop;
    private final GameData gameData;
    private JLayeredPane layeredPane;
    private JPanel overlay;


    public ShopUI(Shop shop, GameData gameData) throws InvalidUILoadException {
        super("/MainUI/ShopUI/MAIN_BACKGROUND_TEST.png");
        this.shop = shop;
        this.gameData = gameData;

        initialize();
    }

    private void initialize() throws InvalidUILoadException{
        this.setLayout(new BorderLayout());

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        initializeSouth(mainPanel);

        overlay = new JPanel(new GridBagLayout());
        overlay.setOpaque(false);
        overlay.setVisible(false);
        overlay.addMouseListener(new java.awt.event.MouseAdapter() {});

        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(overlay, JLayeredPane.MODAL_LAYER);

        add(layeredPane);
    }

    private void initializeSouth(JPanel panel) throws InvalidUILoadException{
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        southPanel.setOpaque(false);
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 40));

        initializeShopItems(southPanel);
        initializeNPCItems(southPanel);
        initializeBounds(southPanel);

        panel.add(southPanel, BorderLayout.SOUTH);
    }

    private void initializeShopItems(JPanel panel) throws InvalidUILoadException{
        for (int i = 0; i < shop.getItems().length; i++) {
            panel.add(new ItemUI("/MainUI/ShopUI/ITEM_FRAME.png", shop.getItems()[i].getItem(), i, gameData, ItemSpecification.SHOP));
            panel.add(Box.createHorizontalStrut(40));
        }
    }

    private void initializeNPCItems(JPanel panel) throws InvalidUILoadException {
        panel.add(Box.createHorizontalStrut(40));
        for (int i = 0; i < shop.getNpc().getDemand().length; i++) {
            panel.add(new ItemUI("/MainUI/ShopUI/ITEM_FRAME.png", shop.getNpc().getDemand()[i], i, gameData, ItemSpecification.NPC));
            panel.add(Box.createHorizontalStrut(40));
        }
    }

    private void initializeBounds(JPanel panel) throws InvalidUILoadException{
        JPanel bounds = new JPanel();
        bounds.setLayout(new BoxLayout(bounds, BoxLayout.Y_AXIS));
        bounds.setOpaque(false);

        addBuyBoundPanel(bounds);
        addSellBoundPanel(bounds);

        panel.add(bounds);
    }

    private void addBuyBoundPanel(JPanel panel) throws InvalidUILoadException{
        panel.add(Box.createVerticalStrut(12));
        String current = String.valueOf(gameData.getDayManagement().getCurrentDay().getDayBoughtAmount());
        String bound = String.valueOf(gameData.getUpgradeManagement().getUpgradeData(UpgradeNames.BUY));

        panel.add(new BoundPanelUI("/MainUI/ShopUI/CURRENT_PANE.png", current, bound, "/MainUI/ShopUI/SHIP_ICON.png"));
    }

    private void addSellBoundPanel(JPanel panel) throws InvalidUILoadException{
        panel.add(Box.createVerticalStrut(12));
        String current = String.valueOf(gameData.getDayManagement().getCurrentDay().getDaySoldAmount());
        String bound = String.valueOf(gameData.getUpgradeManagement().getUpgradeData(UpgradeNames.SELL));

        panel.add(new BoundPanelUI("/MainUI/ShopUI/CURRENT_PANE.png", current, bound, "/MainUI/ShopUI/SELL_ICON.png"));

    }

    public void showShopDialog(JPanel customContent) {
        overlay.removeAll();
        overlay.add(customContent);
        overlay.setVisible(true);
        repaint();
    }

    public void hideDialog() {
        overlay.setVisible(false);
        repaint();
    }

}
