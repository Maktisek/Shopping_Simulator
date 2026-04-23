package Items;

public class ItemDelivery {

    private ItemNames name;
    private int boughtPrice;
    private int daysToBeDelivered;
    private int amount;

    public ItemDelivery(ItemNames name, int amount, int boughtPrice, int daysToBeDelivered) {
        this.name = name;
        this.boughtPrice = boughtPrice;
        this.daysToBeDelivered = daysToBeDelivered;
        this.amount = amount;
    }

    public ItemNames getName() {
        return name;
    }

    public void setName(ItemNames name) {
        this.name = name;
    }

    public int getDaysToBeDelivered() {
        return daysToBeDelivered;
    }

    public void setDaysToBeDelivered(int daysToBeDelivered) {
        this.daysToBeDelivered = daysToBeDelivered;
    }

    public int getBoughtPrice() {
        return boughtPrice;
    }

    public void setBoughtPrice(int boughtPrice) {
        this.boughtPrice = boughtPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
