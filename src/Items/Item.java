package Items;

public class Item {


    private ItemNames name;
    private int basePrice;
    private int currentPrice;
    private double penalization;
    private int currentDayAmount;

    public Item() {
    }

    public Item(ItemNames name, int currentDayAmount, double penalization, int currentPrice, int basePrice) {
        this.name = name;
        this.currentDayAmount = currentDayAmount;
        this.penalization = penalization;
        this.currentPrice = currentPrice;
        this.basePrice = basePrice;
    }

    public void setCurrentPrice(int currentPrice) throws WrongItemException {
        if (currentPrice > 0) {
            this.currentPrice = currentPrice;
        } else {
            throw new WrongItemException("The price cannot be lower than 0");
        }
    }

    public Item copy() {
        return new Item(this.name, this.currentDayAmount, this.penalization, this.currentPrice, this.basePrice);
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

    public int getCurrentDayAmount() {
        return currentDayAmount;
    }

    public void setCurrentDayAmount(int currentDayAmount) {
        this.currentDayAmount = currentDayAmount;
    }

    public double getPenalization() {
        return penalization;
    }

    public void setPenalization(double penalization) {
        this.penalization = penalization;
    }

    @Override
    public String toString() {
        return null;
    }
}
