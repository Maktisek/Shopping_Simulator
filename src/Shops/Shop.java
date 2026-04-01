package Shops;

import Items.Item;
import Items.ItemNames;
import Items.WrongItemException;
import NPCs.NPC;

public class Shop {

    private ShopNames name;
    private Item[] items;
    private NPC npc;


    public void initializeNPC() throws WrongItemException {
        this.npc.loadItems(this.items);
    }

    public void newDay(){

    }

    public Item findItem(ItemNames name){
        for (Item item : items){
            if(item.getName() == name){
                return item;
            }
        }
        return null;
    }


    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
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
}
