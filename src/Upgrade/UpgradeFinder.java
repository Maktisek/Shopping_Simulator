package Upgrade;

import Upgrade.Utilities.UpgradeType;

/**
 * It works as a helper for all in-game upgrades initialization.
 * <p>
 *     Since all upgrades are stored in HashMap in {@link UpgradeManagement}, key and a value are needed.
 *     This class provides safe and easy load because of {@link #key} and {@link #value}.
 * </p>
 * @author Matěj Pospíšil
 * @since 1.0 - (pre-release version)
 */
public class UpgradeFinder {

    @SuppressWarnings("unused")
    private UpgradeType key;
    @SuppressWarnings("unused")
    private Upgrade value;

    public void finishValue() {
        this.value.setNameInfo(key);
    }

    public UpgradeType getKey() {
        return key;
    }

    public Upgrade getValue() {
        return value;
    }
}
