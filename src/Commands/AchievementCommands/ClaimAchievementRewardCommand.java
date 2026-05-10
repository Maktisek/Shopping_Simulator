package Commands.AchievementCommands;

import Achievements.Achievement;
import Achievements.AchievementTypes;
import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;

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
        getAchievementManagement().updateAchievement(AchievementTypes.MONEY, reward);
        return new CommandResult("You got " + reward + " FR as reward from \"" + achievement.getName() +"\"", CommandState.DONE);
    }
}
