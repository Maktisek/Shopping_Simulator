package Upgrade.Upgrades;

import Upgrade.UpgradeBasicType;
import Upgrade.Utilities.UpgradeNames;
/**
 * This class represents an upgrade extending {@link UpgradeBasicType}.
 * <p>
 *     Upgrade type is {@link UpgradeNames#STOCK}
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class MaxStockUpgrade extends UpgradeBasicType {

    public MaxStockUpgrade() {
        setData(100);
        setPrice(150);
    }


    @Override
    public UpgradeNames nameInfo() {
        return UpgradeNames.STOCK;
    }
}
