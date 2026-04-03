package DayCycle;

public class Day {

    private DayNames dayName;
    private final int number;
    private final int dayIncome;

    public Day(int number) {
        this.number = number;
        this.dayIncome = 0;
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
}
