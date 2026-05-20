package UI.MainUI.ShopUI.ShopManagement;

import Game.GameData;
import UI.CreationUI.ButtonType;
import UI.CreationUI.CustomButton;
import UI.CreationUI.MultiplierButton;
import UI.CreationUI.UpdateAble;
import UI.DialogUI.NewRebirthDialog;
import UI.DialogUI.SaveDialogs.SaveAndQuitDialogUI;
import UI.DialogUI.SaveDialogs.SaveDialogUI;
import UI.DialogUI.TurnOffTheGameDialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import UI.MainUI.ShopUI.Money.MoneyPanelUI;
import Utilities.Important;

import javax.swing.*;
import java.util.ArrayList;

public class ShopManagementNorthUI extends JPanel implements UpdateAble {

    private MoneyPanelUI moneyPanelUI;
    private final ArrayList<MultiplierButton> multiplierButtons;
    private final GameData gameData;

    public ShopManagementNorthUI(GameData gameData) throws InvalidUILoadException {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(0, Important.calculateDimension(10), Important.calculateDimension(10), Important.calculateDimension(10)));

        this.multiplierButtons = new ArrayList<>();
        this.gameData = gameData;

        initialization();
    }

    private void initialization() throws InvalidUILoadException {
        initializeBalance();
        initializeMultipliers();
        initializeQuitButton();
        initializeSaveButton();
        initializeQuitAndSaveButton();
        initializeRebirthButton();
    }

    private void initializeBalance() throws InvalidUILoadException {
        this.moneyPanelUI = new MoneyPanelUI("/Sprites/UtilityPanels/MONEY_PANEL.png", gameData);
        add(moneyPanelUI);
    }

    private void initializeMultipliers() throws InvalidUILoadException {
        multiplierButtons.add(new MultiplierButton(100, 100, 1, gameData, multiplierButtons));
        multiplierButtons.add(new MultiplierButton(100, 100, 5, gameData, multiplierButtons));
        multiplierButtons.add(new MultiplierButton(100, 100, 10, gameData, multiplierButtons));

        for (MultiplierButton multiplierButton : multiplierButtons) {
            add(multiplierButton);
            add(Box.createHorizontalStrut(Important.calculateDimension(10)));
        }
        multiplierButtons.get(0).doClick();
    }

    private void initializeSaveButton() throws InvalidUILoadException {
        CustomButton save = new CustomButton("/Sprites/ButtonSprites/SAVE_BUTTON.png", 100, 100, ButtonType.ENTER);
        save.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            try {
                parent.showDialog(new SaveDialogUI("/Sprites/UtilityPanels/ISSUE_PANE.png", "Do you wish to save the game", gameData));
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
        });

        add(Box.createHorizontalStrut(Important.calculateDimension(10)));
        add(save);
    }

    private void initializeQuitAndSaveButton() throws InvalidUILoadException {
        CustomButton saveAndQuit = new CustomButton("/Sprites/ButtonSprites/SAVE_QUIT_BUTTON.png", 100, 100, ButtonType.ENTER);
        saveAndQuit.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            try {
                parent.showDialog(new SaveAndQuitDialogUI("/Sprites/UtilityPanels/ISSUE_PANE.png", "Do you wish to save the game", gameData));
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
        });

        add(Box.createHorizontalStrut(Important.calculateDimension(10)));
        add(saveAndQuit);
    }

    private void initializeQuitButton() throws InvalidUILoadException {
        CustomButton quit = new CustomButton("/Sprites/ButtonSprites/QUIT_GAME_BUTTON.png", 100, 100, ButtonType.ENTER);
        quit.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            try {
                parent.showDialog(new TurnOffTheGameDialogUI("/Sprites/UtilityPanels/ISSUE_PANE.png", "Do you want to turn off the game without saving?", parent));
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
        });
        add(Box.createHorizontalStrut(Important.calculateDimension(10)));
        add(quit);
    }

    private void initializeRebirthButton() throws InvalidUILoadException {
        CustomButton rebirthButton = new CustomButton("/Sprites/ButtonSprites/REBIRTH_BUTTON.png", 100, 100, ButtonType.ENTER);
        rebirthButton.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            try {
                parent.showDialog(new NewRebirthDialog("Do you want to buy new rebirth for " + Important.parseMoney(gameData.getUpgradeManagement().getRebirth().getPrice()) + " FR?", gameData));
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
        });

        add(Box.createHorizontalStrut(Important.calculateDimension(10)));
        add(rebirthButton);
    }

    @Override
    public void update() throws InvalidUILoadException {
        this.moneyPanelUI.update();
    }
}
