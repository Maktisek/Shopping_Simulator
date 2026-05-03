package UI.MainUI.StockUI;

import Game.GameData;
import Items.ItemPlayer;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.CustomButton;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import UI.MainUI.ShopUI.Bounds.BoundPanelUI;
import Upgrade.Utilities.UpgradeNames;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static Utilities.Important.initializeScrollPane;

public class StockManagementUI extends JPanel {

    private final GameData gameData;
    private JLayeredPane layeredPane;
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
        layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));

        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(sidePanel, JLayeredPane.PALETTE_LAYER);

        this.add(layeredPane, BorderLayout.CENTER);
    }

    private void initializeMainPanel() throws InvalidUILoadException {
        this.mainPanel = new BackgroundPanel("/MainUI/ShopUI/STOCK_UI.png");
        this.mainPanel.setLayout(new BorderLayout());
        initializeGrid();
    }

    private void initializeGrid() throws InvalidUILoadException {
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(0, 3, 20, 20));
        grid.setOpaque(false);
        fillGrid(grid);

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        int gridWidth = (3 * 300) + (2 * 20);
        grid.setPreferredSize(new Dimension(gridWidth, grid.getPreferredSize().height));
        wrapper.add(grid);

        JScrollPane scrollPane = initializeScrollPane(wrapper, 16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(135, 0, 0, 0));


        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void fillGrid(JPanel grid) throws InvalidUILoadException {
        for (ItemPlayer itemPlayer : gameData.getPlayer().getStockItems()) {
            ItemPlayerUI panel = new ItemPlayerUI("/MainUI/ShopUI/ITEMPLAYER_FRAME.png", itemPlayer);
            Dimension dimension = new Dimension(300, 300);
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

        initializeNorth();
    }

    private void initializeNorth() throws InvalidUILoadException {
        JPanel north = new JPanel();
        north.setLayout(new BorderLayout());

        initializeBar(north);

        sidePanel.add(north, BorderLayout.NORTH);
    }

    private void initializeBar(JPanel north) throws InvalidUILoadException {
        BackgroundPanel bar = new BackgroundPanel("/MainUI/ShopUI/STOCK_UI_BAR.png");
        Dimension dimension = new Dimension(1920, 135);
        bar.setPreferredSize(dimension);
        bar.setMaximumSize(dimension);
        bar.setMaximumSize(dimension);
        bar.setLayout(new BoxLayout(bar, BoxLayout.X_AXIS));
        bar.setBorder(BorderFactory.createEmptyBorder(5,18,10,10));

        initializeExitButton(bar);
        initializeBoundPanel(bar);

        north.add(bar, BorderLayout.NORTH);
    }

    private void initializeExitButton(JPanel panel) throws InvalidUILoadException {
        CustomButton customButton = new CustomButton("/MainUI/ShopUI/ESCAPE_BUTTON.png", "/MainUI/ShopUI/ESCAPE_BUTTON.png", 100, 100);
        customButton.addActionListener(e ->{
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.switchPanel("Shop");
        });
        panel.add(customButton);
    }

    private void initializeBoundPanel(JPanel panel) throws InvalidUILoadException {
        String current = String.valueOf(gameData.getPlayer().calculateStocks());
        String bound = String.valueOf(gameData.getUpgradeManagement().getUpgradeData(UpgradeNames.STOCK));

        this.stockBound = new BoundPanelUI("/MainUI/ShopUI/CURRENT_PANE.png", current, bound, "/MainUI/ShopUI/STOCK_ICON.png");

        panel.add(Box.createHorizontalStrut(20));
        panel.add(stockBound);
    }

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
