package Commands.AchievementCommands;

import Achievements.Achievement;
import Achievements.AchievementType;
import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;
import Utilities.Important;

/**
 * This command represents claiming a reward from achievement.
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class ClaimAchievementRewardCommand extends Command {

    private final Achievement achievement;

    public ClaimAchievementRewardCommand(GameData gameData, Achievement achievement) {
        super(gameData);
        this.achievement = achievement;
    }

    @Override
    public CommandResult execute() {
        int reward = achievement.returnReward();
        getPlayer().setCurrentBalance(getPlayer().getCurrentBalance() + reward);
        getDayManagement().getCurrentDay().incrementDayIncome(reward);
        getAchievementManagement().updateAchievement(AchievementType.MONEY, reward);
        return new CommandResult("You got " + Important.parseMoney(reward) + " FR as reward from \"" + achievement.getName() +"\"", CommandState.DONE);
    }
}
