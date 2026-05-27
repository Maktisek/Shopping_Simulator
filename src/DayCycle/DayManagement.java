package DayCycle;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents a day management - all days are stored and hold here.
 * <p>
 *     {@link #currentDay} stands for the current day
 * </p>
 * <p>
 *     {@link #daysDatabase} is an {@link ArrayList} filled by all previous days
 * </p>
 * <p>
 *     {@link #daysNames} stands for a {@link HashMap} with key being an integer (1 to 7) and key being {@link DayName}
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class DayManagement implements Serializable {

    private int numberOfDays;
    private Day currentDay;
    private final ArrayList<Day> daysDatabase;
    private final HashMap<Integer, DayName> daysNames;


    public DayManagement() {
        this.numberOfDays = 0;
        this.currentDay = new Day();
        this.daysDatabase = new ArrayList<>();
        this.daysNames = new HashMap<>();
        loadNames();
        nextDay();
    }

    /**
     * Loads {@link #daysNames} with names of the days and their own number.
     */
    public void loadNames(){
        for (int i = 0; i < DayName.values().length; i++) {
            assert daysNames != null;
            daysNames.put(i + 1, DayName.values()[i]);
        }
    }

    /**
     * Calculates, which day should the {@link #currentDay} be.
     * @return the number of the current day, which matches (1 to 7) interval.
     */
    private int calculateDay(){
        int result = this.numberOfDays;
        while (result > 7){
            result -= 7;
        }
        return result;
    }

    /**
     * Sets new {@link #currentDay} by incrementing the day number and calculating its {@link DayName}.
     */
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
}
