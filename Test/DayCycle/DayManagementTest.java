package DayCycle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DayManagementTest {


    DayManagement dayManagement;

    @Before
    public void setUp() throws Exception {
        dayManagement = new DayManagement();
    }


    @Test
    public void nextDay() {
        assertEquals(DayNames.Monday, dayManagement.getCurrentDay().getDayName());
        dayManagement.nextDay();
        assertEquals(DayNames.Tuesday, dayManagement.getCurrentDay().getDayName());
        for (int i = 0; i < 15; i++) {
            dayManagement.nextDay();
        }
        assertEquals(DayNames.Wednesday, dayManagement.getCurrentDay().getDayName());
    }
}