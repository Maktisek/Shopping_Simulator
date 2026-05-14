package UI.MainUI.ShopUI.Bounds;

import Game.GameData;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.UpdateAble;
import UI.Exceptions.InvalidUILoadException;
import UI.CreationUI.StrokeLabel;
import Upgrade.Utilities.UpgradeNames;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BoundPanelUI extends BackgroundPanel implements UpdateAble {

    private String current;
    private String bound;
    private ImageIcon img;
    private final GameData gameData;
    private StrokeLabel label;
    private final BoundTypes type;

    public BoundPanelUI(String imgFile, String iconFile, GameData gameData, BoundTypes type) throws InvalidUILoadException {
        super(imgFile);
        this.gameData = gameData;
        this.type = type;
        setBounds();

        loadIcon(iconFile);
        initialize();
    }

    private void setBounds(){
        switch (type){
            case BUY_BOUND -> {
                this.current = String.valueOf(gameData.getDayManagement().getCurrentDay().getDayBoughtAmount());
                this.bound = String.valueOf(gameData.getUpgradeManagement().getUpgradeData(UpgradeNames.BUY));
            }
            case SELL_BOUND -> {
                this.current = String.valueOf(gameData.getDayManagement().getCurrentDay().getDaySoldAmount());
                this.bound = String.valueOf(gameData.getUpgradeManagement().getUpgradeData(UpgradeNames.SELL));
            }
            case STOCK_BOUND -> {
                this.current = String.valueOf(gameData.getPlayer().calculateStocks());
                this.bound = String.valueOf(gameData.getUpgradeManagement().getUpgradeData(UpgradeNames.STOCK));
            }
        }
    }

    private void initialize() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        initializeDimensions();
        initializeIconLabel();
        initializeTextLabel();
    }

    private void initializeDimensions() {
        Dimension dimension = new Dimension(Important.calculateDimension(270), Important.calculateDimension(90));
        setMinimumSize(new Dimension(dimension));
        setPreferredSize(new Dimension(dimension));
        setMaximumSize(new Dimension(dimension));
    }

    private void initializeIconLabel() {
        add(Box.createHorizontalStrut(Important.calculateDimension(10)));
        JLabel label = new JLabel(img, JLabel.CENTER);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);
    }

    private void initializeTextLabel() {
        label = new StrokeLabel(current + "/" + bound, 24);

        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(label);
    }


    private void loadIcon(String fileName) throws InvalidUILoadException {
        URL imageURL = getClass().getResource(fileName);

        if (imageURL == null) {
            throw new InvalidUILoadException("The icon" + fileName + " could not be found");
        }
        ImageIcon icon = new ImageIcon(imageURL);
        Image scaledImage = icon.getImage().getScaledInstance(Important.calculateDimension(64), Important.calculateDimension(64), Image.SCALE_SMOOTH);
        icon.setImage(scaledImage);

        this.img = icon;
    }

    public void update(String current, String bound) {
        this.current = current;
        this.bound = bound;
        if(label != null){
            label.setText(this.current + "/" + this.bound);
        }
    }

    @Override
    public void update() throws InvalidUILoadException {
        setBounds();
        if(label != null){
            label.setText(this.current + "/" + this.bound);
        }
    }
}
