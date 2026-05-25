package Commands.SaveCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;

import java.io.IOException;

/**
 * This command represents a loading system of the game from .dat file.
 * <p>
 *     {@link #getGameData()} has to be rewritten through {@link GameData#copyFromLoaded(GameData)}.
 *     So firstly a new {@link GameData} has to be initialized from the file, and then it is copied into {@link #getGameData()}
 * </p>
 * <p>
 *     If there is any issue with reading the save file then {@link CommandResult} with {@link CommandState#FAILED_ISSUE} is returned.
 * </p>
 * Otherwise {@link CommandResult} with {@link CommandState#DONE} is returned.
 * @author Matěj Pospíšil
 */
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
