package Shops;


import Items.ItemNames;
import Items.ItemShop;
import Items.WrongItemException;
import NPCs.NPC;
import Player.Player;

import java.util.Arrays;

public class Shop {

    private ShopNames name;
    private ItemShop[] items;
    private NPC npc;
    private ShopKey shopKey;


    public void buyItem(int index, int amount){
        items[index].updatePenalization(0.03 * amount);
        items[index].updateCurrentDayAmount(amount);
    }

    public void initializeNPC(Player player, Shop shop) throws WrongItemException {
        this.npc.loadItems(this.items);
        this.npc.loadDemand(player, shop);
        this.npc.setNewPrices(player, shop);
    }

    public void newDay(Player player) throws WrongItemException{
        updateItems();
        updateNPC(player);
    }

    private void updateItems(){
        for (ItemShop item : items){
            item.newDayPenalization(-0.06);
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
    }

    public ItemShop findItem(ItemNames name){
        for (ItemShop item : items){
            if(item.getItem().getName() == name){
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

    public ShopNames getName() {
        return name;
    }

    public void setName(ShopNames name) {
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

    @Override
    public String toString() {
        return "Shop{" +
                "name=" + name +
                ", items=" + Arrays.toString(items) +
                ", npc=" + npc +
                ", shopKey=" + shopKey +
                '}';
    }
}
