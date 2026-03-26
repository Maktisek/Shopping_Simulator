package Items;

public class Item {


    private ItemNames name;
    private int price;
    private int initialPrice;

    public Item() {
    }

    public Item(int price, ItemNames name, int initialPrice) throws WrongItemException{
        setPrice(price);
        setName(name);
        this.initialPrice = initialPrice;
    }

    public void setPrice(int price) throws WrongItemException {
        if(price > 0){
            this.price = price;
        }else {
            throw new WrongItemException("The price cannot be lower than 0");
        }
    }

    public void setName(ItemNames name){
       this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public int getInitialPrice() {
        return initialPrice;
    }

    public ItemNames getName() {
        return name;
    }

    public Item copy() throws WrongItemException{
        return new Item(this.price, this.name, this.initialPrice);
    }

    @Override
    public String toString() {
        return "Player.Items.Item{" +
                "price=" + price +
                ", name=" + name +
                ", initialPrice=" + initialPrice +
                '}';
    }
}
