package Upgrade;

public class MaxStockUpgrade extends UpgradeBasicType{

    public MaxStockUpgrade() {
        setData(500);
        setPrice(150);
    }


    @Override
    public UpgradeNames nameInfo() {
        return UpgradeNames.STOCK;
    }
}
