package Items;

import Items.Utilities.AmountManager;
import UI.MainUI.ShopUI.Items.ItemSpecification;
import Utilities.Important;

import java.io.Serializable;

public class ItemNPC implements Item, Serializable {

    private ItemBase item;
    private AmountManager amountManager;

    public ItemBase getItemBase() {
        return item;
    }

    @Override
    public String specification() {
        return this.item.information(ItemSpecification.NPC) + "\n" +"Demand:" + Important.parseMoney(amountManager.getCurrent());
    }

    public void setItem(ItemBase item) {
        this.item = item;
    }

    public AmountManager getAmountManager() {
        return amountManager;
    }

    public void setAmountManager(AmountManager amountManager) {
        this.amountManager = amountManager;
    }

}
