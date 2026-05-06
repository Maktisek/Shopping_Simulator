package UI.MainUI.AchievementUI;

import Achievements.Achievement;
import UI.CreationUI.BackgroundPanel;
import UI.Exceptions.InvalidUILoadException;

import javax.swing.*;

public class AchievementBoxUI extends BackgroundPanel {

    Achievement achievement;
    AchievementUITypes type;

    public AchievementBoxUI(AchievementUITypes type) throws InvalidUILoadException {
        this.type = type;
        initialize();
    }

    private void initialize() throws InvalidUILoadException {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10,10,10));
        setOpaque(false);

        initializeImage();
    }

    private void initializeImage() throws InvalidUILoadException {
        switch (this.type){
            case POSSIBLE -> setImg("/MainUI/ShopUI/ACHIEVEMENT_MANAGEMENT_PANE.png");
            case DONE -> setImg("/MainUI/ShopUI/ACHIEVEMENT_MANAGEMENT_PANE_DONE.png");
        }
    }

    private void initializeIcon(){

    }

    private void initializeEast(){

    }



}
