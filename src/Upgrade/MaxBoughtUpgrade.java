package Upgrade;

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
