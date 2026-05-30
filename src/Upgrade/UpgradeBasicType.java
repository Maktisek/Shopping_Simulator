package Upgrade;

import Upgrade.Utilities.UpgradeType;

import java.io.Serializable;

/**
 * This class represents a class implementing {@link Upgrade}.
 * <p>
 *     It stands for upgrades of basic type. Every single upgrade then follows same math
 *     system.
 * </p>
 * Currently in version {@code 1.0} there is no need for another type of upgrade, making this the only type.
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class UpgradeBasicType implements Upgrade, Serializable {

    private int data;
    private int price;
    private int level;
    @SuppressWarnings("unused")
    private UpgradeType type;

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
    public UpgradeType nameInfo() {
        return type;
    }

    @Override
    public void changePrice() {
        this.price = (int) (this.price * 1.15);
    }

    @Override
    public void setNameInfo(UpgradeType type) {
        this.type = type;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setData(int data) {
        this.data = data;
    }
}
