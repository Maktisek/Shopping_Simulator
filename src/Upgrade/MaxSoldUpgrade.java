package Upgrade;

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
