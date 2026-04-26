package UI.MainUI.ShopUI;

import DayCycle.Day;
import Game.GameData;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.CustomButton;
import UI.InvalidUILoadException;
import UI.MainUI.MainUI;
import UI.MainUI.Utilities.StrokeLabel;
import Utilities.Important;

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

    private void initialization() throws InvalidUILoadException {
        initializeDimensions();

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setOpaque(false);

        initializeDay();
        initializeNorth(mainContent);
        initializeCenter(mainContent);
        initializeSouth(mainContent);


        add(mainContent, BorderLayout.CENTER);
    }

    private void initializeDay() {
        this.day = gameData.getDayManagement().getDaysDatabase().get(gameData.getDayManagement().getDaysDatabase().size() - 2);
    }


    private void initializeNorth(JPanel mainPanel) {
        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        north.setOpaque(false);
        north.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        initializeDayName(north);
        initializeSummaryLabel(north);

        north.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(north);
    }


    private void initializeDayName(JPanel north) {
        StrokeLabel name = new StrokeLabel(this.day.getDayName().toString(), 48.0f);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        north.add(name);
        north.add(Box.createVerticalStrut(20));
    }

    private void initializeSummaryLabel(JPanel north) {
        StrokeLabel summaryLabel = new StrokeLabel("Summary", 24.0f);
        summaryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        north.add(summaryLabel);
    }

    private void initializeCenter(JPanel mainPanel) {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        center.setBorder(BorderFactory.createEmptyBorder(120, 5, 0, 5));

        initializeDayStatistics(center);
        center.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(center);
    }

    private void initializeDayStatistics(JPanel center) {
        String[] description = Important.decodeString(this.day.information());
        for (String s : description) {
            StrokeLabel label = new StrokeLabel(Important.insertDots(s, 42), 13.0f);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            center.add(label);
            center.add(Box.createVerticalStrut(20));
        }
    }

    private void initializeSouth(JPanel mainPanel) throws InvalidUILoadException {
        JPanel south = new JPanel();
        south.setLayout(new BoxLayout(south, BoxLayout.Y_AXIS));
        south.setOpaque(false);
        south.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        initializeDeliveryLabel(south);
        initializeDeliveries(south);
        initializeOkButton(south);
        south.setAlignmentX(Component.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(south);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar().setUnitIncrement(24);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        mainPanel.add(scrollPane);
    }

    private void initializeDeliveryLabel(JPanel south) {
        StrokeLabel delivery = new StrokeLabel("Delivery", 24.0f);
        delivery.setAlignmentX(Component.CENTER_ALIGNMENT);
        south.add(delivery);
        south.add(Box.createVerticalStrut(20));
    }

    private void initializeDeliveries(JPanel south) {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        String info = gameData.getPlayer().information();
        if(info == null){
                StrokeLabel label = new StrokeLabel("Nothing was delivered", 14.0f);
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
                wrapper.add(label);
                wrapper.add(Box.createVerticalStrut(10));
                south.add(wrapper);
                 return;
            }

        String[] deliveries = Important.decodeString(info);

        for (String s : deliveries) {
            StrokeLabel label = new StrokeLabel(Important.insertDots(s, 45), 13.0f);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            wrapper.add(label);
            wrapper.add(Box.createVerticalStrut(20));
        }
        south.add(wrapper);
    }

    private void initializeOkButton(JPanel south) throws InvalidUILoadException {
        CustomButton okButton = new CustomButton("/MainUI/ShopUI/OK_BUTTON.png","/MainUI/ShopUI/OK_BUTTON.png", 130, 75);
        okButton.addActionListener(e ->{
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.hideDialog();
        });
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        south.add(okButton);
    }

    private void initializeDimensions() {
        Dimension dimension = new Dimension(570, 720);
        setMinimumSize(new Dimension(dimension));
        setPreferredSize(new Dimension(dimension));
        setMaximumSize(new Dimension(dimension));
    }
}
