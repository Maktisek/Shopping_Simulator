package UI.MainUI.StockUI;

import Game.GameData;
import Items.ItemPlayer;
import UI.CreationUI.Buttons.ButtonType;
import UI.CreationUI.Buttons.CustomButton;
import UI.CreationUI.Labels.StrokeLabel;
import UI.CreationUI.Panels.BackgroundPanel;
import UI.CreationUI.Utilities.UpdateAble;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import UI.MainUI.ShopUI.Items.ItemInformationUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

public class ItemPlayerUI extends BackgroundPanel implements UpdateAble {

    private final GameData gameData;
    private final ItemPlayer itemPlayer;
    private StrokeLabel amount;

    public ItemPlayerUI(String imgFile, ItemPlayer itemPlayer, GameData gameData) throws InvalidUILoadException {
        super(imgFile);
        this.itemPlayer = itemPlayer;
        this.gameData = gameData;
        initialization();
    }

    private void initialization() throws InvalidUILoadException {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(Important.calculateDimension(10), Important.calculateDimension(10), Important.calculateDimension(10), Important.calculateDimension(10)));

        labelInitialization(wrapper);
        initializeImageButton(wrapper);
        initializeAmount(wrapper);

        add(wrapper, BorderLayout.CENTER);
    }

    private void labelInitialization(JPanel panel) {
        StrokeLabel name = new StrokeLabel(itemPlayer.getName(), 22);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(name);
    }

    private void initializeImageButton(JPanel panel) throws InvalidUILoadException {
        panel.add(Box.createVerticalStrut(20));
        CustomButton productButton = new CustomButton("/Sprites/Products/" + itemPlayer.getName() + ".png", 140, 140, ButtonType.ENTER);
        productButton.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            try {
                parent.showDialog(new ItemInformationUI(this.itemPlayer.getName(), this.itemPlayer.description(gameData)));
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
        });
        productButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(productButton);
    }

    private void initializeAmount(JPanel panel){
        panel.add(Box.createVerticalStrut(Important.calculateDimension(20)));
        this.amount = new StrokeLabel(Important.parseMoney(itemPlayer.getAmount()) + "X", 24);
        this.amount.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(this.amount);
    }

    @Override
    public void update(){
        this.amount.setText(Important.parseMoney(itemPlayer.getAmount()) + "X");
        this.amount.repaint();
    }
}
