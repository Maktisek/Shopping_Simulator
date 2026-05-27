package Commands;

/**
 * This enum represents different command states.
 * <p>
 *     {@link CommandResult} has its own state represented by this enum.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public enum CommandState {

    DONE, FAILED_ISSUE, FAILED_BUY, FAILED_END
}
