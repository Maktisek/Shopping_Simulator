package UI.MainUI;

import Achievements.Achievement;
import AudioSystem.AudioType;
import Game.GameData;
import UI.CreationUI.Panels.BackgroundPanel;
import UI.CreationUI.Utilities.DialogAble;
import UI.CreationUI.Utilities.UpdateAble;
import UI.DialogUI.BasicDialogs.DialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.InitialUI.MyFrame;
import UI.MainUI.AchievementUI.AchievementManagementUI;
import UI.MainUI.ShopUI.ShopManagement.ShopManagementUI;
import UI.MainUI.StatisticUI.PlayerStatisticUI;
import UI.MainUI.StockUI.StockManagementUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

/**
 * This class is the class where all UI in-game panels and features meet. It simply holds all UI data and manipulates
 * with them. An analogy can be found in {@link GameData}, but unlike {@link GameData} this class holds UI data.
 * <p>
 *     It implements {@link CardLayout} into its functionality, and it is the main core of the whole system.
 * </p>
 * Also it features the main updating timer {@link #updater} and achievement checker {@link #achievementUpdater}.
 * Both of those timers are responsible for updating the whole UI system.
 * <p>
 *     Also dialogs are appearing here (implementation of {@link DialogAble}), since {@link #mainPanel} shows the game {@link #overlay} can show dialogs.
 *     Both of them are then put inside {@link JLayeredPane}. This system can make any normal {@link JPanel} as a modal dialog.
 * </p>
 * @author Matěj Pospíšil
 * @since 1.0 - (pre-release version)
 */
public class MainUI extends BackgroundPanel implements UpdateAble, DialogAble {

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

    /**
     * Used if there is any external panel to be shown as a main panel.
     * <p>
     *     It is named {@code TEMP}, but cloud be named any name. The only
     *     non-possible names are names, which are already used.
     *     When there is new component added into {@link CardLayout} and has name as some of the
     *     currently existing, the older one gets replaced.
     * </p>
     * @param panel the panel to be shown
     */
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
            try {
                this.stockManagementUI.update();
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
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
            Important.getAudioManagement().pauseSound(gameData.getShopManagement().getCurrentShop().getName(), AudioType.MUSIC);
            showDialog(new DialogUI("/Sprites/AchievementSprites/ACHIEVEMENT_PANE.png", message, "NewGoal", gameData));
        }
    }

    @Override
    public void showDialog(JPanel customContent) {
        overlay.add(customContent);
        overlay.setVisible(true);
        repaint();
    }


    @Override
    public void hideDialog() {
        this.achievementUpdater.start();
        overlay.setVisible(false);
        overlay.removeAll();
        repaint();
    }

    /**
     * This method is useful when it comes to leaving into {@link UI.TitleUI.TitleScreenUI}.
     * <p>
     *     All timers have to be turned off, because if that was not done, they would run in the background and
     *     the system would be overwhelmed.
     * </p>
     */
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
