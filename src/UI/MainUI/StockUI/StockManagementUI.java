package UI.MainUI.StockUI;

import Game.GameData;
import Items.ItemPlayer;
import UI.CreationUI.BackgroundPanel;
import UI.InvalidUILoadException;
import UI.MainUI.ShopUI.CustomScrollBarUI;

import javax.swing.*;
import java.awt.*;

public class StockManagementUI extends JPanel {

    private GameData gameData;
    private JLayeredPane layeredPane;
    private BackgroundPanel mainPanel;
    private JPanel sidePanel;

    public StockManagementUI(GameData gameData) throws InvalidUILoadException {
        this.gameData = gameData;
        initialization();
    }

    private void initialization() throws InvalidUILoadException {
        setLayout(new BorderLayout());
        initializeMainPanel();
        initializeSidePanel();
        initializeLayerPane();

    }

    private void initializeLayerPane(){
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
        wrapper.setLayout(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(20,400,0,400));
        wrapper.add(grid, BorderLayout.CENTER);

        JScrollPane scrollPane = initializeScrollPane(wrapper);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(135,0,0,0));



        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void fillGrid(JPanel grid) throws InvalidUILoadException {
        for (int i = 0; i < 50; i++) {
            BackgroundPanel panel = new BackgroundPanel("/MainUI/ShopUI/ITEMPLAYER_FRAME.png");
            Dimension dimension = new Dimension(300, 300);
            panel.setPreferredSize(dimension);
            panel.setMaximumSize(dimension);
            panel.setMaximumSize(dimension);
            grid.add(panel);
        }
    }

    private JScrollPane initializeScrollPane(JPanel grid) throws InvalidUILoadException {
        JScrollPane scrollPane = new JScrollPane(grid);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        return scrollPane;
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

        north.add(bar, BorderLayout.NORTH);
    }



}
