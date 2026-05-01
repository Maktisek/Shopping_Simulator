package UI.MainUI.ShopUI.Money;

import Game.GameData;
import UI.CreationUI.BackgroundPanel;
import UI.Exceptions.InvalidUILoadException;
import UI.CreationUI.StrokeLabel;
import Utilities.Important;

import java.awt.*;

public class MoneyPanelUI extends BackgroundPanel {

    private final GameData gameData;
    private StrokeLabel price;

    public MoneyPanelUI(String imgFile, GameData gameData) throws InvalidUILoadException {
        super(imgFile);
        this.gameData = gameData;
        initialize();
    }

    private void initialize(){
        setLayout(new BorderLayout());
        initializeDimensions();
        this.price = new StrokeLabel(Important.parseMoney(gameData.getPlayer().getCurrentBalance())+" FR", 24.0f);
        add(price, BorderLayout.CENTER);
    }

    private void initializeDimensions() {
        Dimension dimension = new Dimension(240, 80);
        setMinimumSize(new Dimension(dimension));
        setPreferredSize(new Dimension(dimension));
        setMaximumSize(new Dimension(dimension));
    }

    public void update(){
        this.price.setText(Important.parseMoney(gameData.getPlayer().getCurrentBalance())+" FR");
        this.price.repaint();
    }
}
