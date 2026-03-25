package DayCycle;

public class Day {

    private final int number;
    private final int dayIncome;

    public Day(int number) {
        this.number = number;
        dayIncome = 0;
    }

    public Day(int number, int dayIncome) {
        this.number = number;
        this.dayIncome = dayIncome;
    }

    public int getNumber() {
        return number;
    }

    public int getDayIncome() {
        return dayIncome;
    }

    @Override
    public String toString() {
        return "Day{" +
                "number=" + number +
                ", dayIncome=" + dayIncome +
                '}';
    }
}
