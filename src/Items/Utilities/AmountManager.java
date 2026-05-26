package Items.Utilities;

import Utilities.Interval;

import java.io.Serializable;
import java.util.Random;

/**
 * This class represents a system, which manages stocks or demand of an item.
 * <p>
 *      {@link #max} stands for the maximum possible amount
 * </p>
 * <p>
 *      {@link #current} stands for the current amount available
 * </p>
 * @author Matěj Pospíšil
 */
public class AmountManager implements Serializable {

    private final int max;
    private int current;
    private final Interval interval;

    public AmountManager(int max, int current, Interval interval) {
        this.max = max;
        this.current = current;
        this.interval = interval;
    }

    /**
     * Randomly (based on {@link #interval}) increments {@link #current}.
     */
    public void stockIn(){
        Random rd = new Random();
        int newCurrent = current + rd.nextInt(interval.getLowerBound(), interval.getUpperBound());
        current = Math.min(newCurrent, max);
    }

    public boolean canDecrement(int amount){
        return amount <= current;
    }

    public void decrement(int amount){
        this.current -= amount;
    }

    public int getMax() {
        return max;
    }

    public Interval getInterval() {
        return interval;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

}
