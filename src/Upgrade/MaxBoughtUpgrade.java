package Upgrade;

public class MaxBoughtUpgrade implements Upgrade{

    private int maxBoughtPossible;
    private int price;

    public MaxBoughtUpgrade() {
        this.maxBoughtPossible = 100;
        this.price = 50;
    }

    @Override
    public void levelUp() {
        this.maxBoughtPossible += 10;
    }

    @Override
    public int dataInfo() {
        return maxBoughtPossible;
    }

    @Override
    public int priceInfo() {
        return this.price;
    }
}
