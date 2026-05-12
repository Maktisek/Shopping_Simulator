package UI.MainUI.EndGameUI;

import Game.GameData;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.CustomButton;
import UI.CreationUI.StrokeLabel;
import UI.Exceptions.InvalidUILoadException;
import UI.InitialUI.MyFrame;
import UI.TitleUI.TitleScreenUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

import static Utilities.Important.initializeScrollPane;

public class EndPanelUI extends BackgroundPanel {

    private JPanel mainPanel;
    private JPanel sidePanel;
    private GameData gameData;

    public EndPanelUI(GameData gameData) throws InvalidUILoadException {
        super("/MainUI/ShopUI/STOCK_UI.png");
        this.gameData = gameData;
        initialization();
    }

    private void initialization() throws InvalidUILoadException {
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
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 300 * Important.scale, 0, 300 * Important.scale));

        fillWithStats(wrapper);
        initializeCloseGameButton(wrapper);

        JScrollPane scrollPane = initializeScrollPane(wrapper, 16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(135 * Important.scale, 0, 0, 0));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void fillWithStats(JPanel wrapper){
        String[] stats = Important.decodeString(this.gameData.toString());
        for (String s : stats){
            wrapper.add(Box.createVerticalStrut(20 * Important.scale));
            StrokeLabel stat = new StrokeLabel(Important.insertDots(s, 50), 28.0f);
            stat.setAlignmentX(Component.CENTER_ALIGNMENT);
            wrapper.add(stat);
        }
    }

    private void initializeCloseGameButton(JPanel wrapper) throws InvalidUILoadException {
        CustomButton close = new CustomButton("/MainUI/ShopUI/OK_BUTTON.png", 130, 75);
        close.addActionListener(e ->{
            JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
            parent.dispose();

            TitleScreenUI title;
            try {
                title = new TitleScreenUI();
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
            title.makeVisible();
        });

        close.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.add(Box.createVerticalStrut(20 * Important.scale));
        wrapper.add(close);
    }

    private void initializeSidePanel() throws InvalidUILoadException {
        this.sidePanel = new JPanel();
        this.sidePanel.setLayout(new BorderLayout());
        this.sidePanel.setOpaque(false);

        BackgroundPanel bar = new BackgroundPanel("/MainUI/ShopUI/END_BAR_UI.png");

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
