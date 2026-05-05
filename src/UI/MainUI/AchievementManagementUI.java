package UI.MainUI;

import Achievements.Achievement;
import Achievements.AchievementTypes;
import Game.GameData;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.BarPanelUI;
import UI.CreationUI.GridPanelUI;
import UI.CreationUI.StrokeLabel;
import UI.DialogUI.DialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.StockUI.ItemPlayerUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static Utilities.Important.initializeScrollPane;

public class AchievementManagementUI extends JPanel {

    private GameData gameData;
    private JLayeredPane layeredPane;
    private BackgroundPanel mainPanel;
    private JPanel sidePanel;

    public AchievementManagementUI(GameData gameData) throws InvalidUILoadException {
        this.gameData = gameData;
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

    private void initializeGrid() throws InvalidUILoadException {
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

            GridPanelUI gridPanelUI = new GridPanelUI(3, 300, 300);
            gridPanelUI.setAlignmentX(Component.CENTER_ALIGNMENT);
            fillDoneAchievements(gridPanelUI.getGrid(), gameData.getAchievementManagement().getDoneAchievements().get(type));
            fillBlankSpots(gridPanelUI.getGrid(), type);
            fillPossibleAchievements(gridPanelUI.getGrid(), gameData.getAchievementManagement().getPossibleAchievements().get(type));
            gridPanelUI.finishGrid();
            wrapper.add(gridPanelUI);
        }
    }

    private void fillDoneAchievements(JPanel grid, ArrayList<Achievement> achievements) throws InvalidUILoadException {
//        if(achievements != null) {
//            for (Achievement achievement : achievements) {
//                grid.add(new DialogUI("/MainUI/ShopUI/ITEMPLAYER_FRAME.png", "Test"));
//            }
//        }
        for (int i = 0; i < 10; i++) {
            ItemPlayerUI panel = new ItemPlayerUI("/MainUI/ShopUI/ITEMPLAYER_FRAME.png", gameData.getPlayer().findItem("Bread"));
            Dimension dimension = new Dimension(300, 300);
            panel.setPreferredSize(dimension);
            panel.setMaximumSize(dimension);
            panel.setMaximumSize(dimension);
            grid.add(panel);
        }
    }

    private void fillBlankSpots(JPanel grid, AchievementTypes type) throws InvalidUILoadException {
        int leftOver = 3 - (10 % 3);
        for (int i = 0; i < leftOver; i++) {
            JPanel panel = new JPanel();
            panel.setOpaque(false);
            Dimension dimension = new Dimension(300, 300);
            panel.setPreferredSize(dimension);
            panel.setMaximumSize(dimension);
            panel.setMaximumSize(dimension);
            grid.add(panel);
        }
    }

    private void fillPossibleAchievements(JPanel grid, ArrayList<Achievement> achievements) throws InvalidUILoadException {
        for (int i = 0; i < 10; i++) {
            ItemPlayerUI panel = new ItemPlayerUI("/MainUI/ShopUI/ITEMPLAYER_FRAME.png", gameData.getPlayer().findItem("Bread"));
            Dimension dimension = new Dimension(300, 300);
            panel.setPreferredSize(dimension);
            panel.setMaximumSize(dimension);
            panel.setMaximumSize(dimension);
            grid.add(panel);
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


}
