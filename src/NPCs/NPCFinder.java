package NPCs;

/**
 * This class is POJO
 * <p>
 *     It works as a helper for connecting NPCs with shops in {@link Game.Initialization}
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class NPCFinder {

    @SuppressWarnings("unused")
    private String shopName;
    private NPC npc;

    public String getShopName() {
        return shopName;
    }

    public NPC getNpc() {
        return npc;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }
}
