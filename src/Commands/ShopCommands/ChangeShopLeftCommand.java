package Commands.ShopCommands;

import Commands.Command;
import Game.GameData;

public class ChangeShopLeftCommand extends Command {

    public ChangeShopLeftCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public String execute() {
        if (!getShopManagement().isSwitchLeft()){
            setSuccessful(false);
            return "There is no other shop available";
        }

        if(!getShopManagement().boughtLeft()){
            setSuccessful(false);
        }
    }
}
