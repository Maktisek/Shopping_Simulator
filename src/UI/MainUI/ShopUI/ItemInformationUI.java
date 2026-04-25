package UI.MainUI.ShopUI;

import Items.Item;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.CustomButton;
import UI.InvalidUILoadException;
import UI.MainUI.MainUI;
import UI.MainUI.Utilities.StrokeLabel;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ItemInformationUI extends BackgroundPanel {

    private final Item item;
    private final ItemSpecification spec;

    public ItemInformationUI(Item item, ItemSpecification spec) throws InvalidUILoadException {
        setImg("/MainUI/ShopUI/ITEM_INFO_PANE.png");
        this.item = item;
        this.spec = spec;
        initialization();
    }

    private void initialization() throws InvalidUILoadException {
        setLayout(new BorderLayout());
        initializeDimensions();
        initializeNorth();
        initializeCenter();
        initializeSouth();
    }

    private void initializeDimensions() {
        Dimension dimension = new Dimension(570, 720);
        setMinimumSize(new Dimension(dimension));
        setPreferredSize(new Dimension(dimension));
        setMaximumSize(new Dimension(dimension));
    }

    private void initializeNorth() throws InvalidUILoadException {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);

        setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        String name = this.item.getName().toString();
        StrokeLabel label = new StrokeLabel(name, 48.0f);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        wrapper.add(label);
        wrapper.add(Box.createVerticalStrut(20));

        StrokeLabel heading = new StrokeLabel("Description", 20.0f);
        wrapper.add(heading);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        wrapper.add(Box.createVerticalStrut(80));
        initializeImg(wrapper);

        add(wrapper, BorderLayout.NORTH);
    }

    private void initializeImg(JPanel panel) throws InvalidUILoadException {
        URL imageURL = getClass().getResource("/MainUI/ShopUI/Products/BANANA.png");

        if (imageURL == null) {
            throw new InvalidUILoadException(item.getName().toString() + " picture was not found.");
        }

        ImageIcon icon = new ImageIcon(imageURL);
        Image scaledImage = icon.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);

        JLabel image = new JLabel(new ImageIcon(scaledImage));

        image.setOpaque(false);
        image.setAlignmentX(Component.CENTER_ALIGNMENT);
        image.setAlignmentY(Component.TOP_ALIGNMENT);

        panel.add(image);
    }

    private void initializeCenter() {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        center.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));

        initializeDescriptionLabel(center);

        add(center, BorderLayout.CENTER);
    }

    private void initializeDescriptionLabel(JPanel panel) {
        String[] description = Important.decodeString(this.item.information(spec));
        for (String s : description) {
            StrokeLabel label = new StrokeLabel(Important.insertDots(s, 44), 14.0f);
            panel.add(label);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(Box.createVerticalStrut(20));
        }
    }

    private void initializeSouth() throws InvalidUILoadException {
        CustomButton customButton = new CustomButton("/MainUI/ShopUI/CLOSE_BUTTON.png", "/MainUI/ShopUI/CLOSE_BUTTON.png", 130, 75);
        customButton.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.hideDialog();

        });
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.add(customButton);
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        customButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(wrapper, BorderLayout.SOUTH);
    }

}
