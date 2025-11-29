import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Player {


    private String name;
    private int balance;
    private int allTimeBalance;
    private ArrayList<Item> items;
    private Shop currentShop;
    private int dayNumber;
    private Day currentDay;
    private ArrayList<Day> daysDatabase;

    public Player() {
        this.items = new ArrayList<>();
        this.daysDatabase = new ArrayList<>();
        loadBasicPlayer();
        loadPlayerItems();
        loadPlayerDays();
    }

    public void loadBasicPlayer() {
        try (BufferedReader br = new BufferedReader(new FileReader("res\\playerSave.txt"))) {
            String line1 = br.readLine();
            String line2 = br.readLine();
            String line3 = br.readLine();
            String[] data1 = line1.split(">");
            String[] data2 = line2.split(">");
            String[] data3 = line3.split(">");
            this.name = data1[0];
            this.balance = Integer.parseInt(data1[1]);
            this.allTimeBalance = Integer.parseInt(data1[2]);
            this.dayNumber = Integer.parseInt(data1[3]);
            this.currentDay = new Day(Integer.parseInt(data2[0]), Integer.parseInt(data2[1]));
        } catch (IOException _) {
        }


    }

    public void loadPlayerItems() {
        try (BufferedReader br = new BufferedReader(new FileReader("res\\playerItems.txt"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(">");
                Item temp = new Item(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]));
                temp.loadEvidences(data);
                items.add(temp);
            }
        } catch (IOException | WrongItemException | WrongEvidenceException e) {
            System.out.println(e.getMessage());
        }


    }

    public void loadPlayerDays() {
        try (BufferedReader br = new BufferedReader(new FileReader("res\\playerDays.txt"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null){
                String[] data = line.split(">");
                daysDatabase.add(new Day(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
            }
        } catch (IOException _) {
        }
    }



    public String getName() {
        return name;
    }

    public ArrayList<Day> getDaysDatabase() {
        return daysDatabase;
    }

    public Day getCurrentDay() {
        return currentDay;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Shop getCurrentShop() {
        return currentShop;
    }

    public int getAllTimeBalance() {
        return allTimeBalance;
    }

    public int getBalance() {
        return balance;
    }

    private static class Day {
        int number;
        int dayIncome;

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
    }

}
