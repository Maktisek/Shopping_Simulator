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
            setGameData(GameData.readFromFile());
        } catch (IOException e) {
            return new CommandResult(e.getMessage(), CommandState.FAILED_BUY);
        }
        return new CommandResult("The game has been loaded successfully", CommandState.DONE);
    }
}
