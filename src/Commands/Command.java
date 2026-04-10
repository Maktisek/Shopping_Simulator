package Commands;

import Game.GameData;

public abstract class Command {

    private boolean successful;
    private GameData gameData;

    public Command(GameData gameData) {
        this.successful = true;
        this.gameData = gameData;
    }

    public abstract String execute();

    public boolean isSuccessful(){
        return successful;
    }
}
