package DayCycle;

public class Day {

    private DayNames dayName;
    private final int number;
    private int dayIncome;
    private int daySpending;
    private int dayBoughtAmount;
    private int daySoldAmount;

    public Day(int number) {
        this.number = number;
        this.dayIncome = 0;
        this.daySpending = 0;
    }

    public Day(int number, DayNames dayName) {
        this.number = number;
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

    public int getNumber() {
        return number;
    }

    public int getDayIncome() {
        return dayIncome;
    }

    public DayNames getDayName() {
        return dayName;
    }

    public void setDayName(DayNames dayName) {
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

    @Override
    public String toString() {
        return "Day{" +
                "dayName=" + dayName +
                ", number=" + number +
                ", dayIncome=" + dayIncome +
                ", daySpending=" + daySpending +
                ", dayAmount=" + dayBoughtAmount +
                '}';
    }
}
