package DayCycle;


import java.util.ArrayList;
import java.util.HashMap;

public class DayManagement {

    private int numberOfDays;
    private Day currentDay;
    private final ArrayList<Day> daysDatabase;
    private final HashMap<Integer, DayNames> daysNames;


    public DayManagement() {
        this.numberOfDays = 0;
        this.currentDay = new Day(0);
        this.daysDatabase = new ArrayList<>();
        this.daysNames = new HashMap<>();
        loadNames();
        nextDay();
    }

    public void loadNames(){
        for (int i = 1; i < DayNames.values().length; i++) {
            assert daysNames != null;
            daysNames.put(i, DayNames.values()[i - 1]);
        }
    }

    private int calculateDay(){
        int result = this.numberOfDays;
        while (result > 7){
            result -= 7;
        }
        return result;
    }

    public void nextDay() {
        this.numberOfDays++;
        int dayNumber = calculateDay();
        Day day = new Day(this.numberOfDays, daysNames.get(dayNumber));
        this.daysDatabase.add(day);
        this.currentDay = day;
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

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public void setCurrentDay(Day currentDay) {
        this.currentDay = currentDay;
    }

    @Override
    public String toString() {
        return "DayManagement{" +
                "numberOfDays=" + numberOfDays +
                ", currentDay=" + currentDay +
                ", daysDatabase=" + daysDatabase +
                ", daysNames=" + daysNames +
                '}';
    }
}
