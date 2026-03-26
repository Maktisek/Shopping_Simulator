package NPCs;

import Items.Item;

import java.util.ArrayList;

public class NPC {

    private int quantityWeight;
    private int convenienceWeight;
    private ArrayList<Item> items;
    private Item[] demand;







    public int getQuantityWeight() {
        return quantityWeight;
    }

    public void setQuantityWeight(int quantityWeight) {
        this.quantityWeight = quantityWeight;
    }

    public Item[] getDemand() {
        return demand;
    }

    public void setDemand(Item[] demand) {
        this.demand = demand;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public int getConvenienceWeight() {
        return convenienceWeight;
    }

    public void setConvenienceWeight(int convenienceWeight) {
        this.convenienceWeight = convenienceWeight;
    }
}
