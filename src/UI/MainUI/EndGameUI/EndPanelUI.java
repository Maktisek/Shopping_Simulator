package UI.MainUI.EndGameUI;

import UI.CreationUI.BackgroundPanel;
import UI.CreationUI.BarPanelUI;
import UI.Exceptions.InvalidUILoadException;

import java.awt.*;

public class EndPanelUI extends BackgroundPanel {

    public EndPanelUI(String imgFile) throws InvalidUILoadException {
        super(imgFile);
        initialization();
    }

    private void initialization() throws InvalidUILoadException {
        setLayout(new BorderLayout());
        initializeBarPanel();
    }

    private void initializeBarPanel() throws InvalidUILoadException {
        this.add(new BarPanelUI("ACHIEVEMENTS"), BorderLayout.NORTH);
    }
}
