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

    @Override
    public void changePrice() {
        this.price += 50;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
