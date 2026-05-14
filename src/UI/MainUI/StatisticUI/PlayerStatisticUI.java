package UI.MainUI.StatisticUI;

import Game.GameData;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.CustomButton;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

public class PlayerStatisticUI extends GameDataInfoUI{


    public PlayerStatisticUI(GameData gameData) throws InvalidUILoadException {
        super(gameData);
    }

    @Override
    public void initializeButton(JPanel wrapper) throws InvalidUILoadException {
        CustomButton ok = new CustomButton("/MainUI/ShopUI/OK_BUTTON.png", 130, 75);
        ok.addActionListener(e ->{
            MainUI mainUI = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            mainUI.switchPanel("Shop");
        });

        ok.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.add(Box.createVerticalStrut(Important.calculateDimension(20)));
        wrapper.add(ok);
    }

    @Override
    public BackgroundPanel initializeBar() throws InvalidUILoadException {
        BackgroundPanel bar = new BackgroundPanel("/MainUI/ShopUI/STATISTICS_BAR.png");

        Dimension dimension = new Dimension(Important.calculateDimension(Important.getWidth()), Important.calculateDimension(135));
        bar.setPreferredSize(dimension);
        bar.setMaximumSize(dimension);
        bar.setMinimumSize(dimension);

        return bar;
    }

}
