package UI.MainUI.StatisticUI;

import AudioSystem.AudioType;
import Game.GameData;
import UI.CreationUI.Panels.BackgroundPanel;
import UI.CreationUI.Buttons.ButtonType;
import UI.CreationUI.Buttons.CustomButton;
import UI.CreationUI.Utilities.UpdateAble;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

/**
 * This class an inheritor of {@link GameDataInfoUI}.
 * <p>
 * This class implements statistics in classic way letting player continue after
 * reading.
 * </p>
 * Unlike {@link EndPanelUI} it implements {@link UpdateAble}, because an instance of this class is primarily located in {@link MainUI}
 * and has to update itself every tick.
 * <p>
 *     It is a bad practise to update this panel everytime player opens it, since it will look laggy.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class PlayerStatisticUI extends GameDataInfoUI implements UpdateAble {


    public PlayerStatisticUI(GameData gameData) throws InvalidUILoadException {
        super(gameData);
    }

    @Override
    public void initializeButton(JPanel wrapper) throws InvalidUILoadException {
        CustomButton ok = new CustomButton("/Sprites/ButtonSprites/CLOSE_BUTTON.png", 130, 75, ButtonType.EXIT);
        ok.addActionListener(e -> {
            Important.getAudioManagement().stopSound("StatsOST", AudioType.MUSIC);
            Important.getAudioManagement().resumeSound(gameData.getShopManagement().getCurrentShop().getName(), AudioType.MUSIC, false);
            MainUI mainUI = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            mainUI.switchPanel("Shop");
        });

        ok.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.add(Box.createVerticalStrut(Important.calculateDimension(20)));
        wrapper.add(ok);
    }

    @Override
    public BackgroundPanel initializeBar() throws InvalidUILoadException {
        BackgroundPanel bar = new BackgroundPanel("/Sprites/BarSprites/STATISTICS_BAR.png");

        Dimension dimension = new Dimension(Important.calculateDimension(Important.getWidth()), Important.calculateDimension(135));
        bar.setPreferredSize(dimension);
        bar.setMaximumSize(dimension);
        bar.setMinimumSize(dimension);

        return bar;
    }

    @Override
    public void update() {
        String[] stats = Important.decodeString(this.gameData.toString());
        for (int i = 0; i < stats.length; i++) {
            this.labels.get(i).setText(Important.insertDots(stats[i], 50));
        }
    }
}
