package UI.MainUI.StatisticUI;

import Game.GameData;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.CustomButton;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import UI.TitleUI.TitleScreenUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

public class EndPanelUI extends GameDataInfoUI {


    public EndPanelUI(GameData gameData) throws InvalidUILoadException {
        super(gameData);
        initialization();
    }

    @Override
    public void initializeButton(JPanel wrapper) throws InvalidUILoadException {
        CustomButton close = new CustomButton("/MainUI/ShopUI/QUIT_BUTTON.png", 130, 75);
        close.addActionListener(e ->{
            MainUI mainUI = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            mainUI.turnOff();


            TitleScreenUI title;
            try {
                title = new TitleScreenUI();
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
            title.makeVisible();
        });

        close.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.add(Box.createVerticalStrut(Important.calculateDimension(20)));
        wrapper.add(close);
    }

    @Override
    public BackgroundPanel initializeBar() throws InvalidUILoadException {
        BackgroundPanel bar = new BackgroundPanel("/MainUI/ShopUI/END_BAR_UI.png");

        Dimension dimension = new Dimension(Important.calculateDimension(Important.getWidth()), Important.calculateDimension(135));
        bar.setPreferredSize(dimension);
        bar.setMaximumSize(dimension);
        bar.setMinimumSize(dimension);

        return bar;
    }

}
