package UI.MainUI.StatisticUI;

import Game.GameData;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.StrokeLabel;
import UI.Exceptions.InvalidUILoadException;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static Utilities.Important.initializeScrollPane;

public abstract class GameDataInfoUI extends BackgroundPanel {

    protected JPanel mainPanel;
    protected JPanel sidePanel;
    protected final GameData gameData;
    protected ArrayList<StrokeLabel> labels;

    public GameDataInfoUI(GameData gameData) throws InvalidUILoadException {
        super("/ShopSprites/STOCK_UI.png");
        this.gameData = gameData;
        this.labels = new ArrayList<>();
        initialization();
    }

    protected void initialization() throws InvalidUILoadException {
        setLayout(new BorderLayout());
        initializeMainPanel();
        initializeSidePanel();
        initializeLayeredPane();
    }

    private void initializeMainPanel() throws InvalidUILoadException {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BorderLayout());
        this.mainPanel.setOpaque(false);

        initializeStatistic();
    }

    private void initializeStatistic() throws InvalidUILoadException {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, Important.calculateDimension(300), 0, Important.calculateDimension(300)));

        fillWithStats(wrapper);
        initializeButton(wrapper);

        JScrollPane scrollPane = initializeScrollPane(wrapper, 16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(Important.calculateDimension(135), 0, 0, 0));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void fillWithStats(JPanel wrapper){
        String[] stats = Important.decodeString(this.gameData.toString());
        for (String s : stats){
            wrapper.add(Box.createVerticalStrut(Important.calculateDimension(20)));
            StrokeLabel stat = new StrokeLabel(Important.insertDots(s, 50), 28);
            stat.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.labels.add(stat);
            wrapper.add(stat);
        }
    }

    public abstract void initializeButton(JPanel wrapper) throws InvalidUILoadException;

    private void initializeSidePanel() throws InvalidUILoadException {
        this.sidePanel = new JPanel();
        this.sidePanel.setLayout(new BorderLayout());
        this.sidePanel.setOpaque(false);
        this.sidePanel.add(initializeBar(), BorderLayout.NORTH);
    }

    public abstract BackgroundPanel initializeBar() throws InvalidUILoadException;

    private void initializeLayeredPane(){
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(new OverlayLayout(layeredPane));

        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(sidePanel, JLayeredPane.PALETTE_LAYER);

        this.add(layeredPane, BorderLayout.CENTER);
    }
}
