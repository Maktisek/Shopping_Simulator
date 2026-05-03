package UI.MainUI;

import Achievements.Achievement;
import Game.GameData;
import UI.CreationUI.BackgroundPanel;
import UI.DialogUI.DialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.ShopUI.ShopManagementUI;
import UI.MainUI.StockUI.StockManagementUI;

import javax.swing.*;
import java.awt.*;

public class MainUI extends BackgroundPanel {

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JLayeredPane layeredPane;
    private JPanel overlay;
    private final GameData gameData;
    private Timer updater;
    private ShopManagementUI shopManagementUI;
    private StockManagementUI stockManagementUI;

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
        initializeStockManagementUI();

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

    private void initializeStockManagementUI() throws InvalidUILoadException {
        this.stockManagementUI = new StockManagementUI(this.gameData);
        this.mainPanel.add(stockManagementUI, "Stock");
    }

    public void switchPanel(String panel){
        this.cardLayout.show(mainPanel, panel);
    }

    public void update(){
         this.updater = new Timer(5, e ->{
            this.shopManagementUI.update();
            this.stockManagementUI.update();
             try {
                 checkForAchievements();
             } catch (InvalidUILoadException ex) {
                 throw new RuntimeException(ex);
             }
         });
        updater.start();
    }

    private void checkForAchievements() throws InvalidUILoadException {
        Achievement temp = this.gameData.getAchievementManagement().pollAchievement();
        if(temp != null){
            String message = "Achievement..." + temp.getName() + "...has been unlocked";
            System.out.println(message);
            showDialog(new DialogUI("/MainUI/ShopUI/ACHIEVEMENT_PANE.png", message));
        }
    }

    public void showDialog(JPanel customContent) {
        this.updater.stop();
        overlay.removeAll();
        overlay.add(customContent);
        overlay.setVisible(true);
        repaint();
    }

    public void hideDialog() {
        this.updater.start();
        overlay.setVisible(false);
        repaint();
    }

    public ShopManagementUI getShopManagementUI() {
        return shopManagementUI;
    }
}
