package Commands;

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
