package UI.MainUI.ShopUI;

import DayCycle.Day;
import Game.GameData;
import UI.CreationUI.BackgroundPanel;
import UI.InvalidUILoadException;
import UI.MainUI.Utilities.StrokeLabel;

import javax.swing.*;
import java.awt.*;

public class DaySummaryPanelUI extends BackgroundPanel {

    private GameData gameData;
    private Day day;

    public DaySummaryPanelUI(GameData gameData) throws InvalidUILoadException {
        setImg("/MainUI/ShopUI/INFO_PANE.png");
        this.gameData = gameData;
        setLayout(new BorderLayout());
        initialization();
    }

    private void initialization(){
        initializeDimensions();
        initializeDay();
        initializeNorth();
    }

    private void initializeDay(){
        this.day = gameData.getDayManagement().getDaysDatabase().get(gameData.getDayManagement().getDaysDatabase().size() - 2);
    }



    private void initializeNorth(){
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(20, 0, 0 , 0));

        initializeDayName(wrapper);
        initializeSummaryLabel(wrapper);

        add(wrapper, BorderLayout.NORTH);
    }


    private void initializeDayName(JPanel wrapper){
        StrokeLabel name = new StrokeLabel(this.day.getDayName().toString(), 48.0f);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.add(name);
        wrapper.add(Box.createVerticalStrut(20));


    }

    private void initializeSummaryLabel(JPanel wrapper){
        StrokeLabel summaryLabel = new StrokeLabel("Summary", 24.0f);
        summaryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.add(summaryLabel);
    }


    private void initializeDimensions() {
        Dimension dimension = new Dimension(570, 720);
        setMinimumSize(new Dimension(dimension));
        setPreferredSize(new Dimension(dimension));
        setMaximumSize(new Dimension(dimension));
    }
}
