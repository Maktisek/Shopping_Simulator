package Shops;


import Items.*;
import Items.Exceptions.WrongItemException;
import NPCs.NPC;
import Player.Player;
import Shops.Utilities.ShopKey;
import Upgrade.Rebirth.Rebirth;
import Upgrade.UpgradeManagement;

import java.io.Serializable;

/**
 * This class represents an individual shop, which is the absolute core of the whole game.
 * <p>
 *     Shop has those data in its possession:
 *     <ul>
 *         <li>{@link #name} stands for the name of the shop</li>
 *         <li>{@link #items} is an array of all items that the shop offers</li>
 *         <li>{@link #npc} represents shop's NPC. Every single shop has its own NPC, which sells same products as the shop offers</li>
 *         <li>{@link #shopKey} holds data about access to the shop (more information are written in its documentation)</li>
 *     </ul>
 * </p>
 * Still it is important to say that this class is more like a container of all above-mentioned data.
 * @author Matěj Pospíšil
 */
public class Shop implements Serializable {

    private String name;
    private ItemShop[] items;
    private NPC npc;
    private ShopKey shopKey;


    /**
     * This method stands for buying a product based on its index in {@link #items}.
     * <p>
     *     There is no special action needed, so only penalization and day amount are being incremented by the purchase.
     * </p>
     * The method is not wrong-index friendly. It may sound like a mistake, but when player buys an item it is firmly connected
     * to those indexes. Basically the creation of the UI is coming from this {@link #items} array. There is no chance of the
     * game choosing index out of bounds.
     * @param index is the index of the item that will be bought
     * @param amount stands for how many of them will be bought
     * @param rebirthCoefficient represents a coefficient that is useful in further process (the rebirth coefficient comes from {@link Rebirth#getPenalizationMultiplier()})
     */
    public void buyItem(int index, int amount, double rebirthCoefficient){
        items[index].updatePenalization(0.004 * amount, rebirthCoefficient);
        items[index].updateCurrentDayAmount(amount);
    }

    /**
     * This method starts a chain of reactions that initializes {@link #npc}.
     * @param player is needed further in the process
     * @param shop is needed further in the process
     * @throws WrongItemException if any of the methods in the chain has a problem
     */
    public void initializeNPC(Player player, Shop shop) throws WrongItemException {
        this.npc.loadItems(this.items);
        this.npc.loadDemand(player, shop);
        this.npc.setNewPrices(player, shop);
    }

    /**
     * This method starts a chain of reactions that sets a new day for {@link #npc} and for all {@link #items} via {@link #updateItems(double)}.
     * @param player is needed further in the process
     * @param rebirthCoefficient represents a coefficient that is useful in further process (the rebirth coefficient comes from {@link Rebirth#getPenalizationMultiplier()})
     * @throws WrongItemException if any of the methods in the chain has a problem
     */
    public void newDay(Player player, double rebirthCoefficient) throws WrongItemException{
        updateItems(rebirthCoefficient);
        updateNPC(player);
    }

    /**
     * This method updates data of all items in {@link #items}
     * @param rebirthCoefficient represents a coefficient that is useful in further process (the rebirth coefficient comes from {@link Rebirth#getPenalizationMultiplier()})
     */
    private void updateItems(double rebirthCoefficient){
        for (ItemShop item : items){
            item.newDayPenalization(0.02, rebirthCoefficient);
            item.getAmountManager().stockIn();
            try {
                item.updatePrice();
            }catch (WrongItemException e){
                System.err.println(e.getMessage());
            }
            item.resetCurrentDayAmount();
        }
    }

    /**
     * This method stands for updating {@link #npc}.
     * <p>
     *     It starts chain of methods and on the end it goes through all items in {@link #npc} possession and stocks them in.
     * </p>
     * @param player is needed further in the process
     * @throws WrongItemException if any of the methods in the chain has a problem
     */
    private void updateNPC(Player player) throws WrongItemException{
        this.npc.loadDemand(player,this);
        this.npc.setNewPrices(player, this);
        for (ItemNPC item : npc.getItems()){
            item.getAmountManager().stockIn();
        }
    }

    /**
     * This method finds an item based on its name in {@link #items}.
     * <p>
     *     It iterates through {@link #items} and when the name matches, it returns the instance of {@link ItemShop}.
     * </p>
     * @param name the name of the item to be found
     * @return the found instance of {@link ItemShop}
     */
    public ItemShop findItem(String name){
        for (ItemShop item : items){
            if(item.getItemBase().getName().equalsIgnoreCase(name)){
                return item;
            }
        }
        return null;
    }

    public ItemShop[] getItems() {
        return items;
    }

    public void setItems(ItemShop[] items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NPC getNpc() {
        return npc;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }

    public ShopKey getShopKey() {
        return shopKey;
    }

    public void setShopKey(ShopKey shopKey) {
        this.shopKey = shopKey;
    }
    
}
