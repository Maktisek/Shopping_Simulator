package UI.MainUI.StatisticUI;

import Commands.CommandResult;
import Commands.UserInterfaceCommands.TurnOffTheGameCommand;
import Game.GameData;
import UI.CreationUI.Panels.BackgroundPanel;
import UI.CreationUI.Buttons.ButtonType;
import UI.CreationUI.Buttons.CustomButton;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

/**
 * This class an inheritor of {@link GameDataInfoUI}.
 * <p>
 *     This class implements statistics in "game over" way. When this panel is displayed
 *     player cannot come back to the game and has to either start new game or load back from his save.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class EndPanelUI extends GameDataInfoUI {


    public EndPanelUI(GameData gameData) throws InvalidUILoadException {
        super(gameData);
        initialization();
    }

    @Override
    public void initializeButton(JPanel wrapper) throws InvalidUILoadException {
        CustomButton close = new CustomButton("/Sprites/ButtonSprites/QUIT_BUTTON.png", 130, 75, ButtonType.EXIT);
        close.addActionListener(e ->{
            MainUI mainUI = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            mainUI.hideDialog();

            SwingUtilities.invokeLater(() -> {
                CommandResult result = new TurnOffTheGameCommand(mainUI).execute();
                System.out.println(result.getMessage());
            });
        });

        close.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.add(Box.createVerticalStrut(Important.calculateDimension(20)));
        wrapper.add(close);
    }

    @Override
    public BackgroundPanel initializeBar() throws InvalidUILoadException {
        BackgroundPanel bar = new BackgroundPanel("/Sprites/BarSprites/END_BAR_UI.png");

        Dimension dimension = new Dimension(Important.calculateDimension(Important.getWidth()), Important.calculateDimension(135));
        bar.setPreferredSize(dimension);
        bar.setMaximumSize(dimension);
        bar.setMinimumSize(dimension);

        return bar;
    }

}
