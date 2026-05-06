package UI.MainUI.AchievementUI;

import Achievements.Achievement;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.StrokeLabel;
import UI.Exceptions.InvalidUILoadException;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class AchievementBoxUI extends BackgroundPanel {

    private final Achievement achievement;
    private final AchievementUITypes type;

    public AchievementBoxUI(Achievement achievement, AchievementUITypes type) throws InvalidUILoadException {
        this.type = type;
        this.achievement = achievement;
        initialize();
    }

    private void initialize() throws InvalidUILoadException {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10,10,10));
        setOpaque(false);

        initializeImage();
        initializeDimensions();
        initializeIcon();
        initializeEast();
    }

    private void initializeImage() throws InvalidUILoadException {
        switch (this.type){
            case POSSIBLE -> setImg("/MainUI/ShopUI/ACHIEVEMENT_MANAGEMENT_PANE.png");
            case DONE -> setImg("/MainUI/ShopUI/ACHIEVEMENT_MANAGEMENT_PANE_DONE.png");
        }
    }

    private void initializeIcon() throws InvalidUILoadException {
        URL imageURL = getClass().getResource("/MainUI/ShopUI/"+achievement.getType().toString()+"_ICON.png");

        if(imageURL == null){
            throw new InvalidUILoadException("The image " + "/MainUI/ShopUI/"+achievement.getType().toString()+"_ICON.png" + " was not found");
        }

        ImageIcon icon = new ImageIcon(imageURL);
        Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        icon.setImage(scaledImage);



        JLabel label = new JLabel(icon, JLabel.CENTER);
        label.setOpaque(false);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);

        this.add(label);
    }


    private void initializeEast(){
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);

        initializeName(wrapper);
        initializeDescription(wrapper);
        initializeSouth(wrapper);

        this.add(wrapper);
    }

    private void initializeName(JPanel wrapper){
        StrokeLabel name = new StrokeLabel(this.achievement.getName(), 13.0f);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.add(name);
    }

    private void initializeDescription(JPanel wrapper){
        wrapper.add(Box.createVerticalStrut(5));
        StrokeLabel description = new StrokeLabel(this.achievement.getDescription(), 12.0f);
        description.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.add(description);
    }

    private void initializeSouth(JPanel wrapper){
        JPanel xPanel = new JPanel();
        xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
        xPanel.setOpaque(false);
        xPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        initializeBound(xPanel);

        wrapper.add(Box.createVerticalStrut(20));
        wrapper.add(xPanel);
    }

    private void initializeBound(JPanel xPanel){
        switch (this.type){
            case POSSIBLE -> {
                StrokeLabel bound = new StrokeLabel(Important.parseMoney(this.achievement.getCurrent()) + "/" + Important.parseMoney(this.achievement.getBound()), 32.0f);
                xPanel.add(bound);
            }
            case DONE -> {
                StrokeLabel bound = new StrokeLabel("DONE", 32.0f);
                xPanel.add(bound);
            }
        }
    }

    private void initializeDimensions(){
        Dimension dimension = new Dimension(500, 250);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
    }


}
