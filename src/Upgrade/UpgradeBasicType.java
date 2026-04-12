package Upgrade;

public abstract class UpgradeBasicType implements Upgrade{

    private int data;
    private int price;
    private int level;

    public UpgradeBasicType() {
    }

    @Override
    public void levelUp() {
        this.data += 10 * calculateDials();
        changePrice();
        this.level++;
    }

    private int calculateDials(){
        String s = String.valueOf(this.price);
        int dials = s.length() - 1;
        int firstDial = Character.getNumericValue(s.charAt(0));
        return dials + (firstDial / 5);
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
    public int levelInfo() {
        return this.level;
    }

    @Override
    public void changePrice() {
        this.price = (int) (this.price * 1.15);
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "UpgradeBasicType{" +
                "data=" + data +
                ", price=" + price +
                ", level=" + level +
                '}';
    }
}
