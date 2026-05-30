package UI.MainUI.ShopUI.Money;

import Game.GameData;
import UI.CreationUI.Panels.BackgroundPanel;
import UI.CreationUI.Utilities.UpdateAble;
import UI.Exceptions.InvalidUILoadException;
import UI.CreationUI.Labels.StrokeLabel;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

/**
 * This class is an implementation of {@link BackgroundPanel}.
 * <p>
 *     It visualizes players current balance, and it can be found in the main panel in the left corner.
 * </p>
 * <p>
 *     {@link #price} stands for the current balance, and it has to be updated through update method,
 *     that is why {@link UpdateAble} is implemented.
 * </p>
 * If players money are negative {@link #price} turns into red color and it flashes.
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class MoneyPanelUI extends BackgroundPanel implements UpdateAble {

    private final GameData gameData;
    private StrokeLabel price;
    private Timer timer;

    public MoneyPanelUI(String imgFile, GameData gameData) throws InvalidUILoadException {
        super(imgFile);
        this.gameData = gameData;
        initialize();
    }

    private void initialize(){
        setLayout(new BorderLayout());
        initializeDimensions();
        initializeTimer();

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
        center.setOpaque(false);

        this.price = new StrokeLabel(Important.parseMoney(gameData.getPlayer().getCurrentBalance()), 22);
        add(price, BorderLayout.CENTER);
    }

    private void initializeDimensions() {
        Dimension dimension = new Dimension(Important.calculateDimension(240), Important.calculateDimension(80));
        setMinimumSize(new Dimension(dimension));
        setPreferredSize(new Dimension(dimension));
        setMaximumSize(new Dimension(dimension));
    }

    @Override
    public void update(){
        long balance = gameData.getPlayer().getCurrentBalance();
        updateColors(balance);
        this.price.repaint();
    }

    private void updateColors(long balance){
        if(balance < 0){
            this.price.setText("-"+Important.parseMoney(balance)+" FR");
            this.price.setForeground(Color.RED);
            if (!timer.isRunning()){
                startTimer();
            }
        }else {
            this.price.setText(Important.parseMoney(balance)+" FR");
            if(timer.isRunning()) {
                stopTimer();
            }
            this.price.setForeground(Color.WHITE);

        }
    }

    private void initializeTimer() {
        this.timer = new Timer(500, e -> {
            this.price.setVisibility(!this.price.isVisibility());
        });
    }

    private void startTimer(){
        this.timer.start();
        }

    private void stopTimer(){
        this.timer.stop();
        this.price.setVisibility(true);
    }
}
