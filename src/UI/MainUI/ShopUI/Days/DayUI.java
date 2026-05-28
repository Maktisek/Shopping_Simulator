package UI.MainUI.ShopUI.Days;

import Game.GameData;
import UI.CreationUI.Panels.BackgroundPanel;
import UI.CreationUI.Utilities.UpdateAble;
import UI.Exceptions.InvalidUILoadException;
import UI.CreationUI.Labels.StrokeLabel;
import Utilities.Important;

import java.awt.*;

/**
 * This class is an implementation of {@link BackgroundPanel}.
 * <p>
 *     This class visualizes an individual day by displaying its name.
 * </p>
 * It can be found in the right top corner in the panel where the shop is located.
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class DayUI extends BackgroundPanel implements UpdateAble {

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

    @Override
    public void update(){
        day.setText(gameData.getDayManagement().getCurrentDay().getDayName().toString());
    }

}
