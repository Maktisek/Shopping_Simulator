package UI.MainUI.ShopUI.Items;

import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.CustomButton;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import UI.CreationUI.StrokeLabel;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ItemInformationUI extends BackgroundPanel {

    private final String name;
    private final String specification;

    public ItemInformationUI(String name, String specification) throws InvalidUILoadException {
        setImg("/ShopSprites/INFO_PANE.png");
        this.name = name;
        this.specification = specification;
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
        Dimension dimension = new Dimension(Important.calculateDimension(570), Important.calculateDimension(720));
        setMinimumSize(new Dimension(dimension));
        setPreferredSize(new Dimension(dimension));
        setMaximumSize(new Dimension(dimension));
    }

    private void initializeNorth() throws InvalidUILoadException {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);

        setBorder(BorderFactory.createEmptyBorder(Important.calculateDimension(20), 0, 0, 0));


        StrokeLabel label = new StrokeLabel(this.name, 48);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        wrapper.add(label);
        wrapper.add(Box.createVerticalStrut(Important.calculateDimension(20)));

        StrokeLabel heading = new StrokeLabel("Description", 20);
        wrapper.add(heading);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        wrapper.add(Box.createVerticalStrut(Important.calculateDimension(80)));
        initializeImg(wrapper);

        add(wrapper, BorderLayout.NORTH);
    }

    private void initializeImg(JPanel panel) throws InvalidUILoadException {
        URL imageURL = getClass().getResource("/Products/" + this.name + ".png");

        if (imageURL == null) {
            throw new InvalidUILoadException(this.name + " picture was not found.");
        }

        ImageIcon icon = new ImageIcon(imageURL);
        Image scaledImage = icon.getImage().getScaledInstance(Important.calculateDimension(160), Important.calculateDimension(160), Image.SCALE_DEFAULT);

        JLabel image = new JLabel(new ImageIcon(scaledImage));

        image.setOpaque(false);
        image.setAlignmentX(Component.CENTER_ALIGNMENT);
        image.setAlignmentY(Component.TOP_ALIGNMENT);

        panel.add(image);
    }

    private void initializeCenter() throws InvalidUILoadException {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        center.setBorder(BorderFactory.createEmptyBorder(0, Important.calculateDimension(10), 0, Important.calculateDimension(10)));

        initializeDescriptionLabel(center);

        JScrollPane scrollPane = Important.initializeScrollPane(center, 4);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(Important.calculateDimension(10), 0));

        add(scrollPane, BorderLayout.CENTER);
    }

    private void initializeDescriptionLabel(JPanel panel) {
        String[] description = Important.decodeString(specification);
        for (String s : description) {
            panel.add(Box.createVerticalStrut(Important.calculateDimension(20)));
            StrokeLabel label = new StrokeLabel(Important.insertDots(s, 42), 14);
            panel.add(label);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        panel.add(Box.createVerticalStrut(Important.calculateDimension(5)));
    }

    private void initializeSouth() throws InvalidUILoadException {
        CustomButton customButton = new CustomButton("/ShopSprites/CLOSE_BUTTON.png", 130, 75);
        customButton.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.hideDialog();

        });
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.add(customButton);
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, Important.calculateDimension(30), 0));
        customButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(wrapper, BorderLayout.SOUTH);
    }

}
