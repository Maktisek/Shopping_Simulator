package Shops;

import Items.*;
import NPCs.NPC;
import Player.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShopTest {


    private Shop shop;


    @Before
    public void setUp() throws Exception {
        this.shop = new Shop();
        shop.setName(ShopNames.Test);
        shop.setShopKey(new ShopKey());
        shop.setNpc(new NPC());
        shop.getNpc().setConvenienceWeight(10);
        shop.getNpc().setQuantityWeight(10);

        shop.setItems(new ItemShop[]{
                new ItemShop(new Item(ItemNames.Apple, 12, 10), 90, 0, 1),
                new ItemShop(new Item(ItemNames.Banana, 8, 9), 100, 0, 1),
                new ItemShop(new Item(ItemNames.Bread, 16, 14), 80, 0, 1),
                new ItemShop(new Item(ItemNames.Whisky, 89, 85), 40, 0, 1),
                new ItemShop(new Item(ItemNames.Chocolate, 45, 45), 60, 0, 1)});
    }

    @Test
    public void testShopKey() throws Exception {
        assertFalse(shop.isAccessible());
        shop.getShopKey().setUnlocked(true);
        assertTrue(shop.isAccessible());
    }

    @Test
    public void testNPC() throws Exception {
        shop.getNpc().setItems(new Item[5]);
        assertNull(shop.getNpc().getItems()[2]);
        shop.initializeNPC(new Player(), new Shop());
        assertEquals(ItemNames.Bread, shop.getNpc().getItems()[2].getName());
        shop.getNpc().setDemand(new Item[2]);
        assertNull(shop.getNpc().getDemand()[1]);
        shop.newDay(new Player(0, 0, new ItemPlayer[]{
                new ItemPlayer(12, 1000, 12000, ItemNames.Apple),
                new ItemPlayer(10, 1000, 13, ItemNames.Banana),
                new ItemPlayer(15, 200, 3000, ItemNames.Bread),
                new ItemPlayer(80, 10, 800, ItemNames.Whisky),
                new ItemPlayer(40, 50, 2000, ItemNames.Chocolate)}));
        assertNotNull(shop.getNpc().getDemand()[1]);
    }

    @Test
    public void testBuy() throws Exception {
        assertEquals(1,1, shop.getItems()[0].getPenalization());
        shop.buyItem(0, 10);
        assertEquals(1.2,1.2, shop.getItems()[0].getPenalization());
        shop.getNpc().setDemand(new Item[2]);
        shop.getNpc().setItems(new Item[5]);
        shop.initializeNPC(new Player(), new Shop());
        shop.newDay(new Player(0, 0, new ItemPlayer[]{
                new ItemPlayer(12, 1000, 12000, ItemNames.Apple),
                new ItemPlayer(10, 1000, 13, ItemNames.Banana),
                new ItemPlayer(15, 200, 3000, ItemNames.Bread),
                new ItemPlayer(80, 10, 800, ItemNames.Whisky),
                new ItemPlayer(40, 50, 2000, ItemNames.Chocolate)}));

        assertEquals(1.1,1.1, shop.getItems()[0].getPenalization());
    }
}