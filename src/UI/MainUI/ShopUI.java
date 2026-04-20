package UI.MainUI;

import Commands.CommandResult;
import Commands.CommandState;
import Commands.ShopCommands.ChangeShopLeftCommand;
import Commands.ShopCommands.ChangeShopRightCommand;
import Game.GameData;
import Shops.Shop;
import UI.BackgroundPanel;
import UI.CustomButton;
import UI.InvalidUILoadException;
import Upgrade.UpgradeNames;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;


public class ShopUI extends BackgroundPanel {

    private final Shop shop;
    private final GameData gameData;
    private JLayeredPane layeredPane;
    private JPanel overlay;

    private BoundPanelUI buyBounds;
    private BoundPanelUI sellBounds;
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

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        initializeSouth(mainPanel);
        initializeWest(mainPanel);

        overlay = new JPanel(new GridBagLayout());
        overlay.setOpaque(false);
        overlay.setVisible(false);
        overlay.addMouseListener(new java.awt.event.MouseAdapter() {
        });

        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(overlay, JLayeredPane.MODAL_LAYER);

        add(layeredPane);
    }

    private void initializeSouth(JPanel panel) throws InvalidUILoadException {
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        southPanel.setOpaque(false);
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 40));

        initializeShopItems(southPanel);
        initializeNPCItems(southPanel);
        initializeBounds(southPanel);

        panel.add(southPanel, BorderLayout.SOUTH);
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

    private void initializeBounds(JPanel panel) throws InvalidUILoadException {
        JPanel bounds = new JPanel();
        bounds.setLayout(new BoxLayout(bounds, BoxLayout.Y_AXIS));
        bounds.setOpaque(false);

        addBuyBoundPanel(bounds);
        addSellBoundPanel(bounds);

        panel.add(bounds);
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

    private void initializeWest(JPanel panel) throws InvalidUILoadException{
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setOpaque(false);

        initializeChangeShopButtons(westPanel);
        panel.add(westPanel, BorderLayout.WEST);
    }

    private void initializeChangeShopButtons(JPanel panel) throws InvalidUILoadException{
        panel.add(Box.createVerticalGlue());
        CustomButton previous = new CustomButton("/MainUI/ShopUI/PREVIOUS_SHOP_BUTTON.png", "/MainUI/ShopUI/PREVIOUS_SHOP_BUTTON.png", 162, 162);
        CustomButton next = new CustomButton("/MainUI/ShopUI/NEXT_SHOP_BUTTON.png", "/MainUI/ShopUI/NEXT_SHOP_BUTTON.png", 162, 162);

        previous.setAlignmentX(Component.CENTER_ALIGNMENT);
        next.setAlignmentX(Component.CENTER_ALIGNMENT);

        previous.addActionListener(e ->{
            CommandResult result = new ChangeShopLeftCommand(gameData).execute();
            System.out.println(result.getMessage());
            if (Objects.requireNonNull(result.getState()) == CommandState.FAILED_ISSUE) {
                try {
                    showShopDialog(new IssueDialogUI("/MainUI/ShopUI/ISSUE_PANE.png",result.getMessage()));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        next.addActionListener(e ->{
            CommandResult result = new ChangeShopRightCommand(gameData).execute();
            System.out.println(result.getMessage());
            if (Objects.requireNonNull(result.getState()) == CommandState.FAILED_ISSUE) {
                try {
                    showShopDialog(new IssueDialogUI("/MainUI/ShopUI/ISSUE_PANE.png",result.getMessage()));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        panel.add(previous);
        panel.add(next);
        panel.add(Box.createVerticalGlue());
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

    public void update() {
        this.buyBounds.update(String.valueOf(gameData.getDayManagement().getCurrentDay().getDayBoughtAmount()), String.valueOf(gameData.getUpgradeManagement().getUpgradeData(UpgradeNames.BUY)));
        this.sellBounds.update(String.valueOf(gameData.getDayManagement().getCurrentDay().getDaySoldAmount()), String.valueOf(gameData.getUpgradeManagement().getUpgradeData(UpgradeNames.SELL)));
        for (ItemUI itemUI : items){
            itemUI.update();
        }
    }

}
