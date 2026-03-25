package DayCycle;


import java.util.ArrayList;

public class DayManagement {

    private int numberOfDays;
    private Day currentDay;
    private final ArrayList<Day> daysDatabase;


    public DayManagement() {
        this.currentDay = new Day(0);
        daysDatabase = new ArrayList<>();
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public void setCurrentDay(Day currentDay) {
        this.currentDay = currentDay;
    }

    public void nextDay() {
        numberOfDays++;
        Day day = new Day(this.numberOfDays);
        daysDatabase.add(day);
        currentDay = day;
    }

    public ArrayList<Day> getDaysDatabase() {
        return daysDatabase;
    }

    public Day getCurrentDay() {
        return currentDay;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

}
