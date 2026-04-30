package UI.MainUI.StockUI;

import Items.ItemPlayer;
import UI.CreationUI.BackgroundPanel;
import UI.InvalidUILoadException;

public class ItemPlayerUI extends BackgroundPanel {

    private ItemPlayer itemPlayer;

    public ItemPlayerUI(String imgFile, ItemPlayer itemPlayer) throws InvalidUILoadException {
        super(imgFile);
        this.itemPlayer = itemPlayer;
    }
}
