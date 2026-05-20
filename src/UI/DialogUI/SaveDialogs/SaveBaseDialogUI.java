package UI.DialogUI.SaveDialogs;


import Game.GameData;
import UI.CreationUI.ButtonType;
import UI.CreationUI.CustomButton;
import UI.DialogUI.BaseDialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;

public abstract class SaveBaseDialogUI extends BaseDialogUI {

    protected final GameData gameData;

    public SaveBaseDialogUI(String imgFile, String message, GameData gameData) throws InvalidUILoadException {
        super(imgFile, message);
        this.gameData = gameData;
        initializeButtons();
    }

    private void initializeButtons() throws InvalidUILoadException {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        add(Box.createVerticalStrut(20));

        CustomButton no = new CustomButton("/Sprites/ButtonSprites/NO_BUTTON.png", Important.calculateDimension(130), Important.calculateDimension(75), ButtonType.EXIT);
        CustomButton save = new CustomButton("/Sprites/ButtonSprites/YES_BUTTON.png", Important.calculateDimension(130), Important.calculateDimension(75), ButtonType.ENTER);

        no.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.hideDialog();
        });

        save.addActionListener(e -> {
            loadSaveButton();
        });
        panel.add(no);
        panel.add(Box.createHorizontalStrut(Important.calculateDimension(20)));
        panel.add(save);
        add(panel);
    }

    public abstract void loadSaveButton();
}
