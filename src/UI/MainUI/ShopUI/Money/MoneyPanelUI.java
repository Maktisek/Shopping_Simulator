package UI.MainUI.ShopUI.Money;

import Game.GameData;
import UI.CreationUI.BackgroundPanel;
import UI.Exceptions.InvalidUILoadException;
import UI.CreationUI.StrokeLabel;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

public class MoneyPanelUI extends BackgroundPanel {

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
        center.setLayout(new BorderLayout());
        center.setOpaque(false);

        this.price = new StrokeLabel(Important.parseMoney(gameData.getPlayer().getCurrentBalance()), 24.0f);
        add(price, BorderLayout.CENTER);
    }

    private void initializeDimensions() {
        Dimension dimension = new Dimension(240, 80);
        setMinimumSize(new Dimension(dimension));
        setPreferredSize(new Dimension(dimension));
        setMaximumSize(new Dimension(dimension));
    }

    public void update(){
        int balance = gameData.getPlayer().getCurrentBalance();
        this.price.setText(Important.parseMoney(balance)+" FR");
        updateColors(balance);
        this.price.repaint();
    }

    private void updateColors(int balance){
        if(balance < 40){
            this.price.setForeground(Color.RED);
            if (!timer.isRunning()){
                startTimer();
            }
        }else {
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
