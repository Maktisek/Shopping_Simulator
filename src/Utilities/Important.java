package Utilities;

import java.util.Random;

public class Important {

    public static int priceUpdater(float price){
        Random rd = new Random();
        int percent = rd.nextInt(-40,40);
        return (int) (price + ((price/100) * percent));
    }

}
