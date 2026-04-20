package UI.MainUI;

import UI.BackgroundPanel;
import UI.InvalidUILoadException;

public class IssueBuyDialogUI extends BackgroundPanel {

    private final String message;

    public IssueBuyDialogUI(String imgFile, String message) throws InvalidUILoadException {
        super(imgFile);
        this.message = message;

        initialize();
    }

    private void initialize() throws InvalidUILoadException{
        initializeLabel();
    }

    private void initializeLabel() throws InvalidUILoadException{

    }
}
