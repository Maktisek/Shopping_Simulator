package UI.MainUI.ShopUI.ShopManagement;

import Game.GameData;
import Shops.Shop;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.CustomButton;
import UI.CreationUI.UpdateAble;
import UI.DialogUI.*;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import UI.MainUI.ShopUI.Bounds.BoundPanelUI;
import UI.MainUI.ShopUI.Bounds.BoundTypes;
import UI.MainUI.ShopUI.Days.DayUI;
import UI.MainUI.ShopUI.ShopUI;
import UI.MainUI.ShopUI.Upgrades.UpgradeUI;
import Upgrade.Utilities.UpgradeNames;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShopManagementUI extends BackgroundPanel implements UpdateAble {

    private final JPanel cardPanel;
    private final JPanel mainPanel;
    private final GameData gameData;
    private CardLayout cardLayout;
    private final ArrayList<ShopUI> shopPanels;
    private BoundPanelUI buyBounds;
    private BoundPanelUI sellBounds;
    private DayUI dayUI;
    private final ArrayList<UpgradeUI> upgrades;
    private ShopManagementNorthUI shopManagementNorthUI;
    private ShopManagementSouthUI shopManagementSouthUI;

    public ShopManagementUI(GameData gameData) throws InvalidUILoadException {
        super();
        this.gameData = gameData;
        this.shopPanels = new ArrayList<>();
        this.upgrades = new ArrayList<>();
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
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.setOpaque(false);
        eastPanel.setBorder(BorderFactory.createEmptyBorder(Important.calculateDimension(18), 0, 0, Important.calculateDimension(28)));

        initializeDay(eastPanel);
        initializeNewDayButton(eastPanel);
        initializeUpgrades(eastPanel);

        panel.add(eastPanel, BorderLayout.EAST);
    }

    private void initializeDay(JPanel eastPanel) throws InvalidUILoadException {
        this.dayUI = new DayUI("/MainUI/ShopUI/DAY_FRAME.png", gameData);
        dayUI.setAlignmentX(Component.CENTER_ALIGNMENT);

        eastPanel.add(dayUI);
    }

    private void initializeNewDayButton(JPanel panel) throws InvalidUILoadException {
        CustomButton nextDay = new CustomButton("/MainUI/ShopUI/NEXT_DAY_BUTTON.png", 200, 85);
        nextDay.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(Important.calculateDimension(7)));
        panel.add(nextDay);

        nextDay.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            try {
                parent.showDialog(new NewDayDialogUI("/MainUI/ShopUI/ISSUE_PANE.png", "The tax is  " + Important.parseMoney(gameData.getTax().getCurrent())  + "FR, do you want to proceed into another day?", gameData));
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void initializeUpgrades(JPanel eastPanel) throws InvalidUILoadException {
        eastPanel.add(Box.createVerticalStrut(Important.calculateDimension(15)));
        for (UpgradeNames upgrade : UpgradeNames.values()) {
            UpgradeUI upgradeUI = new UpgradeUI("/MainUI/ShopUI/ITEM_FRAME.png", gameData.getUpgradeManagement().getUpgrades().get(upgrade), gameData);
            upgradeUI.setOpaque(false);
            upgradeUI.setAlignmentX(Component.CENTER_ALIGNMENT);
            eastPanel.add(upgradeUI);
            eastPanel.add(Box.createVerticalStrut(Important.calculateDimension(20)));
            upgrades.add(upgradeUI);
        }
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
        this.dayUI.update();
        for (UpgradeUI upgradeUI : upgrades) {
            upgradeUI.update();
        }
        this.shopManagementNorthUI.update();
        this.shopManagementSouthUI.update();
    }


}
