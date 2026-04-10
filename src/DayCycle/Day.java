package DayCycle;

public class Day {

    private DayNames dayName;
    private final int number;
    private int dayIncome;
    private int daySpending;
    private int dayAmount;

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

    public boolean canIncrementDayAmount(int amount, int bound) {
        int afterIncrement = this.dayAmount + amount;
        return afterIncrement <= bound;
    }

    public void incrementDayAmount(int amount) {
        this.dayAmount += amount;
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

    @Override
    public String toString() {
        return "Day{" +
                "dayName=" + dayName +
                ", number=" + number +
                ", dayIncome=" + dayIncome +
                ", daySpending=" + daySpending +
                ", dayAmount=" + dayAmount +
                '}';
    }
}
