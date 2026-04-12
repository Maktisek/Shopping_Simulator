package Commands;

import Game.GameData;
import Shops.Shop;
import Shops.ShopNames;

public class BuyShopCommand extends Command{

    private ShopNames name;

    public BuyShopCommand(GameData gameData, ShopNames name) {
        super(gameData);
        this.name = name;
    }

    @Override
    public String execute() {
        Shop foundShop = getShopManagement().findShop(name);
        if(foundShop == null){
            setSuccessful(false);
            return "Shop " + name + " does not exist";
        }

        if(foundShop.getShopKey().isUnlocked()){
            setSuccessful(false);
            return "Shop " + name + " is already unlocked";
        }

        if(!getPlayer().canBuy(foundShop.getShopKey().getPrice())){
            setSuccessful(false);
            return "Not enough money";
        }

        foundShop.getShopKey().setUnlocked(true);
        getPlayer().setCurrentBalance(getPlayer().getCurrentBalance() - foundShop.getShopKey().getPrice());
        return "Bought new shop: " + name;
    }
}
