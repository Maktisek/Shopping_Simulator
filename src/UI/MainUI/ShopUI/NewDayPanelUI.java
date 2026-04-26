package UI.MainUI.ShopUI;

import Commands.CommandResult;
import Commands.DayCommands.NewDayCommand;
import Game.GameData;
import UI.CreationUI.CustomButton;
import UI.InvalidUILoadException;
import UI.MainUI.IssueUI.IssueDialogUI;
import UI.MainUI.MainUI;

import javax.swing.*;

public class NewDayPanelUI extends IssueDialogUI {

    private GameData gameData;

    public NewDayPanelUI(String imgFile, String message, GameData gameData) throws InvalidUILoadException {
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

        add(Box.createVerticalStrut(15));
        add(wrapper);
    }

    private void initializeCloseButton(JPanel wrapper) throws InvalidUILoadException {
        CustomButton closeButton = new CustomButton("/MainUI/ShopUI/CLOSE_BUTTON.png", "/MainUI/ShopUI/CLOSE_BUTTON.png", 130, 75);
        closeButton.addActionListener(e ->{
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.hideDialog();
        });
        wrapper.add(closeButton);
        wrapper.add(Box.createHorizontalStrut(20));
    }

    private void initializeYesButton(JPanel wrapper) throws InvalidUILoadException {
        CustomButton yesButton = new CustomButton("/MainUI/ShopUI/YES_BUTTON.png", "/MainUI/ShopUI/YES_BUTTON.png", 130, 75);
        yesButton.addActionListener(e ->{
            CommandResult commandResult = new NewDayCommand(gameData).execute();
            System.out.println(commandResult.getMessage());
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.hideDialog();
            try {
                parent.showDialog(new DaySummaryPanelUI(gameData));
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
        });
        wrapper.add(yesButton);
    }


}
