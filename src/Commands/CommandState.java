package Commands;

/**
 * This enum represents different command states.
 * <p>
 *     {@link CommandResult} has its own state represented by this enum.
 * </p>
 * @author Matěj Pospíšil
 */
public enum CommandState {

    DONE, FAILED_ISSUE, FAILED_BUY, FAILED_END
}
