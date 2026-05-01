package Upgrade.Upgrades;

import Upgrade.UpgradeBasicType;
import Upgrade.Utilities.UpgradeNames;

public class MaxSoldUpgrade extends UpgradeBasicType {

    public MaxSoldUpgrade() {
        setData(5);
        setPrice(50);
    }

    @Override
    public UpgradeNames nameInfo() {
        return UpgradeNames.SELL;
    }
}
