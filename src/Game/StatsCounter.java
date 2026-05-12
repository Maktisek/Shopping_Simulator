package Game;

public class StatsCounter {

    private int shipped;
    private int bought;
    private int earnings;
    private int costs;


    public int averageShipPrice(){
        if(shipped == 0){
            return 0;
        }

        return earnings / shipped;
    }

    public int averageBuyPrice(){
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

    public int getEarnings() {
        return earnings;
    }

    public void setEarnings(int earnings) {
        this.earnings = earnings;
    }

    public int getCosts() {
        return costs;
    }

    public void setCosts(int costs) {
        this.costs = costs;
    }
}
