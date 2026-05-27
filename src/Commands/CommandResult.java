package Commands;

/**
 * This class is responsible for holding data about the process of a command.
 * <p>
 *     It's data are then processed - the UI can react to what happened.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class CommandResult {

    private String message;
    private final CommandState state;

    public CommandResult(String message, CommandState state) {
        this.message = message;
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommandState getState() {
        return state;
    }
}
