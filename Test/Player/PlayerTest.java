package Player;

import Items.Item;
import Items.ItemNames;
import Items.ItemShop;
import Shops.Shop;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {

    Player player;
    Shop shop;

    @Before
    public void setUp() throws Exception {
        player = new Player();
        shop = new Shop();
//        shop.setItems(new ItemShop[]{
//                new ItemShop(new Item(ItemNames.Apple, 12, 10), 90, 0, 1),
//                new ItemShop(new Item(ItemNames.Banana, 8, 9), 100, 0, 1),
//                new ItemShop(new Item(ItemNames.Bread, 16, 14), 80, 0, 1),
//                new ItemShop(new Item(ItemNames.Whisky, 89, 85), 40, 0, 1),
//                new ItemShop(new Item(ItemNames.Chocolate, 45, 45), 60, 0, 1)});
//        ArrayList<Shop> shops = new ArrayList<>();
//        shops.add(shop);
//        player.loadItems(shops);
//        player.setCurrentBalance(0);
//        player.setAllTimeBalance(0);
//    }

    }
    @Test
    public void findItemTest() {
        assertEquals(ItemNames.Bread, player.findItem(ItemNames.Bread).getName());
    }

    @Test
    public void buyAndSellItemTest() throws Exception {
        player.setCurrentBalance(200);
        player.setAllTimeBalance(200);

//        player.buyItem(ItemNames.Apple, 10, shop.findItem(ItemNames.Apple).getItem().getCurrentPrice());

        assertEquals(80, player.getCurrentBalance());

        assertEquals(10, player.findItem(ItemNames.Apple).getAmount());
        assertEquals(1, player.findItem(ItemNames.Apple).getEvidences().size());
        assertEquals(120, player.findItem(ItemNames.Apple).getWholeBoughtPrice());
        assertEquals(12, 12, player.findItem(ItemNames.Apple).getAverageBuyPrice());

        shop.findItem(ItemNames.Apple).updatePrice();

//        player.buyItem(ItemNames.Apple, 1, shop.findItem(ItemNames.Apple).getItem().getCurrentPrice());
//        assertNotEquals(80, player.getCurrentBalance());

//        assertEquals(2, player.findItem(ItemNames.Apple).getEvidences().size());

        player.sellItem(ItemNames.Apple, 1, 10);

        assertEquals(90, player.getCurrentBalance());
        assertEquals(1, player.findItem(ItemNames.Apple).getEvidences().size());
        assertEquals(9, player.findItem(ItemNames.Apple).getAmount());
        assertEquals(108, player.findItem(ItemNames.Apple).getWholeBoughtPrice());

        player.sellItem(ItemNames.Apple, 9, 15);

        assertEquals(0, player.findItem(ItemNames.Apple).getEvidences().size());
        assertEquals(0, player.findItem(ItemNames.Apple).getAmount());
        assertEquals(0, player.findItem(ItemNames.Apple).getWholeBoughtPrice());
        assertEquals(225, player.getCurrentBalance());
        assertEquals(345, player.getAllTimeBalance());
    }
}