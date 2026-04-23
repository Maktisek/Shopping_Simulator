package Items;

public class ItemShop {

    private Item item;
    private double penalization;
    private int currentDayAmount;
    private int priceSensitivity;
    private int daysToBeDelivered;

    public ItemShop() {
    }

    public void updatePrice() throws WrongItemException {
        this.item.setCurrentPrice((int) Math.round((this.item.getBasePrice() * penalization * (1 + calculateBonusPenalization()))));
    }

    private double calculateBonusPenalization() {
        double result = ((double) currentDayAmount / this.priceSensitivity);

        return Math.min(result, 0.25);
    }

    public void updatePenalization(double change) {
        double afterChange = this.penalization + (change * (5 / (penalization) / 2));
        if (afterChange < 0.9) {
            this.penalization = 0.9;
        } else if (afterChange > 1.2) {
            this.penalization = 1.2;
        } else {
            this.penalization = afterChange;
        }
    }

    public void newDayPenalization(double change) {
        double afterChange = this.penalization + change;
        this.penalization = Math.max(afterChange, 0.9);
    }

    public void updateCurrentDayAmount(int change) {
        this.currentDayAmount += change;
    }

    public void resetCurrentDayAmount() {
        this.currentDayAmount = 0;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public double getPenalization() {
        return penalization;
    }

    public void setPenalization(double penalization) {
        this.penalization = penalization;
    }

    public int getCurrentDayAmount() {
        return currentDayAmount;
    }

    public void setCurrentDayAmount(int currentDayAmount) {
        this.currentDayAmount = currentDayAmount;
    }

    public int getPriceSensitivity() {
        return priceSensitivity;
    }

    public void setPriceSensitivity(int priceSensitivity) {
        this.priceSensitivity = priceSensitivity;
    }

    public int getDaysToBeDelivered() {
        return daysToBeDelivered;
    }

    public void setDaysToBeDelivered(int daysToBeDelivered) {
        this.daysToBeDelivered = daysToBeDelivered;
    }

    @Override
    public String toString() {
        return "ItemShop{" +
                "item=" + item +
                ", penalization=" + penalization +
                ", currentDayAmount=" + currentDayAmount +
                ", priceSensitivity=" + priceSensitivity +
                '}';
    }

    public ItemShop(Item item, int priceSensitivity, int currentDayAmount, double penalization) {
        this.item = item;
        this.priceSensitivity = priceSensitivity;
        this.currentDayAmount = currentDayAmount;
        this.penalization = penalization;
    }
}
