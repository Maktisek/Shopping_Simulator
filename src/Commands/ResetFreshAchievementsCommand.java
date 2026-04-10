package Commands;

import Game.GameData;

public class ResetFreshAchievementsCommand extends Command{

    public ResetFreshAchievementsCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public String execute() {
        getAchievementManagement().clearFreshAchievements();
        return "Cleared FreshAchievements";

    }
}
