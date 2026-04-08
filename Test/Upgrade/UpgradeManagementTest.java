package Upgrade;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UpgradeManagementTest {

    UpgradeManagement upgradeSystem;

    @Before
    public void setUp() throws Exception {
        this.upgradeSystem = new UpgradeManagement();
    }

    @Test
    public void levelUpgradeTest(){
        assertEquals(500, upgradeSystem.getUpgradeData(UpgradeNames.STOCK));
        upgradeSystem.levelUpUpgrade(UpgradeNames.STOCK);
        assertEquals(520, upgradeSystem.getUpgradeData(UpgradeNames.STOCK));
        assertEquals(172, upgradeSystem.getUpgradePrice(UpgradeNames.STOCK));

        upgradeSystem.newRebirth();

        assertEquals(572, upgradeSystem.getUpgradeData(UpgradeNames.STOCK));

        upgradeSystem.newRebirth();

        assertEquals(624, upgradeSystem.getUpgradeData(UpgradeNames.STOCK));
    }
}