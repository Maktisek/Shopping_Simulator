package Items;

public class Item {


    private ItemNames name;
    private int basePrice;
    private int currentPrice;
    private double penalization;
    private int currentDayAmount;
    private int priceSensitivity;

    public Item() {
    }

    public Item(ItemNames name, int currentDayAmount, double penalization, int currentPrice, int basePrice, int priceSensitivity) throws WrongItemException{
        this.name = name;
        this.currentDayAmount = currentDayAmount;
        this.penalization = penalization;
        setCurrentPrice(currentPrice);
        this.basePrice = basePrice;
        setPriceSensitivity(priceSensitivity);
    }

    public void setCurrentPrice(int currentPrice) throws WrongItemException {
        if (currentPrice > 0) {
            this.currentPrice = currentPrice;
        } else {
            throw new WrongItemException("The price cannot be lower than 0");
        }
    }

    public void setPriceSensitivity(int priceSensitivity) throws WrongItemException{
        if(this.priceSensitivity > 0){
            this.priceSensitivity = priceSensitivity;
        }else {
            throw new WrongItemException("Price sensitivity must be over 0");
        }
    }

    public Item copy() throws WrongItemException {
        return new Item(this.name, this.currentDayAmount, this.penalization, this.currentPrice, this.basePrice, priceSensitivity);
    }

    public void updatePrice() throws ArithmeticException{
        this.currentPrice = (int) Math.round((this.basePrice * penalization * (1 + ((double) currentDayAmount / this.priceSensitivity))));
    }

    public void updatePenalization(int change){
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

    public int getPriceSensitivity() {
        return priceSensitivity;
    }



    @Override
    public String toString() {
        return null;
    }
}
