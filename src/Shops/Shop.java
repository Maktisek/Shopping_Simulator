package Shops;


import Items.*;
import Items.Exceptions.WrongItemException;
import NPCs.NPC;
import Player.Player;
import Shops.Utilities.ShopKey;

import java.io.Serializable;

public class Shop implements Serializable {

    private String name;
    private ItemShop[] items;
    private NPC npc;
    private ShopKey shopKey;


    public void buyItem(int index, int amount, double rebirthCoefficient){
        items[index].updatePenalization(0.004 * amount, rebirthCoefficient);
        items[index].updateCurrentDayAmount(amount);
    }

    public void initializeNPC(Player player, Shop shop) throws WrongItemException {
        this.npc.loadItems(this.items);
        this.npc.loadDemand(player, shop);
        this.npc.setNewPrices(player, shop);
    }

    public void newDay(Player player, double rebirthCoefficient) throws WrongItemException{
        updateItems(rebirthCoefficient);
        updateNPC(player);
    }

    private void updateItems(double rebirthCoefficient){
        for (ItemShop item : items){
            item.newDayPenalization(-0.02, rebirthCoefficient);
            item.getAmountManager().stockIn();
            try {
                item.updatePrice();
            }catch (WrongItemException e){
                e.printStackTrace();
            }
            item.resetCurrentDayAmount();
        }
    }

    private void updateNPC(Player player) throws WrongItemException{
        this.npc.loadDemand(player,this);
        this.npc.setNewPrices(player, this);
        for (ItemNPC item : npc.getItems()){
            item.getAmountManager().stockIn();
        }
    }

    public ItemShop findItem(java.lang.String name){
        for (ItemShop item : items){
            if(item.getItemBase().getName().equalsIgnoreCase(name)){
                return item;
            }
        }
        return null;
    }

    public boolean isAccessible(){
        return this.shopKey.isUnlocked();
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
