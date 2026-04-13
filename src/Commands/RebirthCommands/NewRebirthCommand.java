package Commands.RebirthCommands;

import Commands.Command;
import Commands.CommandResult;
import Commands.CommandState;
import Game.GameData;

public class NewRebirthCommand extends Command {

    public NewRebirthCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public CommandResult execute() {
        int price = getUpgradeManagement().getRebirth().getPrice();
        if(!getPlayer().canBuy(price)){
            return new CommandResult("Not enough money", CommandState.FAILED_ISSUE);
        }

        getPlayer().setCurrentBalance(getPlayer().getCurrentBalance() - price);
        getUpgradeManagement().setNewRebirth();
        return new CommandResult("New rebirth - level: " + getUpgradeManagement().getRebirth().getAmount(), CommandState.DONE);
    }
}
