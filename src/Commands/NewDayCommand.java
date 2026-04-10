package Commands;

import Game.GameData;

public class NewDayCommand extends Command{

    public NewDayCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public String execute() {
        getShopManagement().setNewDays(getPlayer());
        getDayManagement().nextDay();
        return "Set new day - " + getDayManagement().getCurrentDay().getDayName() ;
    }
}
