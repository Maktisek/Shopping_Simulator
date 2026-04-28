package NPCs;

import Shops.ShopNames;

public class NPCFinder {

    private ShopNames shopName;
    private NPC npc;

    public ShopNames getShopName() {
        return shopName;
    }

    public void setShopName(ShopNames shopName) {
        this.shopName = shopName;
    }

    public NPC getNpc() {
        return npc;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }
}
