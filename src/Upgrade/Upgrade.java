package Upgrade;

import Upgrade.Utilities.UpgradeNames;

public interface Upgrade {

    void levelUp();
    void changePrice();
    int dataInfo();
    int priceInfo();
    int levelInfo();
    UpgradeNames nameInfo();
}
