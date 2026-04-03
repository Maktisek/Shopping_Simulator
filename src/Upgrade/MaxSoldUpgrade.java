package Upgrade;

public class MaxSoldUpgrade implements Upgrade {

    private int maxSoldPossible;
    private int price;

    public MaxSoldUpgrade() {
        this.maxSoldPossible = 100;
        this.price = 100;
    }


    @Override
    public void levelUp() {
        this.maxSoldPossible += 10;
    }

    @Override
    public int dataInfo() {
        return maxSoldPossible;
    }

    @Override
    public int priceInfo() {
        return this.price;
    }
}
