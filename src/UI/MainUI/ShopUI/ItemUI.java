package UI.MainUI.ShopUI;


import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Commands.ProductCommands.BuyProductCommand;
import Commands.ProductCommands.SellProductCommand;
import Game.GameData;
import Items.Item;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.CustomButton;
import UI.InvalidUILoadException;
import UI.MainUI.IssueUI.IssueFailDialogUI;
import UI.MainUI.MainUI;
import UI.MainUI.Utilities.StrokeLabel;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class ItemUI extends BackgroundPanel {

    private final Item item;
    private Font currentFont;
    private final GameData gameData;
    private final int index;
    private final ItemSpecification specification;
    private StrokeLabel price;
    private StrokeLabel name;

    public ItemUI(String imgFile, Item item, int index, GameData gameData, ItemSpecification specification) throws InvalidUILoadException {
        super(imgFile);
        this.item = item;
        this.index = index;
        this.gameData = gameData;
        this.specification = specification;

        Dimension dimension = new Dimension(180, 180);
        setMinimumSize(new Dimension(dimension));
        setPreferredSize(new Dimension(dimension));
        setMaximumSize(new Dimension(dimension));

        initialize();
    }

    private void initialize() throws InvalidUILoadException{
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        this.currentFont = Important.loadFont("/Fonts/Daydream.otf");

        initializeLabel();
        initializeImg();
        initializeCommandPoint();

    }

    private void initializeLabel(){
        name = new StrokeLabel(this.item.getName().toString());
        name.setFont(currentFont);
        name.setOpaque(false);
        name.setFont(this.currentFont.deriveFont(Font.BOLD,14.0f));
        name.setForeground(Color.WHITE);

        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        name.setAlignmentY(Component.TOP_ALIGNMENT);
//        label.setBorder(BorderFactory.createLineBorder(Color.RED)); //Debugging

        add(name);
        add(Box.createVerticalStrut(8));

    }

    private void initializeImg() throws InvalidUILoadException{
//        URL imageURL = getClass().getResource("/MainUI/ShopUI/Products"+item.getItem().getName().toString()+".png");
        URL imageURL = getClass().getResource("/MainUI/ShopUI/Products/BANANA.png");

        if(imageURL == null){
            throw new InvalidUILoadException(item.getName().toString() + " picture was not found.");
        }

        ImageIcon icon = new ImageIcon(imageURL);
        Image scaledImage = icon.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);

        JLabel image = new JLabel(new ImageIcon(scaledImage));

        image.setOpaque(false);
        image.setAlignmentX(Component.CENTER_ALIGNMENT);
        image.setAlignmentY(Component.TOP_ALIGNMENT);

//        image.setBorder(BorderFactory.createLineBorder(Color.BLUE)); //Debugging

        add(image);
    }

    private void initializeCommandPoint() throws InvalidUILoadException {
        JPanel commandPoint = new JPanel();
        commandPoint.setOpaque(false);
        commandPoint.setLayout(new BoxLayout(commandPoint, BoxLayout.Y_AXIS));
        commandPoint.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        initializeButton(commandPoint);
        initializePrice(commandPoint);

        add(commandPoint);
    }


    private void initializeButton(JPanel panel) throws InvalidUILoadException{
        CustomButton button = new CustomButton();
        Command command = null;
        switch (specification){
            case SHOP:{
                button = new CustomButton("/MainUI/ShopUI/BUY_BUTTON.png", "/MainUI/ShopUI/BUY_BUTTON.png", 100, 50);
                command = new BuyProductCommand(gameData, index, 1);
                break;
            }
            case NPC:{
                button = new CustomButton("/MainUI/ShopUI/SELL_BUTTON.png", "/MainUI/ShopUI/SELL_BUTTON.png", 100, 50);
                command = new SellProductCommand(gameData, index, 1);
                break;
            }
        }

        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        Command finalCommand = command;
        button.addActionListener(e ->{
            CommandResult result = finalCommand.execute();
            System.out.println(result.getMessage());
            if (Objects.requireNonNull(result.getState()) == CommandState.FAILED_ISSUE) {
                MainUI parentShop = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
                try {
                    parentShop.showShopDialog(new IssueFailDialogUI("/MainUI/ShopUI/ISSUE_PANE.png",result.getMessage()));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        panel.add(button);
    }

    private void initializePrice(JPanel panel){
        this.price = new StrokeLabel(Important.parseMoney(item.getCurrentPrice()) + " FR");
        this.price.setFont(currentFont);
        this.price.setOpaque(false);
        this.price.setFont(this.currentFont.deriveFont(Font.BOLD,13.0f));
        this.price.setForeground(Color.WHITE);

        this.price.setAlignmentX(Component.CENTER_ALIGNMENT);
//        label.setBorder(BorderFactory.createLineBorder(Color.RED)); //Debugging

        panel.add(this.price);
    }

    public void updateShop(){
        if(this.price != null){
            this.price.setText(item.getCurrentPrice() + " FR");
            updateShopColorPrice();
        }
    }

    public void updateNPC(String name, String price){
        this.name.setText(name);
        this.price.setText(price+ " FR");
        updateNpcColorPrice();
        this.name.repaint();
        this.price.repaint();
    }

    private void updateShopColorPrice() {
        if (item.getBasePrice() > item.getCurrentPrice()) {
            this.price.setForeground(Color.GREEN);
        } else if (item.getBasePrice() < item.getCurrentPrice()) {
            this.price.setForeground(Color.RED);
        }else {
            this.price.setForeground(Color.WHITE);
        }
    }

    private void updateNpcColorPrice(){
        int npcPrice = gameData.getShopManagement().getCurrentShop().getNpc().getDemand()[index].getCurrentPrice();
        double playerPrice = gameData.getPlayer().findItem(gameData.getShopManagement().getCurrentShop().getNpc().getDemand()[index].getName()).getAverageBuyPrice();
        if(playerPrice == 0){
            this.price.setForeground(Color.WHITE);
            return;
        }
        if (npcPrice > playerPrice) {
            this.price.setForeground(Color.GREEN);
        } else if (npcPrice < playerPrice) {
            this.price.setForeground(Color.RED);
        }else {
            this.price.setForeground(Color.WHITE);
        }
    }
}
