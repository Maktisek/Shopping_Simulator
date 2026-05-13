package UI.MainUI.ShopUI.Days;

import Game.GameData;
import UI.CreationUI.BackgroundPanel;
import UI.Exceptions.InvalidUILoadException;
import UI.CreationUI.StrokeLabel;
import Utilities.Important;

import java.awt.*;

public class DayUI extends BackgroundPanel {

    private final GameData gameData;
    private StrokeLabel day;

    public DayUI(String imgFile, GameData gameData) throws InvalidUILoadException {
        super(imgFile);
        this.gameData = gameData;

        initialize();
    }

    private void initialize(){
        setLayout(new BorderLayout());
        setOpaque(false);
        initializeDimension();
        initializeLabel();
    }

    private void initializeDimension(){
        Dimension dimension = new Dimension(Important.calculateDimension(250), Important.calculateDimension(90));
        setMinimumSize(new Dimension(dimension));
        setPreferredSize(new Dimension(dimension));
        setMaximumSize(new Dimension(dimension));
    }

    private void initializeLabel(){
        day = new StrokeLabel(gameData.getDayManagement().getCurrentDay().getDayName().toString(), 22);

        day.setAlignmentX(Component.CENTER_ALIGNMENT);
        day.setAlignmentY(Component.TOP_ALIGNMENT);

        add(day, BorderLayout.CENTER);
    }

    public void update(){
        day.setText(gameData.getDayManagement().getCurrentDay().getDayName().toString());
    }

}
