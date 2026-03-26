package NPCs;

import Items.Item;
import Items.ItemPlayer;
import Items.Shop;
import Player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class NPC {

    private int quantityWeight;
    private int convenienceWeight;
    private ArrayList<Item> items;
    private Item[] demand;

    public void loadDemand(Player player, Shop shop) {
        resetDemand();
        double first = 0;
        double second = 0;
        for (Item item : this.items) {
            ItemPlayer playersItem = player.findItem(item.getName());
            Item shopsItem = shop.findItem(item.getName());
            if (playersItem != null && shopsItem != null) {
                double playerAverage = playersItem.getAverageBuyPrice();
                if (playerAverage != 0) {
                    double s = (playersItem.getAmount() * quantityWeight) +
                            ((shopsItem.getPrice() / playerAverage) * convenienceWeight);
                    if (s > first) {
                        demand[1] = demand[0];
                        second = first;
                        first = s;
                        demand[0] = item;
                    } else if (s > second) {
                        second = s;
                        demand[1] = item;
                    }
                }
            }
        }
        checkLoadDemand();
    }

    private void checkLoadDemand(){
        if(demand[0] == null && demand[1] == null){
            fillWholeDemandRandomly();
        }else if(demand[1] == null){
            Random rd = new Random();
            do{
                demand[1] = items.get(rd.nextInt(items.size()));
            }while (demand[0] == demand[1]);
        }
    }

    private void fillWholeDemandRandomly(){
        Random rd = new Random();
        int indexOne = rd.nextInt(items.size());
        int indexTwo;
        do{
            indexTwo = rd.nextInt(items.size());
        }while (indexOne == indexTwo);

        demand[0] = items.get(indexOne);
        demand[1] = items.get(indexTwo);
    }

    private void resetDemand(){
        Arrays.fill(demand, null);
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
