package Items;


import java.util.LinkedList;
import java.util.Queue;


public class ItemPlayer {

    private ItemNames name;
    private int amount;
    private int wholeBoughtPrice;
    private double averageBuyPrice;
    private final Queue<Evidence> evidences;


    public ItemPlayer() {
        this.evidences = new LinkedList<>();
    }

    public void setWholeBoughtPrice(int wholeBoughtPrice) throws WrongItemException {
        if (wholeBoughtPrice >= 0) {
            this.wholeBoughtPrice = wholeBoughtPrice;
        } else {
            throw new WrongItemException("Whole bought price must be over -1");
        }
    }

    public void setAmount(int amount) throws WrongItemException {
        if (amount >= 0) {
            this.amount = amount;
        } else {
            throw new WrongItemException("Amount must be over -1");
        }
    }

    public int buyItem(int amount, int shopPrice) throws WrongEvidenceException, WrongItemException {
        moveWithAmount(amount);
        moveWithWholeBoughtPrice(amount, shopPrice);
        this.evidences.add(new Evidence(amount, shopPrice));
        updateAveragePrice();
        return amount * shopPrice;
    }

    private void updateAveragePrice() {
        if (this.amount > 0) {
            this.averageBuyPrice = (double) this.wholeBoughtPrice / this.amount;
        } else {
            this.averageBuyPrice = 0;
        }
    }

    private void moveWithAmount(int move) throws WrongItemException {
        int afterMove = this.amount + move;
        if (afterMove < 0) {
            throw new WrongItemException("There cannot be less than 0 items in a stack.");
        } else {
            this.amount = afterMove;
        }
    }

    private void moveWithWholeBoughtPrice(int amount, int shopPrice) throws WrongItemException {
        int afterMove = this.wholeBoughtPrice + (shopPrice * amount);
        if (afterMove < 0) {
            throw new WrongItemException("WholeBoughtPrice must be over -1");
        } else {
            this.wholeBoughtPrice = afterMove;
        }
    }

    public int sellItem(int amount, int NPCPrice) throws WrongItemException {
        moveWithAmount(-amount);
        int result = 0;
        while (amount != 0 && this.evidences.peek() != null) {
            int[] arr = this.evidences.peek().register(amount);
            amount = arr[0];
            result = result + (arr[3] * NPCPrice);
            if (arr[1] == 0) {
                this.evidences.poll();
            }
            this.wholeBoughtPrice = this.wholeBoughtPrice - arr[2];
        }
        updateAveragePrice();
        return result;
    }

    public int getAmount() {
        return amount;
    }

    public Queue<Evidence> getEvidences() {
        return evidences;
    }

    public double getAverageBuyPrice() {
        return averageBuyPrice;
    }

    public void setAverageBuyPrice(double averageBuyPrice) {
        this.averageBuyPrice = averageBuyPrice;
    }

    public int getWholeBoughtPrice() {
        return wholeBoughtPrice;
    }

    public ItemNames getName() {
        return name;
    }

    public void setName(ItemNames name) {
        this.name = name;
    }

    public ItemPlayer(double averageBuyPrice, int wholeBoughtPrice, int amount, ItemNames name) {
        this.evidences = new LinkedList<>();
        this.averageBuyPrice = averageBuyPrice;
        this.wholeBoughtPrice = wholeBoughtPrice;
        this.amount = amount;
        this.name = name;
    }
}
