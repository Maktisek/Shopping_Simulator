package UI.MainUI.ShopUI;

import Game.GameData;
import UI.CreationUI.BackgroundPanel;
import UI.InvalidUILoadException;
import UI.MainUI.Utilities.StrokeLabel;
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
        Dimension dimension = new Dimension(250, 90);
        setMinimumSize(new Dimension(dimension));
        setPreferredSize(new Dimension(dimension));
        setMaximumSize(new Dimension(dimension));
    }

    private void initializeLabel(){
        day = new StrokeLabel(gameData.getDayManagement().getCurrentDay().getDayName().toString());
        Font font = Important.loadFont("/Fonts/Daydream.otf");
        day.setFont(font);
        day.setOpaque(false);
        day.setFont(font.deriveFont(Font.BOLD,24.0f));
        day.setForeground(Color.WHITE);

        day.setAlignmentX(Component.CENTER_ALIGNMENT);
        day.setAlignmentY(Component.TOP_ALIGNMENT);

        add(day, BorderLayout.CENTER);
    }

    public void update(){
        day.setText(gameData.getDayManagement().getCurrentDay().getDayName().toString());
    }

}
