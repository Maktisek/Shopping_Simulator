package Upgrade;

import java.io.Serializable;

/**
 * This class represents an abstract class implementing {@link Upgrade}.
 * <p>
 *     It stands for classic upgrades and every single upgrade extending this class
 *     should not override its methods.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public abstract class UpgradeBasicType implements Upgrade, Serializable {

    protected int data;
    protected int price;
    protected int level;

    public UpgradeBasicType() {
        setLevel(1);
    }

    @Override
    public void levelUp() {
        this.data += 2 * calculateDials();
        changePrice();
        this.level++;
    }

    /**
     * This method represents a system, which calculates special coefficient based on length of {@link #price}.
     * <p>
     *     The first dial is used to make the change of this coefficient every half before another dial is added.
     *     For {@code  400} it returns {@code 2}, but for {@code 500} it returns {@code 3}.
     * </p>
     * @return the calculated coefficient
     */
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

    public void setLevel(int level) {
        this.level = level;
    }
}
