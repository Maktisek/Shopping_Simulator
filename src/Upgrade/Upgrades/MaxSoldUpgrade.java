package Upgrade.Upgrades;

import Upgrade.UpgradeBasicType;
import Upgrade.Utilities.UpgradeType;
/**
 * This class represents an upgrade extending {@link UpgradeBasicType}.
 * <p>
 *     Upgrade type is {@link UpgradeType#SELL}
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class MaxSoldUpgrade extends UpgradeBasicType {

    public MaxSoldUpgrade() {
        setData(5);
        setPrice(50);
    }

    @Override
    public UpgradeType nameInfo() {
        return UpgradeType.SELL;
    }
}
