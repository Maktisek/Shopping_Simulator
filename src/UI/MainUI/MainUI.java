package UI.MainUI;

import Game.GameData;
import UI.InvalidUILoadException;
import UI.MainUI.ShopUI.ShopManagementUI;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JPanel{

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JLayeredPane layeredPane;
    private JPanel overlay;
    private final GameData gameData;
    private ShopManagementUI shopManagementUI;

    public MainUI(GameData gameData) throws InvalidUILoadException {
        this.layeredPane = new JLayeredPane();
        this.layeredPane.setLayout(new OverlayLayout(layeredPane));

        this.overlay = new JPanel(new GridBagLayout());
        this.overlay.setOpaque(false);
        this.overlay.setVisible(false);
        this.overlay.addMouseListener(new java.awt.event.MouseAdapter() {
        });


        this.gameData = gameData;

        initialize();
        this.layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        this.layeredPane.add(overlay, JLayeredPane.MODAL_LAYER);
        setLayout(new BorderLayout());
        add(layeredPane, BorderLayout.CENTER);
    }

    private void initialize() throws InvalidUILoadException {
        initializeMainPanel();
        initializeShopManagementUI();

        this.cardLayout.show(mainPanel, "Shop");

        update();
    }

    private void initializeMainPanel() {
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
    }

    private void initializeShopManagementUI() throws InvalidUILoadException{
        this.shopManagementUI = new ShopManagementUI(this.gameData);
        this.mainPanel.add(shopManagementUI, "Shop");
    }

    public void update(){
        Timer updater = new Timer(20, e ->{
            this.shopManagementUI.update();
        });
        updater.start();
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

    public ShopManagementUI getShopManagementUI() {
        return shopManagementUI;
    }
}
