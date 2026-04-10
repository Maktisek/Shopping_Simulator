package Commands;

public abstract class Command {

    private boolean successful;

    public Command(boolean successful) {
        this.successful = successful;
    }

    public abstract String execute();

    public boolean isSuccessful(){
        return successful;
    }
}
