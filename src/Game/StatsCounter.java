package Game;

import java.io.Serializable;

/**
 * This class represents a simple POJO with little calculations.
 * <p>
 *     It is used to store all statistic data.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class StatsCounter implements Serializable {

    private int shipped;
    private int bought;
    private long earnings;
    private long costs;


    public long averageShipPrice(){
        if(shipped == 0){
            return 0;
        }
        return earnings / shipped;
    }

    public long averageBuyPrice(){
        if(bought == 0){
            return 0;
        }
        return costs / bought;
    }

    public int getShipped() {
        return shipped;
    }

    public void setShipped(int shipped) {
        this.shipped = shipped;
    }

    public int getBought() {
        return bought;
    }

    public void setBought(int bought) {
        this.bought = bought;
    }

    public long getEarnings() {
        return earnings;
    }

    public void setEarnings(long earnings) {
        this.earnings = earnings;
    }

    public long getCosts() {
        return costs;
    }

    public void setCosts(long costs) {
        this.costs = costs;
    }
}
