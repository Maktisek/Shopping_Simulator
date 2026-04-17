package UI.MainUI;

import Game.GameData;
import UI.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;

public class MainUI {

    private final JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private final GameData gameData;
    private ShopManagementUI shopManagementUI;

    public MainUI(GameData gameData) throws InvalidUILoadException {
        this.frame = new JFrame("Forest Market");
        this.frame.setLayout(new BorderLayout());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.gameData = gameData;

        initialize();
    }

    public void show() {
        this.frame.setVisible(true);
    }

    private void initialize() throws InvalidUILoadException{
        initializeMainPanel();
        initializeShopManagementUI();

        this.cardLayout.show(mainPanel, "Shop");
    }

    private void initializeMainPanel() {
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
        this.frame.add(mainPanel, BorderLayout.CENTER);
    }

    private void initializeShopManagementUI() throws InvalidUILoadException{
        this.shopManagementUI = new ShopManagementUI(this.gameData);
        this.mainPanel.add(shopManagementUI, "Shop");
    }
}
