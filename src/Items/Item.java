package Items;
/**
 * This interface represents an item.
 * <p>
 *     The game features more varieties of items and not every single of one need to implement this interface.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public interface Item {
    /**
     * This method is used in order to get an instance of {@link ItemBase} from that item. It
     * <p>
     *     Every single class implementing this interface should have {@link ItemBase} as a field.
     * </p>
     * @return the instance of {@link ItemBase}
     */
    ItemBase getItemBase();

    /**
     * This method represent my own {@code toString()} method.
     * @return the information about the object.
     */
    String specification();
}
