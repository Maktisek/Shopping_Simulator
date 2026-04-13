package Commands.AchievementsCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;

public class ResetFreshAchievementsCommand extends Command {

    public ResetFreshAchievementsCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public CommandResult execute() {
        getAchievementManagement().clearFreshAchievements();
        return new CommandResult("Cleared FreshAchievements", CommandState.DONE);
    }
}
