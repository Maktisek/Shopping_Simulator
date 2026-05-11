package Taxes;

import java.io.Serializable;

public class Tax implements Serializable {

    private int start;
    private int max;
    private int current;
    private int previous;
    private int startDelay;
    private double k;

    public void initializeK(){
        this.k = (Math.log(1000 + startDelay) - Math.log(startDelay + 1));
    }

    public void calculateNewDay(int dayNumber) {
        this.previous = this.current;
        this.current = start + (int) Math.round((calculateAmplitude() * calculateCoefficient(dayNumber)));
    }

    private int calculateAmplitude() {
        return max - start;
    }

    private double calculateCoefficient(int dayNumber) {
        return ((Math.log(dayNumber + startDelay) - Math.log(startDelay + 1)) / k);
    }


    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStartDelay() {
        return startDelay;
    }

    public void setStartDelay(int startDelay) {
        this.startDelay = startDelay;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPrevious() {
        return previous;
    }

    public void setPrevious(int previous) {
        this.previous = previous;
    }
}
