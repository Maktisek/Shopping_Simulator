package Commands;

import Game.GameData;

public class ResetFreshAchievementsCommand extends Command{

    public ResetFreshAchievementsCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public String execute() {
        setSuccessful(true);
        getAchievementManagement().clearFreshAchievements();
        return "Cleared FreshAchievements";

    }
}
