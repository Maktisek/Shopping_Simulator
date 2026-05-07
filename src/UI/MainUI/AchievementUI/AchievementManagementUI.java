package UI.MainUI.AchievementUI;

import Achievements.Achievement;
import Achievements.AchievementTypes;
import Game.GameData;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.BarPanelUI;
import UI.CreationUI.GridPanelUI;
import UI.CreationUI.StrokeLabel;
import UI.Exceptions.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static Utilities.Important.initializeScrollPane;

public class AchievementManagementUI extends JPanel {

    private final GameData gameData;
    private JLayeredPane layeredPane;
    private BackgroundPanel mainPanel;
    private final ArrayList<AchievementBoxUI> achievements;
    private JPanel sidePanel;

    public AchievementManagementUI(GameData gameData) throws InvalidUILoadException {
        this.gameData = gameData;
        this.achievements = new ArrayList<>();
        initialize();
    }

    private void initialize() throws InvalidUILoadException {
        setLayout(new BorderLayout());
        initializeMainPanel();
        initializeSidePanel();
        initializeLayerPane();
    }

    private void initializeLayerPane() {
        this.layeredPane = new JLayeredPane();
        this.layeredPane.setLayout(new OverlayLayout(layeredPane));

        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(sidePanel, JLayeredPane.PALETTE_LAYER);

        add(layeredPane);
    }

    private void initializeMainPanel() throws InvalidUILoadException {
        this.mainPanel = new BackgroundPanel("/MainUI/ShopUI/STOCK_UI.png");
        this.mainPanel.setLayout(new BorderLayout());
        initializeGrid();
    }

    public void initializeGrid() throws InvalidUILoadException {
        mainPanel.removeAll();
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        fillGrid(wrapper);

        JScrollPane scrollPane = initializeScrollPane(wrapper, 16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(135, 0, 0, 0));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void fillGrid(JPanel wrapper) throws InvalidUILoadException {
        for (AchievementTypes type : AchievementTypes.values()) {
            wrapper.add(Box.createVerticalStrut(20));
            StrokeLabel typeLabel = new StrokeLabel(type.toString() + " achievements", 40.0f);
            typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            wrapper.add(typeLabel);
            wrapper.add(Box.createVerticalStrut(20));

            GridPanelUI gridPanelUI = new GridPanelUI(3, 500);
            gridPanelUI.setAlignmentX(Component.CENTER_ALIGNMENT);
            fillPossibleAchievements(gridPanelUI.getGrid(), gameData.getAchievementManagement().getPossibleAchievements().get(type));
            gridPanelUI.finishGrid();

            wrapper.add(gridPanelUI);
        }
    }

    private void fillPossibleAchievements(JPanel grid, ArrayList<Achievement> achievements) throws InvalidUILoadException {
        if (achievements != null) {
            for (Achievement achievement : achievements) {
                AchievementBoxUI achievementBoxUI = new AchievementBoxUI(gameData, achievement);
                grid.add(achievementBoxUI);
                this.achievements.add(achievementBoxUI);
            }
        }
    }

    private void initializeSidePanel() throws InvalidUILoadException {
        this.sidePanel = new JPanel();
        this.sidePanel.setLayout(new BorderLayout());
        this.sidePanel.setOpaque(false);

        initializeBar();
    }

    private void initializeBar() throws InvalidUILoadException {
        BarPanelUI barPanelUI = new BarPanelUI("ACHIEVEMENTS");
        this.sidePanel.add(barPanelUI, BorderLayout.NORTH);
    }

    public void update() throws InvalidUILoadException {
        for (AchievementBoxUI achievementBoxUI : achievements){
            achievementBoxUI.update();
        }
        this.mainPanel.repaint();
    }


}
