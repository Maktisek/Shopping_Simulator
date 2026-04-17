package UI.MainUI;

import Items.Item;
import Items.ItemShop;
import UI.BackgroundPanel;
import UI.InvalidUILoadException;

public class ItemUI extends BackgroundPanel {

    private ItemShop item;

    public ItemUI(String imgFile, ItemShop item) throws InvalidUILoadException {
        super(imgFile);
        this.item = item;
    }



}
