package UI.MainUI.ShopUI.ShopManagement;

import Game.GameData;
import Shops.Shop;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.UpdateAble;
import UI.DialogUI.*;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.ShopUI.ShopUI;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShopManagementUI extends BackgroundPanel implements UpdateAble {

    private final JPanel cardPanel;
    private final JPanel mainPanel;
    private final GameData gameData;
    private CardLayout cardLayout;
    private final ArrayList<ShopUI> shopPanels;
    private ShopManagementNorthUI shopManagementNorthUI;
    private ShopManagementSouthUI shopManagementSouthUI;
    private ShopManagementEastUI shopManagementEastUI;

    public ShopManagementUI(GameData gameData) throws InvalidUILoadException {
        super();
        this.gameData = gameData;
        this.shopPanels = new ArrayList<>();
        this.cardPanel = new JPanel();
        this.mainPanel = new JPanel(new BorderLayout());

        initialize();
    }

    private void initialize() throws InvalidUILoadException {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));

        this.cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        setLayout(new BorderLayout());
        initializeShops();

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        initializeSouthPanel(wrapper);
        initializeEast(wrapper);

        JPanel central = new JPanel(new BorderLayout());
        central.setOpaque(false);

        initializeWestPanel(central);
        initializeNorth(central);

        wrapper.add(central, BorderLayout.CENTER);

        mainPanel.add(cardPanel, BorderLayout.CENTER);
        cardLayout.show(cardPanel, gameData.getShopManagement().getShops().get(0).getName());

        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(wrapper, JLayeredPane.PALETTE_LAYER);
        add(layeredPane);
        update();
    }

    private void initializeShops() throws InvalidUILoadException {
        for (int i = 0; i < gameData.getShopManagement().getShops().size(); i++) {
            Shop currentShop = gameData.getShopManagement().getShops().get(i);
            ShopUI shop = new ShopUI(currentShop, gameData);
            this.shopPanels.add(shop);
            cardPanel.add(shop, gameData.getShopManagement().getShops().get(i).getName());
        }
    }

    private void initializeWestPanel(JPanel panel) throws InvalidUILoadException {
        ShopManagementWestUI shopManagementWestUI = new ShopManagementWestUI(gameData);
        panel.add(shopManagementWestUI, BorderLayout.WEST);
    }

    private void initializeSouthPanel(JPanel panel) throws InvalidUILoadException {
        this.shopManagementSouthUI = new ShopManagementSouthUI(gameData);
        panel.add(shopManagementSouthUI, BorderLayout.SOUTH);
    }

    private void initializeEast(JPanel panel) throws InvalidUILoadException {
        this.shopManagementEastUI = new ShopManagementEastUI(gameData);
        panel.add(this.shopManagementEastUI, BorderLayout.EAST);
    }

    private void initializeNorth(JPanel wrapper) throws InvalidUILoadException {
        this.shopManagementNorthUI = new ShopManagementNorthUI(gameData);
        wrapper.add(shopManagementNorthUI, BorderLayout.NORTH);
    }

    public void changeCard(String card) {
        this.cardLayout.show(cardPanel, card);
    }

    @Override
    public void update() throws InvalidUILoadException {
        for (ShopUI shopPanel : shopPanels) {
            shopPanel.update();
        }
        this.shopManagementNorthUI.update();
        this.shopManagementSouthUI.update();
        this.shopManagementEastUI.update();
    }
}