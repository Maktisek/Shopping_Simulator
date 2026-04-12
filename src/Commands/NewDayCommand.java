package Commands;

import Game.GameData;

public class NewDayCommand extends Command{

    public NewDayCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public String execute() {
        if(getPlayer().bankrupt()){
            setSuccessful(false);
            return "GAME OVER - BANKRUPT";
        }
        setSuccessful(true);
        getShopManagement().setNewDays(getPlayer());
        getDayManagement().nextDay();
        return "Set new day - " + getDayManagement().getCurrentDay().getDayName() ;
    }
}
