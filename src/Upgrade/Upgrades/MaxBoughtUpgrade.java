package Upgrade.Upgrades;

import Upgrade.UpgradeBasicType;
import Upgrade.Utilities.UpgradeNames;

/**
 * This class represents an upgrade extending {@link UpgradeBasicType}.
 * <p>
 *     Upgrade type is {@link UpgradeNames#BUY}
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class MaxBoughtUpgrade extends UpgradeBasicType {

    public MaxBoughtUpgrade() {
        setData(10);
        setPrice(50);
    }

    @Override
    public UpgradeNames nameInfo() {
        return UpgradeNames.BUY;
    }
}
