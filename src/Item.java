import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Item {


    private int price;
    private int amount;
    private ItemNames  name;
    private int wholeBoughtPrice;
    private Queue<Evidence> evidences;

    /**
     * The constructor for adding items into shop.
     * It creates an item, which has set amount to -1 - shopping variation.
     * @param price The price of the product to be sold.
     * @param name The name of the product to be sold.
     */
    public Item(int price, String name) throws WrongItemException{
        setPrice(price);
        this.amount = -1;
        setName(name);
        wholeBoughtPrice = 0;
        evidences = new LinkedList<>();
    }

    public Item(int price, int wholeBoughtPrice, String name, int amount) throws WrongItemException, IllegalArgumentException{
        setPrice(price);
        setWholeBoughtPrice(wholeBoughtPrice);
        setName(name);
        setAmount(amount);
        evidences = new LinkedList<>();
    }


    public Item() {
    }

    public void setPrice(int price) throws WrongItemException{
        if(price > 0){
            this.price = price;
        }else {
            throw new WrongItemException("Wrong price");
        }
    }

    public void setWholeBoughtPrice(int wholeBoughtPrice) throws WrongItemException {
        if(wholeBoughtPrice >= 0){
            this.wholeBoughtPrice = wholeBoughtPrice;
        }else {
            throw new WrongItemException("Wrong whole bought price");
        }
    }

    public void setAmount(int amount) throws WrongItemException{
        if(amount >= 0){
            this.amount = amount;
        }else {
            throw new WrongItemException("Wrong price");
        }
    }

    public void setName(String name) throws IllegalArgumentException{
       this.name = ItemNames.valueOf(name);
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public int getWholeBoughtPrice() {
        return wholeBoughtPrice;
    }

    public ItemNames getName() {
        return name;
    }

    public boolean addEvidence(int amount) throws WrongEvidenceException, WrongItemException{
        itemBought(amount);
        return evidences.add(new Evidence(amount, this.price));
    }

    public void moveWithAmount(int move) throws WrongItemException{
        int afterMove = amount + move;
        if(afterMove < 0){
            throw new WrongItemException("Wrong movement with amount");
        }else {
            amount = afterMove;
        }
    }

    public int itemSold(int amount) throws WrongItemException{
        moveWithAmount(amount);
        amount = amount * -1;
        int result = 0;
        while (amount != 0){
            int[] arr = evidences.element().register(amount);
            amount = arr[0];
            result = result + (arr[3] * this.price);
            if(arr[1] == 0){
                evidences.poll();
            }
            this.wholeBoughtPrice = this.wholeBoughtPrice - arr[2];
        }
        return result;
    }

    public void itemBought(int amount) throws WrongItemException{
        moveWithAmount(amount);
        this.wholeBoughtPrice = this.wholeBoughtPrice + (this.price * amount);
    }

    @Override
    public String toString() {
        return "Item{" +
                "price=" + price +
                ", amount=" + amount +
                ", name=" + name +
                ", wholeBoughtPrice=" + wholeBoughtPrice +
                '}';
    }

    private static class Evidence{
        int amount;
        int price;

        public Evidence(int amount, int price) throws WrongEvidenceException{
            setAmount(amount);
            setPrice(price);
        }

        public void setAmount(int amount) throws WrongEvidenceException{
            if(amount > 0){
                this.amount = amount;
            }else {
                throw new WrongEvidenceException("Wrong amount inserted");
            }
        }

        public void setPrice(int price) throws WrongEvidenceException{
            if(price > 0){
                this.price = price;
            }else {
                throw new WrongEvidenceException("Wrong amount inserted");
            }
        }

        public int getAmount() {
            return amount;
        }

        public int getPrice() {
            return price;
        }

        public int[] register(int move){
            if(move >= this.amount){
                int sold = this.amount;
                move = move - this.amount;
                int earnings = this.amount * this.price;
                this.amount = 0;
                return new int[]{move, this.amount, earnings, sold};
            }else{
                int sold = move;
                this.amount = this.amount - move;
                int earnings = move * this.price;
                move = 0;
                return new int[]{move, this.amount, earnings, sold};
            }
        }
    }

}
