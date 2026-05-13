package UI.MainUI.ShopUI.Upgrades;

import Commands.CommandResult;
import Commands.CommandState;
import Commands.UpgradeCommands.UpgradeCommand;
import Game.GameData;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.CustomButton;
import UI.Exceptions.InvalidUILoadException;
import UI.DialogUI.DialogUI;
import UI.MainUI.MainUI;
import UI.CreationUI.StrokeLabel;
import Upgrade.Upgrade;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

public class UpgradeUI extends BackgroundPanel {

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
        CustomButton buyButton = new CustomButton("/MainUI/ShopUI/" + upgrade.nameInfo().toString() + "_ICON.png", 80, 80);
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyButton.addActionListener(e ->{
            CommandResult result = new UpgradeCommand(gameData, this.upgrade.nameInfo()).execute();
            System.out.println(result.getMessage());
            if(result.getState() == CommandState.FAILED_ISSUE){
                MainUI parentShop = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
                try {
                    parentShop.showDialog(new DialogUI("/MainUI/ShopUI/ISSUE_PANE.png", result.getMessage()));
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
        this.price = new StrokeLabel(Important.parseMoney(upgrade.priceInfo()) + " FR", 13);
        this.price.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(price);
    }

    private void initializeDimensions() {
        Dimension dimension = new Dimension(Important.calculateDimension(180), Important.calculateDimension(180));
        setMinimumSize(new Dimension(dimension));
        setPreferredSize(new Dimension(dimension));
        setMaximumSize(new Dimension(dimension));
    }

    public void update(){
        this.price.setText(Important.parseMoney(upgrade.priceInfo()) + " FR");
        this.level.setText("LEVEL " + Important.parseMoney(upgrade.levelInfo()));
        this.level.repaint();
        this.price.repaint();
    }
}
