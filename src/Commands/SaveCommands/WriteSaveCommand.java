package Commands.SaveCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;

import java.io.IOException;

public class WriteSaveCommand extends Command {

    public WriteSaveCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public CommandResult execute() {
        try {
            getGameData().writeToFile();
        } catch (IOException e) {
            return new CommandResult("Oh, this should not have happened", CommandState.FAILED_ISSUE);
        }
        return new CommandResult("The game has been successfully saved", CommandState.DONE);
    }
}
