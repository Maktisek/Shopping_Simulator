package Utilities;

import Utilities.Exceptions.WrongIntervalException;

public class Interval {

    private int upperBound;
    private int lowerBound;

    public Interval(int upperBound, int lowerBound) {
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public void testInterval() throws WrongIntervalException {
        if(upperBound <= lowerBound){
            throw new WrongIntervalException("Upper bound is lower or same as the lower bound");
        }
    }
}
