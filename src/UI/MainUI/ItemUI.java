package UI.MainUI;

import Items.Item;
import Items.ItemShop;
import UI.BackgroundPanel;
import UI.InvalidUILoadException;

import java.awt.*;

public class ItemUI extends BackgroundPanel {

    private ItemShop item;

    public ItemUI(String imgFile, ItemShop item) throws InvalidUILoadException {
        super(imgFile);
        this.item = item;

        Dimension dimension = new Dimension(180, 180);
        setMinimumSize(new Dimension(dimension));
        setPreferredSize(new Dimension(dimension));
        setMaximumSize(new Dimension(dimension));
    }



}
