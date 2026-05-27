package Taxes;

import Utilities.Important;

import java.beans.BeanProperty;
import java.io.Serializable;

/**
 * This class represents an individual tax.
 * <p>
 * Every single day the player has to pay the tax. As the days goes, the tax is getting higher and higher until
 * it matches or overlap the bound. This in-game mechanic brings more challenge to the game.
 * </p>
 * <p>
 * It is simpler class since it only consist few fields and some calculations based on logarithm, but let me clear them out:
 * </p>
 * <ul>
 *     <li>{@link #start} stands for the value, from which the tax start</li>
 *     <li>{@link #max} is the pre-mentioned bound above which the tax cannot go</li>
 *     <li>{@link #current} represents the current value of the tax</li>
 *     <li>{@link #previous} hold information about the previous state of the tax (usable when displaying information about last day)</li>
 *     <li>{@link #startDelay} shifts the start of the logarithmic function - the larger it is the more in the "flat" part of
 *     the graph the tax starts. So it means that the tax will be slower on the start.</li>
 *     <li>{@link #dayNumber} stands for the "X" in the function. The higher day is the higher the tax is.</li>
 *     <li>{@link #maxDayNumber} represents the maximum possible day. Basically, when {@link #dayNumber} is higher than that, {@link #current}
 *     goes above {@link #max} and is immediately set to {@link #max}.</li>
 *     <li>{@link #k} this value is loaded after start of the game, and it is not changed afterward. I would like to make it final
 *     but Java needs this value to be set in constructor, which is something I do not want to do</li>
 * </ul>
 * @since   1.0 - (pre-release version)
 */

public class Tax implements Serializable {

    private int start;
    private int max;
    private int current;
    private int previous;
    @SuppressWarnings("unused")
    private int startDelay;
    private int dayNumber;
    @SuppressWarnings("unused")
    private int maxDayNumber;
    private double k;

    /**
     * Initializes coefficient "K"
     */
    public void initializeK() {
        this.k = (Math.log(maxDayNumber + startDelay) - Math.log(startDelay + 1));
        System.out.println(k);
    }

    /**
     * Calculates new value of {@link #current} and if it larger than {@link #max} then {@link #current} is set to {@link #max}
     */
    public void calculateNewDay() {
        this.previous = this.current;
        this.current = start + (int) Math.round((calculateAmplitude() * calculateCoefficient()));
        if(current > max){
            this.current = max;
        }
    }

    private int calculateAmplitude() {
        return max - start;
    }

    /**
     * Calculates the coefficient which multiplies the amplitude.
     * <p>
     *     When {@link #dayNumber} matches {@link #maxDayNumber} the calculation ends up being {@code 1.0}.
     * </p>
     * @return the calculated amplitude
     */
    private double calculateCoefficient() {
        return ((Math.log(dayNumber + startDelay) - Math.log(startDelay + 1)) / k);
    }

    /**
     * This method is called anytime player buys new rebirth.
     * <p>
     *     After every rebirth the game can be sure that he player is doing well. This is the right time make the taxes more aggressive.
     * </p>
     * Here are changes that happen when new rebirth is bought:
     * <ul>
     *     <li>{@link #start} is set to 50% of what is {@link #current}</li>
     *     <li>{@link #current} is then set to start</li>
     *     <li>{@link #max} is set to 400% of what it was before (this value will be probably changed in the future, since it was not
     *     properly tested)</li>
     *     <li>{@link #dayNumber} is set to 1, because the player starts from day 1</li>
     * </ul>
     */
    public void updateAfterRebirth() {
        this.start = Math.max(2, (int) (((double) this.current / (double) 100) * 50));
        this.current = this.start;
        this.max = (int) (((double) max / (double) 100) * 400);
        this.dayNumber = 1;
    }

    public void incrementDayNumber() {
        this.dayNumber++;
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
}
