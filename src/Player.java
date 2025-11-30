import java.io.*;
import java.util.ArrayList;

public class Player {


    private String name;
    private int balance;
    private int allTimeBalance;
    private ArrayList<ItemPlayer> items;
    private Shop currentShop;
    private DayManagement dayManagement;

    public Player() {
        this.items = new ArrayList<>();
        loadBasicPlayer();
        loadPlayerItems();
        loadPlayerDays();
    }

    private void loadBasicPlayer() {
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
            this.dayManagement = new DayManagement(Integer.parseInt(data2[0]), Integer.parseInt(data2[1]));
        } catch (IOException _) {
        }
    }

    private void loadPlayerItems() {
        try (BufferedReader br = new BufferedReader(new FileReader("res\\playerItems.txt"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(">");
                ItemPlayer temp = new ItemPlayer(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]));
                temp.loadEvidences(data);
                items.add(temp);
            }
        } catch (IOException | WrongItemException | WrongEvidenceException e) {
            System.out.println(e.getMessage());
        }


    }

    private void loadPlayerDays() {
        try (BufferedReader br = new BufferedReader(new FileReader("res\\playerDays.txt"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null){
                String[] data = line.split(">");
                dayManagement.addDay(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
            }
        } catch (IOException _) {
        }
    }

    public void save(){
        saveBasicPlayer();
        savePlayerItems();
        savePlayerDays();
    }

    private void saveBasicPlayer(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("res\\playerSave.txt"))){
            bw.write(basicPlayerIntoCSV() + "\n");
            bw.write(this.dayManagement.getCurrentDay().dayIntoCSV()+ "\n");
            bw.write("Current shop will be added in future");
        } catch (IOException _) {
        }

    }

    private void savePlayerItems(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("res\\playerItems.txt"))){
            bw.write("price>wholeBoughtPrice>name>amount>initialPrice>now all evidences in (amount>price) \n");
            for (ItemPlayer item : items) {
                bw.write(item.intoCSV() + "\n");
            }
        }catch (IOException _) {
        }
    }

    private void savePlayerDays(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("res\\playerDays.txt"))){
            bw.write("number of the day>dayIncome");
            for (Day day : dayManagement.getDaysDatabase()) {
                bw.write(day.dayIntoCSV()+"\n");
            }
        }catch (IOException _) {
        }
    }


    public String basicPlayerIntoCSV(){
        return this.name+">"+this.balance+">"+this.allTimeBalance;
    }

    public ArrayList<ItemPlayer> getItems() {
        return items;
    }

    public static class Day {
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

        public String dayIntoCSV(){
            return this.number+">"+this.dayIncome;
        }
    }

    public static class DayManagement {
        private int numberOfDays;
        private Player.Day currentDay;
        private ArrayList<Day> daysDatabase;

        public DayManagement(int dayNumber, int incomeCurrentDay) {
            this.currentDay = new Day(dayNumber, incomeCurrentDay);
            daysDatabase = new ArrayList<>();
        }

        public void setNumberOfDays(int numberOfDays) {
            this.numberOfDays = numberOfDays;
        }

        public void setCurrentDay(Player.Day currentDay) {
            this.currentDay = currentDay;
        }

        public void addDay(int number, int income){
            numberOfDays++;
            daysDatabase.add(new Day(number, income));
        }

        public void addNextDay(){
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

}
