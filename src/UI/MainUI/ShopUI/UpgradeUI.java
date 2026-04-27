package UI.MainUI.ShopUI;

import UI.CreationUI.BackgroundPanel;
import UI.InvalidUILoadException;
import Upgrade.Upgrade;

import javax.swing.*;

public class UpgradeUI extends BackgroundPanel {

    private Upgrade upgrade;

    public UpgradeUI(String imgFile, Upgrade upgrade) throws InvalidUILoadException {
        super(imgFile);
        this.upgrade = upgrade;
    }

    private void initialize(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    private void initializeNameLabel(){



    }
}
