package UI.MainUI.ShopUI.Items;


import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Commands.ProductCommands.BuyProductCommand;
import Commands.ProductCommands.SellProductCommand;
import Game.GameData;
import Items.Item;
import UI.CreationUI.*;
import UI.Exceptions.InvalidUILoadException;
import UI.DialogUI.DialogUI;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ItemUI extends BackgroundPanel implements UpdateAble {

    private Item item;
    private final GameData gameData;
    private final int index;
    private final ItemSpecification specification;
    private StrokeLabel price;
    private StrokeLabel name;
    private CustomButton image;

    public ItemUI(String imgFile, Item item, int index, GameData gameData, ItemSpecification specification) throws InvalidUILoadException {
        super(imgFile);
        this.index = index;
        this.gameData = gameData;
        this.specification = specification;
        this.item = item;

        Dimension dimension = new Dimension(Important.calculateDimension(180), Important.calculateDimension(180));
        setMinimumSize(new Dimension(dimension));
        setPreferredSize(new Dimension(dimension));
        setMaximumSize(new Dimension(dimension));

        initialize();
    }

    private void initialize() throws InvalidUILoadException {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(Important.calculateDimension(15), 0, 0, 0));

        initializeLabel();
        initializeImg();
        initializeCommandPoint();

    }

    private void initializeLabel() {
        name = new StrokeLabel(this.item.getItem().getName(), 14);

        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        name.setAlignmentY(Component.TOP_ALIGNMENT);

        add(name);
        add(Box.createVerticalStrut(Important.calculateDimension(8)));

    }

    private void initializeImg() throws InvalidUILoadException {
        this.image = initializeCustomImageButton();
        add(image);
    }

    private CustomButton initializeCustomImageButton() throws InvalidUILoadException {
        CustomButton image = new CustomButton("/Sprites/Products/" + item.getItem().getName() + ".png", 80, 80, ButtonType.ENTER);

        image.setOpaque(false);
        image.setAlignmentX(Component.CENTER_ALIGNMENT);
        image.setAlignmentY(Component.TOP_ALIGNMENT);

        image.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            try {
                parent.showDialog(new ItemInformationUI(this.item.getItem().getName(), this.item.specification()));
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
        });
        return image;
    }

    private void initializeCommandPoint() throws InvalidUILoadException {
        JPanel commandPoint = new JPanel();
        commandPoint.setOpaque(false);
        commandPoint.setLayout(new BoxLayout(commandPoint, BoxLayout.Y_AXIS));
        commandPoint.setBorder(BorderFactory.createEmptyBorder(0, Important.calculateDimension(10), 0, Important.calculateDimension(10)));

        initializeButton(commandPoint);
        initializePrice(commandPoint);

        add(commandPoint);
    }


    private void initializeButton(JPanel panel) throws InvalidUILoadException {
        CustomButton button = new CustomButton();
        Command command = null;
        String name = "";
        switch (specification) {
            case SHOP: {
                button = new CustomButton("/Sprites/ButtonSprites/BUY_BUTTON.png", 100, 50, ButtonType.NONE);
                name = "Buy";
                command = new BuyProductCommand(gameData, index);
                break;
            }
            case NPC: {
                button = new CustomButton("/Sprites/ButtonSprites/SELL_BUTTON.png", 100, 50, ButtonType.NONE);
                name = "Sell";
                command = new SellProductCommand(gameData, index);
                break;
            }
        }

        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        Command finalCommand = command;
        String finalName = name;
        CustomButton finalButton = button;
        button.addActionListener(e -> {
            CommandResult result = finalCommand.execute();
            System.out.println(result.getMessage());
            if (Objects.requireNonNull(result.getState()) == CommandState.FAILED_ISSUE) {
                MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
                try {
                    parent.showDialog(new DialogUI("/Sprites/UtilityPanels/ISSUE_PANE.png", result.getMessage(), "Error"));
                } catch (InvalidUILoadException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                finalButton.addAdditionalSound(finalName);
            }
        });
        panel.add(button);
    }

    private void initializePrice(JPanel panel) {
        this.price = new StrokeLabel(Important.parseMoney(item.getItem().getCurrentPrice()) + " FR", 13);
        this.price.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(this.price);
    }

    private void updateShop() {
        if (this.price != null) {
            this.price.setText(Important.parseMoney(item.getItem().getCurrentPrice()) + " FR");
            updateShopColorPrice();
        }
    }

    private void updateNPC() throws InvalidUILoadException {
        this.name.setText(item.getItem().getName());
        updateImage();
        this.price.setText(item.getItem().getCurrentPrice() + " FR");
        this.name.repaint();
        this.price.repaint();
    }

    private void updateImage() throws InvalidUILoadException {
        this.image.setImgFile("/Sprites/Products/" + item.getItem().getName() + ".png");
        this.image.setImages();
        this.image.repaint();
    }

    private void updateShopColorPrice() {
        if (item.getItem().getCurrentPrice() * gameData.getAmount() <= gameData.getPlayer().getCurrentBalance()) {
            this.price.setForeground(Color.GREEN);
        } else {
            this.price.setForeground(Color.RED);
        }
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public void update() throws InvalidUILoadException {
        switch (specification) {
            case SHOP -> updateShop();
            case NPC -> updateNPC();
        }
    }
}
