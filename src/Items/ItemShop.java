package Items;

public class ItemShop {

    private Item item;
    private double penalization;
    private int currentDayAmount;
    private int priceSensitivity;

    public ItemShop() {
    }

    public void setPriceSensitivity(int priceSensitivity) throws WrongItemException{
        if(this.priceSensitivity > 0){
            this.priceSensitivity = priceSensitivity;
        }else {
            throw new WrongItemException("The price sensitivity of " + this.item.getName() + " is under 0");
        }
    }

    public void updatePrice() throws WrongItemException{
        this.item.setCurrentPrice((int) Math.round((this.item.getBasePrice() * penalization * (1 + ((double) currentDayAmount / this.priceSensitivity)))));
    }

    public void updatePenalization(double change){
        double afterChange = this.penalization + change;
        if(afterChange < 0.5){
            this.penalization = 0.5;
        }else if(afterChange > 5){
            this.penalization = 5;
        }else {
            this.penalization = afterChange;
        }
    }

    public void updateCurrentDayAmount(int change){
        this.currentDayAmount += change;
    }

    public void resetCurrentDayAmount(){
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

    public ItemShop(Item item, int priceSensitivity, int currentDayAmount, double penalization) {
        this.item = item;
        this.priceSensitivity = priceSensitivity;
        this.currentDayAmount = currentDayAmount;
        this.penalization = penalization;
    }
}
