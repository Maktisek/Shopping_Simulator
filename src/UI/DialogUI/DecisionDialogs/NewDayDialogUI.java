package UI.DialogUI.DecisionDialogs;

import AudioSystem.AudioType;
import Commands.CommandResult;
import Commands.CommandState;
import Commands.DayCommands.NewDayCommand;
import Game.GameData;
import UI.CreationUI.Buttons.ButtonType;
import UI.CreationUI.Buttons.CustomButton;
import UI.DialogUI.BasicDialogs.BaseDialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import UI.MainUI.ShopUI.Days.DaySummaryPanelUI;
import UI.MainUI.StatisticUI.EndPanelUI;
import Utilities.Important;

import javax.swing.*;

public class NewDayDialogUI extends BaseDialogUI {

    private final GameData gameData;

    public NewDayDialogUI(String imgFile, String message, GameData gameData) throws InvalidUILoadException {
        super(imgFile, message);
        this.gameData = gameData;
        initialize();
    }

    private void initialize() throws InvalidUILoadException {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
        wrapper.setOpaque(false);
        initializeCloseButton(wrapper);
        initializeYesButton(wrapper);

        add(Box.createVerticalStrut(Important.calculateDimension(15)));
        add(wrapper);
    }

    private void initializeCloseButton(JPanel wrapper) throws InvalidUILoadException {
        CustomButton closeButton = new CustomButton("/Sprites/ButtonSprites/CLOSE_BUTTON.png", Important.calculateDimension(130), Important.calculateDimension(75), ButtonType.EXIT);
        closeButton.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.hideDialog();
        });
        wrapper.add(closeButton);
        wrapper.add(Box.createHorizontalStrut(Important.calculateDimension(20)));
    }

    private void initializeYesButton(JPanel wrapper) throws InvalidUILoadException {
        CustomButton yesButton = new CustomButton("/Sprites/ButtonSprites/YES_BUTTON.png", Important.calculateDimension(130), Important.calculateDimension(75), ButtonType.ENTER);
        yesButton.addActionListener(e -> {
            CommandResult commandResult = new NewDayCommand(gameData).execute();
            System.out.println(commandResult.getMessage());
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            if (commandResult.getState() == CommandState.DONE) {
                parent.hideDialog();
                try {
                    Important.getAudioManagement().playSound("NewDay", AudioType.SOUNDS, 0, false);
                    Important.getAudioManagement().pauseSound(gameData.getShopManagement().getCurrentShop().getName(), AudioType.MUSIC);
                    parent.showDialog(new DaySummaryPanelUI(gameData));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (commandResult.getState() == CommandState.FAILED_END) {
                parent.hideDialog();
                try {
                    Important.getAudioManagement().playSound("EndOST", AudioType.MUSIC, 0, true);
                    Important.getAudioManagement().stopSound(gameData.getShopManagement().getCurrentShop().getName(), AudioType.MUSIC);
                    parent.initAndSwitchPanel(new EndPanelUI(gameData));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
                parent.resetCursor();
        }
    });
        wrapper.add(yesButton);
}


}
