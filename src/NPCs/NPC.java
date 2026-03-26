package NPCs;

import Items.Item;
import Items.Shop;
import Player.Player;

import java.util.ArrayList;

public class NPC {

    private int quantityWeight;
    private int convenienceWeight;
    private ArrayList<Item> items;
    private Item[] demand;

    public void loadDemand(Player player, Shop shop) {
        double first = 0;
        double second = 0;
        for (int i = 0; i < this.items.size(); i++) {
            double s = (player.findItem(items.get(i).getName()).getAmount() * quantityWeight) +
                    ((shop.findItem(items.get(i).getName()).getPrice() / player.findItem(items.get(i).getName()).getAverageBuyPrice()) * convenienceWeight);
            if (s > first) {
                first = s;
                demand[0] = items.get(i);
            } else if (s > second) {
                second = s;
                demand[1] = items.get(i);
            }
        }
    }

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
