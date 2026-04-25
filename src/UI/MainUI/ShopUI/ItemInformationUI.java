package UI.MainUI.ShopUI;

import Items.Item;
import UI.CreationUI.BackgroundPanel;
import UI.InvalidUILoadException;
import UI.MainUI.Utilities.StrokeLabel;
import Utilities.Important;

import java.awt.*;

public class ItemInformationUI extends BackgroundPanel {

    private Item item;

    public ItemInformationUI(String imgFile, Item item) throws InvalidUILoadException {
        super(imgFile);
        this.item = item;
        initialization();
    }

    private void initialization(){
        setLayout(new BorderLayout());
        initializeLabel();
    }

    private void initializeLabel(){
        String name = this.item.getName().toString();
        StrokeLabel label = new StrokeLabel(name, 24.0f);
        label.setForeground(Color.WHITE);

        add(label, BorderLayout.NORTH);
    }
}
