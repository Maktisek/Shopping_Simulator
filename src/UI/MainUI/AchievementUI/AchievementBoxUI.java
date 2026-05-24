package UI.MainUI.AchievementUI;

import Achievements.Achievement;
import AudioSystem.AudioType;
import Commands.AchievementCommands.ClaimAchievementRewardCommand;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import UI.CreationUI.Buttons.ButtonType;
import UI.CreationUI.Buttons.CustomButton;
import UI.CreationUI.Labels.StrokeLabel;
import UI.CreationUI.Panels.BackgroundPanel;
import UI.CreationUI.Utilities.UpdateAble;
import UI.DialogUI.BasicDialogs.DialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class AchievementBoxUI extends BackgroundPanel implements UpdateAble {

    private final GameData gameData;
    private final Achievement achievement;
    private AchievementUITypes type;
    private CustomButton claimButton;
    private StrokeLabel bound;
    private StrokeLabel percentualBound;

    public AchievementBoxUI(GameData gameData, Achievement achievement) throws InvalidUILoadException {
        this.type = AchievementUITypes.POSSIBLE;
        this.gameData = gameData;
        this.achievement = achievement;
        initialize();
    }

    private void initialize() throws InvalidUILoadException {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(Important.calculateDimension(10), Important.calculateDimension(10), Important.calculateDimension(10), Important.calculateDimension(10)));
        setOpaque(false);

        initializeImage();
        initializeClaimButton();
        initializeDimensions();
        initializeIcon();
        initializeEast();
    }

    private void initializeImage() throws InvalidUILoadException {
        switch (this.type) {
            case POSSIBLE -> setImg("/Sprites/AchievementSprites/ACHIEVEMENT_MANAGEMENT_PANE.png");
            case DONE -> setImg("/Sprites/AchievementSprites/ACHIEVEMENT_MANAGEMENT_PANE_DONE.png");
        }
    }

    private void initializeIcon() throws InvalidUILoadException {
        URL imageURL = getClass().getResource("/Sprites/IconSprites/" + achievement.getType().toString() + "_ICON.png");

        if (imageURL == null) {
            throw new InvalidUILoadException("The image " + "/Sprites/IconSprites/" + achievement.getType().toString() + "_ICON.png" + " was not found");
        }

        ImageIcon icon = new ImageIcon(imageURL);
        Image scaledImage = icon.getImage().getScaledInstance(Important.calculateDimension(150), Important.calculateDimension(150), Image.SCALE_SMOOTH);
        icon.setImage(scaledImage);


        JLabel label = new JLabel(icon, JLabel.CENTER);
        label.setOpaque(false);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);

        this.add(label);
    }


    private void initializeEast() {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);

        initializeName(wrapper);
        initializeDescription(wrapper);
        initializeSouth(wrapper);

        this.add(wrapper);
    }

    private void initializeName(JPanel wrapper) {
        StrokeLabel name = new StrokeLabel(this.achievement.getName(), 13);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.add(name);
    }

    private void initializeDescription(JPanel wrapper) {
        wrapper.add(Box.createVerticalStrut(5));
        StrokeLabel description = new StrokeLabel(this.achievement.getDescription(), 12);
        description.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.add(description);
    }

    private void initializeSouth(JPanel wrapper) {
        JPanel xPanel = new JPanel();
        xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));
        xPanel.setOpaque(false);
        xPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        Dimension dimension = new Dimension(Important.calculateDimension(400), Important.calculateDimension(74));
        xPanel.setPreferredSize(dimension);
        xPanel.setMaximumSize(dimension);
        xPanel.setMinimumSize(dimension);

        initializeBound(xPanel);
        initializePercentualBound(xPanel);
        xPanel.add(this.claimButton);

        wrapper.add(Box.createVerticalStrut(Important.calculateDimension(10)));
        wrapper.add(xPanel);
    }

    private void initializeBound(JPanel xPanel) {
        this.bound = new StrokeLabel(Important.parseMoney(this.achievement.getCurrent()) + "/" + Important.parseMoney(this.achievement.getBound()), 24);
        xPanel.add(bound);
    }

    private void initializePercentualBound(JPanel xPanel) {
        this.percentualBound = new StrokeLabel(this.achievement.calculatePercent() + " %",24);
        xPanel.add(percentualBound);
    }

    private void initializeClaimButton() throws InvalidUILoadException {
        this.claimButton = new CustomButton("/Sprites/ButtonSprites/CLAIM_BUTTON.png", 256, 74, ButtonType.NONE);
        this.claimButton.setVisible(false);
        this.claimButton.addActionListener(e ->{
            CommandResult result = new ClaimAchievementRewardCommand(this.gameData, this.achievement).execute();
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            if(result.getState() == CommandState.DONE){
                try {
                    Important.getAudioManagement().playSound("ClaimGoal", AudioType.SOUNDS, 0);
                    initializeImage();
                    this.claimButton.setVisible(false);
                    this.bound.setVisible(true);
                    parent.showDialog(new DialogUI("/Sprites/UtilityPanels/ISSUE_PANE.png", result.getMessage()));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
            }else {
                try {
                    parent.showDialog(new DialogUI("/Sprites/UtilityPanels/ISSUE_PANE.png", "Emm, this should not happen"));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void initializeDimensions() {
        Dimension dimension = new Dimension(Important.calculateDimension(500), Important.calculateDimension(250));
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
    }

    @Override
    public void update() throws InvalidUILoadException {
        if(achievement.getReward() == 0 && (type != AchievementUITypes.DONE)){
            this.claimButton.setVisible(false);
            this.type = AchievementUITypes.DONE;
            this.bound.setText("DONE");
            this.percentualBound.setVisible(false);
            initializeImage();
        }

        if (this.achievement.isDone() && (type != AchievementUITypes.DONE)) {
            this.type = AchievementUITypes.DONE;
            this.bound.setText("DONE");
            this.bound.setVisible(false);
            this.percentualBound.setVisible(false);
            this.claimButton.setVisible(true);
        }

        if (this.type == AchievementUITypes.POSSIBLE) {
            this.bound.setText(Important.parseMoney(this.achievement.getCurrent()) + "/" + Important.parseMoney(this.achievement.getBound()));
            this.percentualBound.setText(this.achievement.calculatePercent() + " %");
        }
    }

}
