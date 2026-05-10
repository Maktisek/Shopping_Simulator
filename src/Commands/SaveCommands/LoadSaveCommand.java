package Commands.SaveCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;

import java.io.IOException;

public class LoadSaveCommand extends Command {

    public LoadSaveCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public CommandResult execute() {
        try {
            GameData newGameData = GameData.readFromFile();
            this.getGameData().copyFromLoaded(newGameData);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return new CommandResult(e.getMessage(), CommandState.FAILED_ISSUE);
        }
        return new CommandResult("The game has been loaded successfully", CommandState.DONE);
    }
}
