package Upgrade;

public class Rebirth {

    private double multiplier;
    private int price;
    private int amount;

    public Rebirth(int price) {
        this.multiplier = 1;
        this.price = price;
        this.amount = 1;
    }

    public void newRebirth(){
        this.multiplier = this.multiplier * (1 + (0.3/amount));
        this.price = (int) (this.price * (1 + (amount * 0.04)));
        this.amount++;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Rebirth{" +
                "multiplier=" + multiplier +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
