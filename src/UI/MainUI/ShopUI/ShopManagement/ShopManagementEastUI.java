package UI.MainUI.ShopUI.ShopManagement;

import Game.GameData;
import UI.CreationUI.Buttons.ButtonType;
import UI.CreationUI.Buttons.CustomButton;
import UI.CreationUI.Utilities.UpdateAble;
import UI.DialogUI.DecisionDialogs.NewDayDialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import UI.MainUI.ShopUI.Days.DayUI;
import UI.MainUI.ShopUI.Upgrades.UpgradeUI;
import Upgrade.Utilities.UpgradeType;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShopManagementEastUI extends JPanel implements UpdateAble {

    private final GameData gameData;
    private DayUI dayUI;
    private final ArrayList<UpgradeUI> upgrades;

    public ShopManagementEastUI(GameData gameData) throws InvalidUILoadException {
        this.gameData = gameData;
        this.upgrades = new ArrayList<>();

        initialization();
    }

    private void initialization() throws InvalidUILoadException {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(Important.calculateDimension(18), 0, 0, Important.calculateDimension(28)));

        initializeDay();
        initializeNewDayButton();
        initializeUpgrades();
    }

    private void initializeDay() throws InvalidUILoadException {
        this.dayUI = new DayUI("/Sprites/UtilityPanels/DAY_PANE.png", gameData);
        dayUI.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(dayUI);
    }

    private void initializeNewDayButton() throws InvalidUILoadException {
        CustomButton nextDay = new CustomButton("/Sprites/ButtonSprites/NEXT_DAY_BUTTON.png", 200, 85, ButtonType.ENTER);
        nextDay.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createVerticalStrut(Important.calculateDimension(7)));
        this.add(nextDay);

        nextDay.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            try {
                parent.showDialog(new NewDayDialogUI("The tax is  " + Important.parseMoney(gameData.getTax().getCurrent())  + "FR, do you want to proceed into another day?", gameData));
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void initializeUpgrades() throws InvalidUILoadException {
        this.add(Box.createVerticalStrut(Important.calculateDimension(15)));
        for (UpgradeType upgrade : UpgradeType.values()) {
            UpgradeUI upgradeUI = new UpgradeUI("/Sprites/UtilityPanels/ITEM_FRAME.png", gameData.getUpgradeManagement().getUpgrades().get(upgrade), gameData);
            upgradeUI.setOpaque(false);
            upgradeUI.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.add(upgradeUI);
            this.add(Box.createVerticalStrut(Important.calculateDimension(20)));
            upgrades.add(upgradeUI);
        }
    }

    @Override
    public void update() throws InvalidUILoadException {
        this.dayUI.update();
        for (UpgradeUI upgradeUI : upgrades) {
            upgradeUI.update();
        }
    }
}
