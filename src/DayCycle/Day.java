package DayCycle;

import Utilities.Important;

import java.io.Serializable;

/**
 * This class represents an individual day.
 * <p>
 *     {@link #dayIncome} stands for how much has the player made this day
 * </p>
 * <p>
 *     {@link #daySpending} stands for how much has the player spent this day
 * </p>
 * <p>
 *     {@link #dayBoughtAmount} stands for how many products has the player bought today
 * </p>
 * <p>
 *     {@link #daySoldAmount} stands for how many products has the player sold today
 * </p>
 * {@link #dayName} stands for classic english day system (monday - sunday) and it cycles through of it.
 * @author Matěj Pospíšil
 */
public class Day implements Serializable {

    private DayName dayName;
    private final int numberOfTheDay;
    private int dayIncome;
    private int daySpending;
    private int dayBoughtAmount;
    private int daySoldAmount;

    /**
     * This constructor is made just for first day implementation
     */
    public Day() {
        this.numberOfTheDay = 0;
        this.dayIncome = 0;
        this.daySpending = 0;
    }

    /**
     * This is the main constructor for creating a new day
     * @param numberOfTheDay the number of the new day
     * @param dayName the name of the new day
     */
    public Day(int numberOfTheDay, DayName dayName) {
        this.numberOfTheDay = numberOfTheDay;
        this.dayIncome = 0;
        this.daySpending = 0;
        this.dayName = dayName;
    }

    public void incrementDayIncome(int income) {
        this.dayIncome += income;
    }

    public void incrementDaySpending(int spending) {
        this.daySpending += spending;
    }

    public boolean canIncrementDayBoughtAmount(int amount, int bound) {
        int afterIncrement = this.dayBoughtAmount + amount;
        return afterIncrement <= bound;
    }

    public void incrementDayBoughtAmount(int amount) {
        this.dayBoughtAmount += amount;
    }

    public boolean canIncrementDaySoldAmount(int amount, int bound) {
        int afterIncrement = this.daySoldAmount + amount;
        return afterIncrement <= bound;
    }

    public void incrementDaySoldAmount(int amount) {
        this.daySoldAmount += amount;
    }

    public int getNumberOfTheDay() {
        return numberOfTheDay;
    }

    public int getDayIncome() {
        return dayIncome;
    }

    public DayName getDayName() {
        return dayName;
    }

    public void setDayName(DayName dayName) {
        this.dayName = dayName;
    }

    public int getDaySpending() {
        return daySpending;
    }

    public void setDayIncome(int dayIncome) {
        this.dayIncome = dayIncome;
    }

    public int getDaySoldAmount() {
        return daySoldAmount;
    }

    public void setDaySoldAmount(int daySoldAmount) {
        this.daySoldAmount = daySoldAmount;
    }

    public int getDayBoughtAmount() {
        return dayBoughtAmount;
    }

    public void setDayBoughtAmount(int dayBoughtAmount) {
        this.dayBoughtAmount = dayBoughtAmount;
    }

    public void setDaySpending(int daySpending) {
        this.daySpending = daySpending;
    }

    /**
     * This method is made for writing down summary of a day in {@link UI.MainUI.ShopUI.Days.DaySummaryPanelUI}
     * @param tax how much is the current tax
     * @return the text in a specific format, which is divided into two parts by ":".
     * This output is later used in {@link Important#insertDots(String, int)}
     */
    public String information(int tax) {
        return "Tax paid:" + Important.parseMoney(tax) + " FR" + "\n" +
                "Income:" + Important.parseMoney(dayIncome) + " FR" + "\n" +
                "Spending:" + Important.parseMoney(daySpending) + " FR" + "\n" +
                "Bought:" + Important.parseMoney(dayBoughtAmount) + " products" + "\n" +
                "Sold:" + Important.parseMoney(daySoldAmount) + " products" + "\n";
    }
}
