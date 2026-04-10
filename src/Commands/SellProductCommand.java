package Commands;

import Game.GameData;

public class SellProductCommand extends Command{


    public SellProductCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public String execute() {
        return "";
    }
}
