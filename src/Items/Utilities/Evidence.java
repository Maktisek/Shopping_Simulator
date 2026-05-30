package Items.Utilities;

import Items.Exceptions.WrongEvidenceException;

import java.io.Serializable;

/**
 * This class represents a system, which registers a purchase.
 * <p>
 * It is useful in backtracking players purchases.
 * </p>
 * {@link #price} does not stand for whole price, but for how much did one piece of that product cost.
 *
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class Evidence implements Serializable {
    private long amount;
    private long price;

    public Evidence(int amount, long price) throws WrongEvidenceException {
        setAmount(amount);
        setPrice(price);
    }

    public void setAmount(long amount) throws WrongEvidenceException {
        if (amount > 0) {
            this.amount = amount;
        } else {
            throw new WrongEvidenceException("Wrong amount inserted");
        }
    }

    public void setPrice(long price) throws WrongEvidenceException {
        if (price > 0) {
            this.price = price;
        } else {
            throw new WrongEvidenceException("Wrong amount inserted");
        }
    }

    public long getAmount() {
        return amount;
    }

    public long getPrice() {
        return price;
    }

    /**
     * This is one of the most confusing method of this whole code. Let me be clear:
     * <p>
     *     This method is used for backtracking evidence. The param {@code move} stands for
     *     how many pieces of that product does player want to sell.
     * </p>
     * <p>
     * So it means that there are only two situations that can occur:
     *  <ul>
     *       <li>{@code move} is larger or equal to {@link #amount} </li>
     *       <li>{@code move} is lower that {@link #amount}</li>
     *   </ul>
     * </p>
     * Both varieties do not differ in the process and logic, but only returns different output.
     * The output is then used somewhere else to determine if this evidence was already backtracked or is still unfinished.
     * <p>
     *     Basically the situation when {@code move} is larger or equal to {@link #amount} is the moment when the evidence is backtracked successfully.
     *     Otherwise, the evidence is not "drained" and still useful.
     * </p>
     * @param move the requested amount (how much player wants to sell of that item)
     * @return an array of length four. Every single index has to be used correctly, let me make it clear:
     * <ul>
     *     <li>{@code Index 0:} stands for how many products have to be processed afterward</li>
     *     <li>{@code Index 1:} stands for how many products were not backtracked from this evidence</li>
     *     <li>{@code Index 2:} stands for how much money were backtracked (so we can get back how much did the player spent back before)</li>
     *     <li>{@code Index 3:} stands for how many products were backtracked</li>
     * </ul>
     */
    public long[] register(long move) {
        if (move >= this.amount) {
            long soldAmount = this.amount;
            move = move - this.amount;
            long oldMoney = this.amount * this.price;
            this.amount = 0;
            return new long[]{move, this.amount, oldMoney, soldAmount};
        } else {
            this.amount = this.amount - move;
            long oldMoney = move * this.price;
            return new long[]{0, this.amount, oldMoney, move};
        }
    }
}
