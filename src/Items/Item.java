package Items;

public class Item {


    private int price;
    private ItemNames name;
    private final int initialPrice;

    public Item(int price, String name, int initialPrice) throws WrongItemException, IllegalArgumentException{
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

    public void setName(String name) throws IllegalArgumentException{
       this.name = ItemNames.valueOf(name);
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

    @Override
    public String toString() {
        return "Player.Items.Item{" +
                "price=" + price +
                ", name=" + name +
                ", initialPrice=" + initialPrice +
                '}';
    }
}
