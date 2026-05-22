package UI.MainUI.StockUI;

import Game.GameData;
import Items.ItemPlayer;
import UI.CreationUI.*;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.ShopUI.Bounds.BoundPanelUI;
import UI.MainUI.ShopUI.Bounds.BoundTypes;
import Upgrade.Utilities.UpgradeNames;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static Utilities.Important.initializeScrollPane;

public class StockManagementUI extends JPanel implements UpdateAble {

    private final GameData gameData;
    private BackgroundPanel mainPanel;
    private JPanel sidePanel;
    private final ArrayList<ItemPlayerUI> items;
    private BoundPanelUI stockBound;

    public StockManagementUI(GameData gameData) throws InvalidUILoadException {
        this.gameData = gameData;
        this.items = new ArrayList<>();
        initialization();
    }

    private void initialization() throws InvalidUILoadException {
        setLayout(new BorderLayout());
        initializeMainPanel();
        initializeSidePanel();
        initializeLayerPane();

    }

    private void initializeLayerPane() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));

        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(sidePanel, JLayeredPane.PALETTE_LAYER);

        this.add(layeredPane, BorderLayout.CENTER);
    }

    private void initializeMainPanel() throws InvalidUILoadException {
        this.mainPanel = new BackgroundPanel("/Sprites/BackgroundSprites/BACKGROUND_PANE.png");
        this.mainPanel.setLayout(new BorderLayout());
        initializeGrid();
    }

    private void initializeGrid() throws InvalidUILoadException {
        GridPanelUI gridPanelUI = new GridPanelUI(3, Important.calculateDimension(300));
        fillGrid(gridPanelUI.getGrid());
        gridPanelUI.finishGrid();

        JScrollPane scrollPane = initializeScrollPane(gridPanelUI, 16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(Important.calculateDimension(135), 0, 0, 0));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void fillGrid(JPanel grid) throws InvalidUILoadException {
        for (ItemPlayer itemPlayer : gameData.getPlayer().getStockItems()) {
            ItemPlayerUI panel = new ItemPlayerUI("/Sprites/UtilityPanels/ITEMPLAYER_PANE.png", itemPlayer);
            Dimension dimension = new Dimension(Important.calculateDimension(300), Important.calculateDimension(300));
            panel.setPreferredSize(dimension);
            panel.setMaximumSize(dimension);
            panel.setMaximumSize(dimension);
            grid.add(panel);
            items.add(panel);
        }
    }


    private void initializeSidePanel() throws InvalidUILoadException {
        this.sidePanel = new JPanel();
        this.sidePanel.setLayout(new BorderLayout());
        this.sidePanel.setOpaque(false);

        initializeBar();
    }

    private void initializeBar() throws InvalidUILoadException {
        BarPanelUI bar = new BarPanelUI("STOCK", gameData);
        initializeBoundPanel(bar);
        sidePanel.add(bar, BorderLayout.NORTH);
    }


    private void initializeBoundPanel(JPanel panel) throws InvalidUILoadException {
        this.stockBound = new BoundPanelUI("/Sprites/UtilityPanels/CURRENT_PANE.png", "/Sprites/IconSprites/STOCK_ICON.png", gameData, BoundTypes.STOCK_BOUND);

        panel.add(Box.createHorizontalStrut(Important.calculateDimension(20)));
        panel.add(stockBound);
    }

    @Override
    public void update(){
        for (ItemPlayerUI itemPlayerUI : items){
            itemPlayerUI.update();
            updateBound();
        }
    }

    private void updateBound(){
        String current = String.valueOf(gameData.getPlayer().calculateStocks());
        String bound = String.valueOf(gameData.getUpgradeManagement().getUpgradeData(UpgradeNames.STOCK));
        this.stockBound.update(current, bound);
    }
}
