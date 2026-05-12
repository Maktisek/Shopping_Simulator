package UI.MainUI.EndGameUI;

import UI.CreationUI.BackgroundPanel;
import UI.Exceptions.InvalidUILoadException;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

public class EndPanelUI extends BackgroundPanel {

    private JPanel mainPanel;
    private JPanel sidePanel;

    public EndPanelUI() throws InvalidUILoadException {
        super("/MainUI/ShopUI/STOCK_UI.png");
        initialization();
    }

    private void initialization() throws InvalidUILoadException {
        setLayout(new BorderLayout());
        initializeMainPanel();
        initializeSidePanel();
        initializeLayeredPane();
    }

    private void initializeMainPanel(){
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.setOpaque(false);

        initializeStatistic();

    }

    private void initializeStatistic(){
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);


    }

    private void initializeSidePanel() throws InvalidUILoadException {
        this.sidePanel = new JPanel();
        this.sidePanel.setLayout(new BorderLayout());
        this.sidePanel.setOpaque(false);

        BackgroundPanel bar = new BackgroundPanel("/MainUI/ShopUI/STOCK_UI_BAR.png");

        Dimension dimension = new Dimension(Important.width * Important.scale, 135 * Important.scale);
        bar.setPreferredSize(dimension);
        bar.setMaximumSize(dimension);
        bar.setMinimumSize(dimension);

        this.sidePanel.add(bar, BorderLayout.NORTH);
    }

    private void initializeLayeredPane(){
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));

        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(sidePanel, JLayeredPane.PALETTE_LAYER);

        this.add(layeredPane, BorderLayout.CENTER);
    }
}
