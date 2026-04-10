package Commands;

import Game.GameData;

public class BuyProductCommand extends Command{

    public BuyProductCommand(GameData gameData) {
        super(gameData);
    }

    @Override
    public String execute() {
        return "";
    }
}
