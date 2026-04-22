package UI.MainUI.ShopUI;

import Commands.CommandResult;
import Commands.ShopCommands.ChangeShopLeftCommand;
import Commands.ShopCommands.ChangeShopRightCommand;
import Commands.ShopCommands.ShopDirection;
import Game.GameData;
import Shops.Shop;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.CustomButton;
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

    public ShopManagementUI(GameData gameData) throws InvalidUILoadException {
        super();
        this.gameData = gameData;
        this.shopPanels = new ArrayList<>();
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

        mainPanel.add(cardPanel, BorderLayout.CENTER);
        cardLayout.show(cardPanel, gameData.getShopManagement().getShops().get(0).getName().toString());


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
            cardPanel.add(shop, gameData.getShopManagement().getShops().get(i).getName().toString());
        }
    }

    private void initializeWestPanel(JPanel panel) throws InvalidUILoadException {
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setOpaque(false);
        initializeChangeShopButtons(westPanel);

        panel.add(westPanel, BorderLayout.WEST);
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
        panel.add(Box.createVerticalGlue());
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
                    parent.showShopDialog(new IssueFailDialogUI("/MainUI/ShopUI/ISSUE_PANE.png", result.getMessage()));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            }
            case FAILED_BUY: {
                try {
                    parent.showShopDialog(new IssueBuyDialogUI("/MainUI/ShopUI/ISSUE_PANE.png", result.getMessage(), gameData, shopDirection));
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
        setBounds.setBorder(BorderFactory.createEmptyBorder(0,0,27,10));

        setBounds.add(Box.createHorizontalStrut(1600));
        setBounds.add(bounds);
        setBounds.setOpaque(false);


        panel.add(setBounds, BorderLayout.SOUTH);
    }

    private void addBuyBoundPanel(JPanel panel) throws InvalidUILoadException {
        panel.add(Box.createVerticalStrut(12));

        String current = String.valueOf(gameData.getDayManagement().getCurrentDay().getDayBoughtAmount());
        String bound = String.valueOf(gameData.getUpgradeManagement().getUpgradeData(UpgradeNames.BUY));

        this.buyBounds = new BoundPanelUI("/MainUI/ShopUI/CURRENT_PANE.png", current, bound, "/MainUI/ShopUI/SHIP_ICON.png");

        panel.add(buyBounds);
    }

    private void addSellBoundPanel(JPanel panel) throws InvalidUILoadException {
        panel.add(Box.createVerticalStrut(12));
        String current = String.valueOf(gameData.getDayManagement().getCurrentDay().getDaySoldAmount());
        String bound = String.valueOf(gameData.getUpgradeManagement().getUpgradeData(UpgradeNames.SELL));

        this.sellBounds = new BoundPanelUI("/MainUI/ShopUI/CURRENT_PANE.png", current, bound, "/MainUI/ShopUI/SELL_ICON.png");

        panel.add(sellBounds);
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
    }
}
