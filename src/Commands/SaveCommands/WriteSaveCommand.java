package Commands.SaveCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;

import java.io.IOException;

/**
 * This command represents a system which saves the game.
 * <p>
 *     {@link #getGameData()} uses {@link GameData#writeToFile()} method to save the game.
 * </p>
 * <p>
 *     If there is any issue with writing the save file then {@link CommandResult} with {@link CommandState#FAILED_ISSUE} is returned.
 * </p>
 * Otherwise {@link CommandResult} with {@link CommandState#DONE} is returned.
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class WriteSaveCommand extends Command {

    public WriteSaveCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public CommandResult execute() {
        try {
            getGameData().writeToFile();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return new CommandResult("Oh, this should not have happened", CommandState.FAILED_ISSUE);
        }
        return new CommandResult("The game has been successfully saved", CommandState.DONE);
    }
}
