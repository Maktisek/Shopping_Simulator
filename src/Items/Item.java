package Items;

public class Item {


    private ItemNames name;
    private int basePrice;
    private int currentPrice;

    public Item() {
    }

    public Item(ItemNames name, int currentPrice, int basePrice) throws WrongItemException{
        this.name = name;
        setCurrentPrice(currentPrice);
        this.basePrice = basePrice;
    }

    public void setCurrentPrice(int currentPrice) throws WrongItemException {
        if (currentPrice > 0) {
            this.currentPrice = currentPrice;
        } else {
            throw new WrongItemException("The price of " + name + " is under 0");
        }
    }

    public Item copy() throws WrongItemException {
        return new Item(this.name, this.currentPrice, this.basePrice);
    }


    public void setName(ItemNames name) {
        this.name = name;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public ItemNames getName() {
        return name;
    }


    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }



    @Override
    public String toString() {
        return null;
    }
}
