package Items;

public class Evidence {
    private int amount;
    private int price;

    public Evidence(int amount, int price) throws WrongEvidenceException {
        setAmount(amount);
        setPrice(price);
    }

    public void setAmount(int amount) throws WrongEvidenceException {
        if (amount > 0) {
            this.amount = amount;
        } else {
            throw new WrongEvidenceException("Wrong amount inserted");
        }
    }

    public void setPrice(int price) throws WrongEvidenceException {
        if (price > 0) {
            this.price = price;
        } else {
            throw new WrongEvidenceException("Wrong amount inserted");
        }
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public int[] register(int move) {
        if (move >= this.amount) {
            int soldAmount = this.amount;
            move = move - this.amount;
            int oldMoney = this.amount * this.price;
            this.amount = 0;
            return new int[]{move, this.amount, oldMoney, soldAmount};
        } else {
            this.amount = this.amount - move;
            int oldMoney = move * this.price;
            return new int[]{0, this.amount, oldMoney, move};
        }
    }

    @Override
    public String toString() {
        return "Evidence{" +
                "amount=" + amount +
                ", price=" + price +
                '}';
    }
}
