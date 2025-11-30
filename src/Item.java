import java.util.LinkedList;
import java.util.Queue;

public class Item {


    private int price;
    private ItemNames  name;
    private final int initialPrice;

    public Item(int price, String name, int initialPrice) throws WrongItemException, IllegalArgumentException{
        setPrice(price);
        setName(name);
        this.initialPrice = initialPrice;
    }

    public void setPrice(int price) throws WrongItemException{
        if(price > 0){
            this.price = price;
        }else {
            throw new WrongItemException("Wrong price");
        }
    }


    public void setName(String name) throws IllegalArgumentException{
       this.name = ItemNames.valueOf(name);
    }

    public int getPrice() {
        return price;
    }

    public int getInitialPrice() {
        return initialPrice;
    }

    public ItemNames getName() {
        return name;
    }

    public String intoCSV(){
        return this.price+">"+this.name+">"+this.initialPrice;
    }




}
