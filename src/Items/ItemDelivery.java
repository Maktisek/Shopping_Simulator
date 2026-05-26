package Items;

import java.io.Serializable;

/**
 * This class represents an undelivered product, which still waits until will be shipped into players stocks.
 * <p>
 *     It is a basic POJO, cloud be record class
 * </p>
 * @author Matěj Pospíšil
 */
public class ItemDelivery implements Serializable {

    private String name;
    private int boughtPrice;
    private int daysToBeDelivered;
    private int amount;

    public ItemDelivery(String name, int amount, int boughtPrice, int daysToBeDelivered) {
        this.name = name;
        this.boughtPrice = boughtPrice;
        this.daysToBeDelivered = daysToBeDelivered;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDaysToBeDelivered() {
        return daysToBeDelivered;
    }

    public void setDaysToBeDelivered(int daysToBeDelivered) {
        this.daysToBeDelivered = daysToBeDelivered;
    }

    public int getBoughtPrice() {
        return boughtPrice;
    }

    public void setBoughtPrice(int boughtPrice) {
        this.boughtPrice = boughtPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
