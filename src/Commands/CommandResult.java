package Commands;

/**
 * This class is responsible for holding data about the process of a command.
 * <p>
 *     It's data are then processed - the UI can react to what happened.
 * </p>
 * @author Matěj Pospíšil
 */
public class CommandResult {

    private String message;
    private CommandState state;

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

    public void setState(CommandState state) {
        this.state = state;
    }
}
