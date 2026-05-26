package Items;

import Items.Utilities.AmountManager;
import UI.MainUI.ShopUI.Items.ItemSpecification;
import Utilities.Important;

import java.io.Serializable;

/**
 * This class stands for an item stored in an instance of {@link NPCs.NPC}.
 * <p>
 *     It is a simple class featuring:
 *     <ul>
 *         <li>{@link #item} as the main product</li>
 *         <li>{@link #amountManager} as the amount counter</li>
 *     </ul>
 * </p>
 * Here it is clearly visible, why the system with {@link ItemBase} works so well
 * @author Matěj Pospíšil
 */
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
