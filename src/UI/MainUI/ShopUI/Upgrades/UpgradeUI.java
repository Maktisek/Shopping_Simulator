package UI.MainUI.ShopUI.Upgrades;

import AudioSystem.AudioType;
import Commands.CommandResult;
import Commands.CommandState;
import Commands.UpgradeCommands.UpgradeCommand;
import Game.GameData;
import UI.CreationUI.Buttons.ButtonType;
import UI.CreationUI.Buttons.CustomButton;
import UI.CreationUI.Labels.StrokeLabel;
import UI.CreationUI.Panels.BackgroundPanel;
import UI.CreationUI.Utilities.UpdateAble;
import UI.DialogUI.DecisionDialogs.ContinueUpgradeDialogUI;
import UI.Exceptions.InvalidUILoadException;
import UI.DialogUI.BasicDialogs.DialogUI;
import UI.MainUI.MainUI;
import Upgrade.Upgrade;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

/**
 * This class is an implementation of {@link BackgroundPanel}.
 * <p>
 *     It visualizes individual {@link Upgrade} instances.
 * </p>
 * It displays:
 * <ul>
 *     <li>Name of the upgrade</li>
 *     <li>Icon of the upgrade (it is clickable)</li>
 *     <li>Level information</li>
 *     <li>Price of next upgrade</li>
 * </ul>
 * <p>
 *     Clicking the icon upgrades the upgrade!
 * </p>
 * I kinda like how I called it upgrade, and now I can say "upgrade an upgrade":)
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class UpgradeUI extends BackgroundPanel implements UpdateAble {

    private final Upgrade upgrade;
    private StrokeLabel price;
    private StrokeLabel level;
    private final GameData gameData;

    public UpgradeUI(String imgFile, Upgrade upgrade, GameData gameData) throws InvalidUILoadException {
        super(imgFile);
        this.upgrade = upgrade;
        this.gameData = gameData;
        initialize();
    }

    private void initialize() throws InvalidUILoadException {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(Important.calculateDimension(10), Important.calculateDimension(10) ,Important.calculateDimension(10) ,Important.calculateDimension(10)));
        initializeDimensions();
        initializeNameLabel();
        initializeImage();
        initializeLevelTextLabel();
        initializePriceLabel();
    }

    private void initializeNameLabel(){
        StrokeLabel name = new StrokeLabel(this.upgrade.nameInfo().toString(), 22);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(name);
        add(Box.createVerticalStrut(Important.calculateDimension(10)));
    }

    private void initializeImage() throws InvalidUILoadException {
        CustomButton buyButton = new CustomButton("/Sprites/IconSprites/" + upgrade.nameInfo().toString() + "_ICON.png", 80, 80, ButtonType.NONE);
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyButton.addActionListener(e ->{
            CommandResult result = new UpgradeCommand(gameData, this.upgrade.nameInfo(), true).execute();
            System.out.println(result.getMessage());
            if(result.getState() == CommandState.FAILED_ISSUE){
                MainUI parentShop = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
                try {
                    parentShop.showDialog(new DialogUI("/Sprites/UtilityPanels/ISSUE_PANE.png", result.getMessage(), "Error"));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
            }else if(result.getState() == CommandState.DONE){
                Important.getAudioManagement().playSound("NewUpgrade", AudioType.SOUNDS, 0, false);
            }else {
                MainUI parentShop = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
                try {
                    parentShop.showDialog(new ContinueUpgradeDialogUI(result.getMessage(), gameData, this.upgrade.nameInfo()));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        add(buyButton);
    }

    private void initializeLevelTextLabel(){
        add(Box.createVerticalStrut(4));
        this.level = new StrokeLabel("LEVEL " + Important.parseMoney(upgrade.levelInfo()), 14);
        this.level.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(this.level);
    }

    private void initializePriceLabel(){
        add(Box.createVerticalStrut(8));
        this.price = new StrokeLabel(Important.parseMoney(upgrade.priceInfo() * gameData.getAmount()) + " FR", 13);
        this.price.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(price);
    }

    private void initializeDimensions() {
        Dimension dimension = new Dimension(Important.calculateDimension(180), Important.calculateDimension(180));
        setMinimumSize(new Dimension(dimension));
        setPreferredSize(new Dimension(dimension));
        setMaximumSize(new Dimension(dimension));
    }

    @Override
    public void update(){
        this.price.setText(Important.parseMoney(upgrade.priceInfo() * gameData.getAmount()) + " FR");
        this.level.setText("LEVEL " + Important.parseMoney(upgrade.levelInfo()));
        this.level.repaint();
        this.price.repaint();
    }
}
