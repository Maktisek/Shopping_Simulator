package Upgrade;

public abstract class UpgradeType implements Upgrade{

    private int data;
    private int price;

    public UpgradeType() {
    }

    @Override
    public void levelUp() {
        this.data += 10;
    }

    @Override
    public int dataInfo() {
        return data;
    }

    @Override
    public int priceInfo() {
        return this.price;
    }
}
