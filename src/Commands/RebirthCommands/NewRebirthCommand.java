package Commands.RebirthCommands;

import Commands.Command;
import Game.GameData;

public class NewRebirthCommand extends Command {

    public NewRebirthCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public String execute() {
        int price = getUpgradeManagement().getRebirth().getPrice();
        if(!getPlayer().canBuy(price)){
            setSuccessful(false);
            return "Not enough money";
        }

        getPlayer().setCurrentBalance(getPlayer().getCurrentBalance() - price);
        getUpgradeManagement().setNewRebirth();
        return "New rebirth - level: " + getUpgradeManagement().getRebirth().getAmount();
    }
}
