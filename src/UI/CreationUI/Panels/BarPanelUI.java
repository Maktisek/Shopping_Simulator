package UI.CreationUI.Panels;

import AudioSystem.AudioType;
import Game.GameData;
import UI.CreationUI.Buttons.ButtonType;
import UI.CreationUI.Buttons.CustomButton;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents an implementation of {@link BackgroundPanel}.
 * <p>
 *     It is commonly used in informational part of UI as a "bar" on top of the screen.
 * </p>
 * <p>
 *     Base on {@link #type} the bar is chosen.
 * </p>
 * <p>
 *     Basically just an exit button is being initialized here, but since it is an inheritor of {@link BackgroundPanel},
 *     any other component can be added after the initialization process is finished.
 * </p>
 * <p>
 *     An instance of {@link GameData} must be sent here in order to reach the name of the current shop.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class BarPanelUI extends BackgroundPanel {

    private final GameData gameData;
    private final String type;

    public BarPanelUI(String type, GameData gameData) throws InvalidUILoadException {
        super("/Sprites/BarSprites/" + type + "_UI_BAR.png");
        this.gameData = gameData;
        this.type = type;
        initialize();
    }

    private void initialize() throws InvalidUILoadException {
        initializeDimensions();

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(Important.calculateDimension(5), Important.calculateDimension(18), Important.calculateDimension(10), Important.calculateDimension(10)));

        initializeExitButton();
    }

    private void initializeExitButton() throws InvalidUILoadException {
        CustomButton customButton = new CustomButton("/Sprites/ButtonSprites/ESCAPE_BUTTON.png", Important.calculateDimension(100), Important.calculateDimension(100), ButtonType.EXIT);
        customButton.addActionListener(e -> {
            Important.getAudioManagement().resumeSound(gameData.getShopManagement().getCurrentShop().getName(), AudioType.MUSIC, false);
            Important.getAudioManagement().stopSound(type+"OST",AudioType.MUSIC);
            MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, this);
            parent.switchPanel("Shop");
        });
        add(customButton);
    }

    private void initializeDimensions() {
        Dimension dimension = new Dimension(Important.calculateDimension(Important.getWidth()), Important.calculateDimension(135));
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
    }

}
