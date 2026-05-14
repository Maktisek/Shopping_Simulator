package UI.DialogUI;

import Commands.CommandResult;
import Commands.CommandState;
import Commands.DayCommands.NewDayCommand;
import Game.GameData;
import UI.CreationUI.CustomButton;
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
        CustomButton closeButton = new CustomButton("/MainUI/ShopUI/CLOSE_BUTTON.png", Important.calculateDimension(130), Important.calculateDimension(75));
        closeButton.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.hideDialog();
        });
        wrapper.add(closeButton);
        wrapper.add(Box.createHorizontalStrut(Important.calculateDimension(20)));
    }

    private void initializeYesButton(JPanel wrapper) throws InvalidUILoadException {
        CustomButton yesButton = new CustomButton("/MainUI/ShopUI/YES_BUTTON.png", Important.calculateDimension(130), Important.calculateDimension(75));
        yesButton.addActionListener(e -> {
            CommandResult commandResult = new NewDayCommand(gameData).execute();
            System.out.println(commandResult.getMessage());
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            if (commandResult.getState() == CommandState.DONE) {
                parent.hideDialog();
                try {
                    parent.showDialog(new DaySummaryPanelUI(gameData));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (commandResult.getState() == CommandState.FAILED_END) {
                parent.hideDialog();
                try {
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
