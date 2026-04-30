package UI.MainUI.StockUI;

import Items.ItemPlayer;
import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.CustomButton;
import UI.InvalidUILoadException;
import UI.MainUI.IssueUI.IssueFailDialogUI;
import UI.MainUI.MainUI;
import UI.MainUI.Utilities.StrokeLabel;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

public class ItemPlayerUI extends BackgroundPanel {

    private final ItemPlayer itemPlayer;
    private StrokeLabel amount;

    public ItemPlayerUI(String imgFile, ItemPlayer itemPlayer) throws InvalidUILoadException {
        super(imgFile);
        this.itemPlayer = itemPlayer;
        initialization();
    }

    private void initialization() throws InvalidUILoadException {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        labelInitialization(wrapper);
        initializeImageButton(wrapper);
        initializeAmount(wrapper);

        add(wrapper, BorderLayout.CENTER);
    }

    private void labelInitialization(JPanel panel) {
        StrokeLabel name = new StrokeLabel(itemPlayer.getName(), 22.0f);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(name);
    }

    private void initializeImageButton(JPanel panel) throws InvalidUILoadException {
        panel.add(Box.createVerticalStrut(20));
        CustomButton productButton = new CustomButton("/MainUI/ShopUI/Products/BANANA.png", "/MainUI/ShopUI/Products/BANANA.png", 140, 140);
        productButton.addActionListener(e -> {
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            try {
                parent.showDialog(new IssueFailDialogUI("/MainUI/ShopUI/ISSUE_PANE.png", "Coming soon"));
            } catch (InvalidUILoadException ex) {
                throw new RuntimeException(ex);
            }
        });
        productButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(productButton);
    }

    private void initializeAmount(JPanel panel){
        panel.add(Box.createVerticalStrut(20));
        this.amount = new StrokeLabel(Important.parseMoney(itemPlayer.getAmount()) + "X", 24.0f);
        this.amount.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(this.amount);
    }

    public void update(){
        this.amount.setText(Important.parseMoney(itemPlayer.getAmount()) + "X");
        this.amount.repaint();
    }
}
