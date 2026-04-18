package UI.MainUI;


import Commands.ProductCommands.BuyProductCommand;
import Game.GameData;
import Items.ItemShop;
import UI.BackgroundPanel;
import UI.CustomButton;
import UI.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ItemUI extends BackgroundPanel {

    private final ItemShop item;
    private Font currentFont;
    private GameData gameData;
    private int index;

    public ItemUI(String imgFile, ItemShop item, int index, GameData gameData) throws InvalidUILoadException {
        super(imgFile);
        this.item = item;
        this.index = index;
        this.gameData = gameData;

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
        loadFont();
        initializeLabel();
        initializeImg();
        initializeBuyButton();
    }

    private void initializeLabel(){
        StrokeLabel label = new StrokeLabel(this.item.getItem().getName().toString());
        label.setFont(currentFont);
        label.setOpaque(false);
        label.setFont(this.currentFont.deriveFont(Font.BOLD,14.0f));
        label.setForeground(Color.WHITE);

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.TOP_ALIGNMENT);
//        label.setBorder(BorderFactory.createLineBorder(Color.RED)); //Debugging

        add(label);
        add(Box.createVerticalStrut(8));

    }

    private void initializeImg() throws InvalidUILoadException{
//        URL imageURL = getClass().getResource("/MainUI/ShopUI/Products"+item.getItem().getName().toString()+".png");
        URL imageURL = getClass().getResource("/MainUI/ShopUI/Products/BANANA.png");

        if(imageURL == null){
            throw new InvalidUILoadException(item.getItem().getName().toString() + " picture was not found.");
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

    private void initializeBuyButton() throws InvalidUILoadException{
        CustomButton button = new CustomButton("/MainUI/ShopUI/BUY_BUTTON.png", "/MainUI/ShopUI/BUY_BUTTON.png", 100, 50);

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setAlignmentY(Component.TOP_ALIGNMENT);

        button.addActionListener(e ->{
            System.out.println(new BuyProductCommand(gameData, index, 1).execute());
            System.out.println(gameData);
        });

        add(button);
    }


    private void loadFont(){
        try {
            InputStream in = getClass().getResourceAsStream("/Fonts/Daydream.otf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, in);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            this.currentFont = customFont;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

}
