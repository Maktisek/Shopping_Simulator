package UI.MainUI;

import Achievements.Achievement;
import Game.GameData;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.UpdateAble;
import UI.DialogUI.DialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.InitialUI.MyFrame;
import UI.MainUI.AchievementUI.AchievementManagementUI;
import UI.MainUI.ShopUI.ShopManagement.ShopManagementUI;
import UI.MainUI.StatisticUI.PlayerStatisticUI;
import UI.MainUI.StockUI.StockManagementUI;

import javax.swing.*;
import java.awt.*;

public class MainUI extends BackgroundPanel implements UpdateAble {

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private final JPanel overlay;
    private final GameData gameData;
    private Timer updater;
    private Timer achievementUpdater;
    private ShopManagementUI shopManagementUI;
    private StockManagementUI stockManagementUI;
    private AchievementManagementUI achievementManagementUI;
    private PlayerStatisticUI playerStatisticUI;

    public MainUI(GameData gameData) throws InvalidUILoadException {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));

        this.overlay = new JPanel(new GridBagLayout());
        this.overlay.setOpaque(false);
        this.overlay.setVisible(false);
        this.overlay.addMouseListener(new java.awt.event.MouseAdapter() {
        });

        this.gameData = gameData;

        initialize();
        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(overlay, JLayeredPane.MODAL_LAYER);
        setLayout(new BorderLayout());
        add(layeredPane, BorderLayout.CENTER);
    }

    private void initialize() throws InvalidUILoadException {
        resetCursor();
        initializeMainPanel();
        initializeShopManagementUI();
        initializeStockManagementUI();
        initializeAchievementManagementUI();
        initializeStatistic();

        this.cardLayout.show(mainPanel, "Shop");

        update();
    }

    private void initializeMainPanel() {
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
    }

    private void initializeShopManagementUI() throws InvalidUILoadException {
        this.shopManagementUI = new ShopManagementUI(this.gameData);
        this.mainPanel.add(shopManagementUI, "Shop");
    }

    private void initializeStockManagementUI() throws InvalidUILoadException {
        this.stockManagementUI = new StockManagementUI(this.gameData);
        this.mainPanel.add(stockManagementUI, "STOCK");
    }

    private void initializeAchievementManagementUI() throws InvalidUILoadException {
        this.achievementManagementUI = new AchievementManagementUI(gameData);
        this.mainPanel.add(achievementManagementUI, "ACHIEVEMENTS");
    }

    private void initializeStatistic() throws InvalidUILoadException {
        this.playerStatisticUI = new PlayerStatisticUI(gameData);
        this.mainPanel.add(playerStatisticUI, "STATS");
    }

    public void switchPanel(String panel) {
        this.cardLayout.show(mainPanel, panel);
    }

    public void initAndSwitchPanel(JPanel panel){
        this.mainPanel.add(panel, "TEMP");
        switchPanel("TEMP");
    }

    @Override
    public void update() {
        this.updater = new Timer(5, e -> {
            try {
                this.shopManagementUI.update();
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
            this.stockManagementUI.update();
            try {
                this.achievementManagementUI.update();
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
            this.playerStatisticUI.update();
        });
        updater.start();

        this.achievementUpdater = new Timer(5, e -> {
            try {
                checkForAchievements();
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
        });
        achievementUpdater.start();
    }

    public void stopAllTimers() {
        this.updater.stop();
        this.achievementUpdater.stop();
    }

    public void startAllTimers(){
        this.updater.start();
        this.achievementUpdater.start();
    }

    private void checkForAchievements() throws InvalidUILoadException {
        Achievement temp = this.gameData.getAchievementManagement().peekAchievement();
        if (temp != null && overlay.getComponents().length == 0) {
            this.gameData.getAchievementManagement().pollAchievement();
            this.achievementUpdater.stop();
            String message = "Goal \"" + temp.getName() + "\" has been reached";
            System.out.println(message);
            showDialog(new DialogUI("/Sprites/AchievementSprites/ACHIEVEMENT_PANE.png", message, "NewGoal"));
        }
    }

    public void showDialog(JPanel customContent) throws InvalidUILoadException {
        overlay.add(customContent);
        overlay.setVisible(true);
        repaint();
    }

    public void hideDialog() {
        this.achievementUpdater.start();
        overlay.setVisible(false);
        overlay.removeAll();
        repaint();
    }

    public void turnOff() {
        stopAllTimers();
        MyFrame parent = getMyFrame();
        parent.stopTimer();
        parent.dispose();
    }

    public ShopManagementUI getShopManagementUI() {
        return shopManagementUI;
    }

    public MyFrame getMyFrame() {
        return (MyFrame) SwingUtilities.getAncestorOfClass(MyFrame.class, this);
    }
}
