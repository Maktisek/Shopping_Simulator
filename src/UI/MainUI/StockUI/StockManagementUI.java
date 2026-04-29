package UI.MainUI.StockUI;

import Game.GameData;
import UI.CreationUI.BackgroundPanel;
import UI.InvalidUILoadException;

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

        BackgroundPanel bar = new BackgroundPanel("/MainUI/ShopUI/STOCK_UI_BAR.png");
        Dimension dimension = new Dimension(1920, 135);
        bar.setPreferredSize(dimension);
        bar.setMaximumSize(dimension);
        bar.setMaximumSize(dimension);


        north.add(bar, BorderLayout.NORTH);

        sidePanel.add(north, BorderLayout.NORTH);
    }



}
