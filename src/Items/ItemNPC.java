package Items;

import UI.MainUI.ShopUI.ItemSpecification;
import Utilities.Important;

public class ItemNPC implements Item {

    private ItemBase item;
    private AmountManager amountManager;

    public ItemBase getItem() {
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

    @Override
    public String toString() {
        return "ItemNPC{" +
                "item=" + item +
                ", amountManager=" + amountManager +
                '}';
    }
}
