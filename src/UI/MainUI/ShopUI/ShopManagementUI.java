package UI.MainUI.ShopUI;

import Commands.CommandResult;
import Commands.ShopCommands.ChangeShopLeftCommand;
import Commands.ShopCommands.ChangeShopRightCommand;
import Commands.ShopCommands.ShopDirection;
import Game.GameData;
import Shops.Shop;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.CustomButton;
import UI.CreationUI.MultiplierButton;
import UI.InvalidUILoadException;
import UI.MainUI.IssueUI.IssueBuyDialogUI;
import UI.MainUI.IssueUI.IssueFailDialogUI;
import UI.MainUI.MainUI;
import Upgrade.UpgradeNames;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShopManagementUI extends BackgroundPanel {

    private final JPanel cardPanel;
    private final JPanel mainPanel;
    private JLayeredPane layeredPane;
    private final GameData gameData;
    private CardLayout cardLayout;
    private final ArrayList<ShopUI> shopPanels;
    private BoundPanelUI buyBounds;
    private BoundPanelUI sellBounds;
    private DayUI dayUI;
    private final ArrayList<UpgradeUI> upgrades;
    private MoneyPanelUI moneyPanelUI;
    private ArrayList<MultiplierButton> multiplierButtons;

    public ShopManagementUI(GameData gameData) throws InvalidUILoadException {
        super();
        this.gameData = gameData;
        this.shopPanels = new ArrayList<>();
        this.upgrades = new ArrayList<>();
        this.multiplierButtons = new ArrayList<>();
        this.cardPanel = new JPanel();
        this.mainPanel = new JPanel(new BorderLayout());

        initialize();
    }

    private void initialize() throws InvalidUILoadException {
        layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));
        this.cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        setLayout(new BorderLayout());
        initializeShops();

        //Wrapper stands for an individual panel which is then added within the JLayerPane, so the components
        //can be shown on top of another components.
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        initializeWestPanel(wrapper);
        initializeBounds(wrapper);
        initializeEast(wrapper);

        JPanel northWrapper = new JPanel(new BorderLayout());
        northWrapper.setOpaque(false);
        initializeNorth(northWrapper);

        mainPanel.add(cardPanel, BorderLayout.CENTER);
        cardLayout.show(cardPanel, gameData.getShopManagement().getShops().get(0).getName().toString());


        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(wrapper, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(northWrapper, JLayeredPane.PALETTE_LAYER);
        add(layeredPane);
        update();
    }

    private void initializeShops() throws InvalidUILoadException {
        for (int i = 0; i < gameData.getShopManagement().getShops().size(); i++) {
            Shop currentShop = gameData.getShopManagement().getShops().get(i);
            ShopUI shop = new ShopUI(currentShop, gameData);
            this.shopPanels.add(shop);
            cardPanel.add(shop, gameData.getShopManagement().getShops().get(i).getName().toString());
        }
    }

    private void initializeWestPanel(JPanel panel) throws InvalidUILoadException {
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setOpaque(false);
        initializeChangeShopButtons(westPanel);
        initializeStocksChangeButton(westPanel);

        panel.add(westPanel, BorderLayout.WEST);
    }

    private void initializeStocksChangeButton(JPanel westPanel) throws InvalidUILoadException {
        CustomButton previous = new CustomButton("/MainUI/ShopUI/STOCK_BUTTON.png", "/MainUI/ShopUI/STOCK_BUTTON.png", 162, 162);
        previous.addActionListener(e ->{
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.switchPanel("Stock");
        });
        previous.setAlignmentX(Component.CENTER_ALIGNMENT);
        westPanel.add(previous);
        westPanel.add(Box.createVerticalGlue());
    }

    private void initializeChangeShopButtons(JPanel panel) throws InvalidUILoadException {
        panel.add(Box.createVerticalGlue());
        CustomButton previous = new CustomButton("/MainUI/ShopUI/PREVIOUS_SHOP_BUTTON.png", "/MainUI/ShopUI/PREVIOUS_SHOP_BUTTON.png", 162, 162);
        CustomButton next = new CustomButton("/MainUI/ShopUI/NEXT_SHOP_BUTTON.png", "/MainUI/ShopUI/NEXT_SHOP_BUTTON.png", 162, 162);

        previous.setAlignmentX(Component.CENTER_ALIGNMENT);
        next.setAlignmentX(Component.CENTER_ALIGNMENT);

        previous.addActionListener(e -> {
            CommandResult result = new ChangeShopLeftCommand(gameData).execute();
            proceedCommandResult(result, ShopDirection.LEFT);
        });

        next.addActionListener(e -> {
            CommandResult result = new ChangeShopRightCommand(gameData).execute();
            proceedCommandResult(result, ShopDirection.RIGHT);
        });

        panel.add(previous);
        panel.add(next);
    }

    private void proceedCommandResult(CommandResult result, ShopDirection shopDirection) {
        System.out.println(result.getMessage());
        MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
        switch (result.getState()) {
            case DONE: {
                changeCard(gameData.getShopManagement().getCurrentShop().getName().toString());
                break;
            }
            case FAILED_ISSUE: {
                try {
                    parent.showDialog(new IssueFailDialogUI("/MainUI/ShopUI/ISSUE_PANE.png", result.getMessage()));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            }
            case FAILED_BUY: {
                try {
                    parent.showDialog(new IssueBuyDialogUI("/MainUI/ShopUI/ISSUE_PANE.png", result.getMessage(), gameData, shopDirection));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            }
        }
    }

    private void initializeBounds(JPanel panel) throws InvalidUILoadException {
        JPanel bounds = new JPanel();
        bounds.setLayout(new BoxLayout(bounds, BoxLayout.Y_AXIS));
        bounds.setOpaque(false);

        addBuyBoundPanel(bounds);
        addSellBoundPanel(bounds);

        JPanel setBounds = new JPanel();
        setBounds.setLayout(new BoxLayout(setBounds, BoxLayout.X_AXIS));
        setBounds.setBorder(BorderFactory.createEmptyBorder(0, 0, 27, 10));

        setBounds.add(Box.createHorizontalStrut(1600));
        setBounds.add(bounds);
        setBounds.setOpaque(false);


        panel.add(setBounds, BorderLayout.SOUTH);
    }

    private void addBuyBoundPanel(JPanel panel) throws InvalidUILoadException {
        panel.add(Box.createVerticalStrut(12));

        String current = String.valueOf(gameData.getDayManagement().getCurrentDay().getDayBoughtAmount());
        String bound = String.valueOf(gameData.getUpgradeManagement().getUpgradeData(UpgradeNames.BUY));

        this.buyBounds = new BoundPanelUI("/MainUI/ShopUI/CURRENT_PANE.png", current, bound, "/MainUI/ShopUI/BUY_ICON.png");

        panel.add(buyBounds);
    }

    private void addSellBoundPanel(JPanel panel) throws InvalidUILoadException {
        panel.add(Box.createVerticalStrut(12));
        String current = String.valueOf(gameData.getDayManagement().getCurrentDay().getDaySoldAmount());
        String bound = String.valueOf(gameData.getUpgradeManagement().getUpgradeData(UpgradeNames.SELL));

        this.sellBounds = new BoundPanelUI("/MainUI/ShopUI/CURRENT_PANE.png", current, bound, "/MainUI/ShopUI/SELL_ICON.png");

        panel.add(sellBounds);
    }

    private void initializeEast(JPanel panel) throws InvalidUILoadException {
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.setOpaque(false);
        eastPanel.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 28));

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
        CustomButton nextDay = new CustomButton("/MainUI/ShopUI/NEXT_DAY_BUTTON.png", "/MainUI/ShopUI/NEXT_DAY_BUTTON.png", 200, 85);
        nextDay.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(7));
        panel.add(nextDay);

        nextDay.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            try {
                parent.showDialog(new NewDayPanelUI("/MainUI/ShopUI/ISSUE_PANE.png", "Do you want to proceed into another day?", gameData));
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void initializeUpgrades(JPanel eastPanel) throws InvalidUILoadException {
        eastPanel.add(Box.createVerticalStrut(15));
        for (UpgradeNames upgrade : UpgradeNames.values()) {
            UpgradeUI upgradeUI = new UpgradeUI("/MainUI/ShopUI/ITEM_FRAME.png", gameData.getUpgradeManagement().getUpgrades().get(upgrade), gameData);
            upgradeUI.setOpaque(false);
            upgradeUI.setAlignmentX(Component.CENTER_ALIGNMENT);
            eastPanel.add(upgradeUI);
            eastPanel.add(Box.createVerticalStrut(20));
            upgrades.add(upgradeUI);
        }
    }

    private void initializeNorth(JPanel wrapper) throws InvalidUILoadException {
        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
        north.setOpaque(false);
        north.setBorder(BorderFactory.createEmptyBorder(5,10,15,10));

        initializeBalance(north);
        initializeMultipliers(north);

        wrapper.add(north, BorderLayout.NORTH);
    }

    private void initializeBalance(JPanel north) throws InvalidUILoadException {
        this.moneyPanelUI = new MoneyPanelUI("/MainUI/ShopUI/MONEY_PANEL.png", gameData);
        north.add(moneyPanelUI);
    }

    private void initializeMultipliers(JPanel north) throws InvalidUILoadException {
        multiplierButtons.add(new MultiplierButton(100, 100, 1, gameData, multiplierButtons));
        multiplierButtons.add(new MultiplierButton(100, 100, 5, gameData, multiplierButtons));
        multiplierButtons.add(new MultiplierButton(100, 100, 10, gameData, multiplierButtons));

        for (MultiplierButton multiplierButton : multiplierButtons){
            north.add(multiplierButton);
            north.add(Box.createHorizontalStrut(10));
        }
        multiplierButtons.get(0).doClick();
    }

    public void changeCard(String card) {
        this.cardLayout.show(cardPanel, card);
    }

    public void update() {
        for (ShopUI shopPanel : shopPanels) {
            shopPanel.update();
        }
        this.buyBounds.update(String.valueOf(gameData.getDayManagement().getCurrentDay().getDayBoughtAmount()), String.valueOf(gameData.getUpgradeManagement().getUpgradeData(UpgradeNames.BUY)));
        this.sellBounds.update(String.valueOf(gameData.getDayManagement().getCurrentDay().getDaySoldAmount()), String.valueOf(gameData.getUpgradeManagement().getUpgradeData(UpgradeNames.SELL)));
        this.dayUI.update();
        for (UpgradeUI upgradeUI : upgrades){
            upgradeUI.update();
        }
        this.moneyPanelUI.update();
    }
}
