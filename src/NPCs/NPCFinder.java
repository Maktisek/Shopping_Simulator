package NPCs;

/**
 * This class is POJO
 * <p>
 *     It works as a helper for connecting NPCs with shops in {@link Game.Initialization}
 * </p>
 * @author Matěj Pospíšil
 */
public class NPCFinder {

    private String shopName;
    private NPC npc;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public NPC getNpc() {
        return npc;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }
}
