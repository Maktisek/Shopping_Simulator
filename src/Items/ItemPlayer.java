package Items;


import java.util.LinkedList;
import java.util.Queue;


public class ItemPlayer {


    private final Item item;
    private int amount;
    private int wholeBoughtPrice;
    private final Queue<Evidence> evidences;


    public ItemPlayer(int price, String name, int initialPrice, int wholeBoughtPrice, int amount) throws WrongItemException, IllegalArgumentException {
        item = new Item(price, name, initialPrice);
        setAmount(amount);
        setWholeBoughtPrice(wholeBoughtPrice);
        this.evidences = new LinkedList<>();
    }

    public void setWholeBoughtPrice(int wholeBoughtPrice) throws WrongItemException {
        if (wholeBoughtPrice >= 0) {
            this.wholeBoughtPrice = wholeBoughtPrice;
        } else {
            throw new WrongItemException("Wrong whole bought price");
        }
    }

    public void setAmount(int amount) throws WrongItemException {
        if (amount >= 0) {
            this.amount = amount;
        } else {
            throw new WrongItemException("Wrong price");
        }
    }

    public void buyItem(int amount) throws WrongEvidenceException, WrongItemException {
        moveWithAmount(amount);
        moveWithWholeBoughtPrice(amount);
        evidences.add(new Evidence(amount, this.item.getPrice()));
    }

    private void moveWithAmount(int move) throws WrongItemException {
        int afterMove = this.amount + move;
        if (afterMove < 0) {
            throw new WrongItemException("There cannot be less than 0 items in a stack.");
        } else {
            this.amount = afterMove;
        }
    }

    private void moveWithWholeBoughtPrice(int amount) {
        this.wholeBoughtPrice += (this.item.getPrice() * amount);
    }

    public int sellItem(int amount, int NPCPrice) throws WrongItemException {
        moveWithAmount(-amount);
        int result = 0;
        while (amount != 0) {
            int[] arr = this.evidences.peek().register(amount);
            amount = arr[0];
            result = result + (arr[3] * NPCPrice);
            if (arr[1] == 0) {
                this.evidences.poll();
            }
            this.wholeBoughtPrice = this.wholeBoughtPrice - arr[2];
        }
        return result;
    }

    private void addEvidence(int amount, int price) throws WrongEvidenceException {
        this.evidences.add(new Evidence(amount, price));
    }


    public Item getItem() {
        return item;
    }
}
