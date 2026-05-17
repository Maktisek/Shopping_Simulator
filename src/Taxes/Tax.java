package Taxes;

import Utilities.Important;

import java.io.Serializable;

public class Tax implements Serializable {

    private int start;
    private int max;
    private int current;
    private int previous;
    private int startDelay;
    private int dayNumber;
    private double k;

    public void initializeK(){
        this.k = (Math.log(1000 + startDelay) - Math.log(startDelay + 1));
    }

    public void calculateNewDay() {
        this.previous = this.current;
        this.current = start + (int) Math.round((calculateAmplitude() * calculateCoefficient()));
    }

    private int calculateAmplitude() {
        return max - start;
    }

    private double calculateCoefficient() {
        return ((Math.log(dayNumber + startDelay) - Math.log(startDelay + 1)) / k);
    }

    public void updateAfterRebirth(){
        this.start = ((Important.choseOver(2, (int) (((double) this.current / (double) 100) * 50))));
        this.current = this.start;
        this.dayNumber = 1;
    }

    public void incrementDayNumber(){
        this.dayNumber++;
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
