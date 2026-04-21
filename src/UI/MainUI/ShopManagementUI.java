package UI.MainUI;

import Commands.CommandResult;
import Commands.ShopCommands.ChangeShopLeftCommand;
import Commands.ShopCommands.ChangeShopRightCommand;
import Commands.ShopCommands.ShopDirection;
import Game.GameData;
import Shops.Shop;
import UI.CustomButton;
import UI.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShopManagementUI extends JPanel {

    private final JPanel cardPanel;
    private final JPanel mainPanel;
    private JPanel currentShop;
    private final JPanel overlay;
    private JLayeredPane layeredPane;
    private final GameData gameData;
    private CardLayout cardLayout;
    private final ArrayList<ShopUI> shopPanels;

    public ShopManagementUI(GameData gameData) throws InvalidUILoadException {
        this.gameData = gameData;
        this.shopPanels = new ArrayList<>();
        this.cardPanel = new JPanel();
        this.mainPanel = new JPanel(new BorderLayout());
        this.overlay = new JPanel(new GridBagLayout());

        initialize();
    }

    private void initialize() throws InvalidUILoadException {
        layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));


        this.cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        setLayout(new BorderLayout());

        initializeShops();
        initializeWestPanel();

        mainPanel.add(cardPanel, BorderLayout.CENTER);
        cardLayout.show(cardPanel, gameData.getShopManagement().getShops().get(0).getName().toString());

        overlay.setOpaque(false);
        overlay.setVisible(false);
        overlay.addMouseListener(new java.awt.event.MouseAdapter() {
        });

        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(overlay, JLayeredPane.MODAL_LAYER);
        layeredPane.add(initializeWestPanel(), JLayeredPane.PALETTE_LAYER);
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

    private JPanel initializeWestPanel() throws InvalidUILoadException {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);

        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setOpaque(false);
        initializeChangeShopButtons(westPanel);

        wrapper.add(westPanel, BorderLayout.WEST);
        return wrapper;
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

        switch (result.getState()) {
            case DONE: {
                changeCard(gameData.getShopManagement().getCurrentShop().getName().toString());
                break;
            }
            case FAILED_ISSUE: {
                try {
                    showShopDialog(new IssueFailDialogUI("/MainUI/ShopUI/ISSUE_PANE.png", result.getMessage()));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            }
            case FAILED_BUY: {
                try {
                    showShopDialog(new IssueBuyDialogUI("/MainUI/ShopUI/ISSUE_PANE.png", result.getMessage(), gameData, shopDirection));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            }
        }
    }

    public void changeCard(String card) {
        this.cardLayout.show(cardPanel, card);
    }

    public void update() {
        for (ShopUI shopPanel : shopPanels) {
            shopPanel.update();
        }
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
