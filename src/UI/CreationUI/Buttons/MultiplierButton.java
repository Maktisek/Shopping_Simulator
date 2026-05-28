package UI.CreationUI.Buttons;

import AudioSystem.AudioType;
import Game.GameData;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * This class represents a special type button extending {@link CustomBaseChangingButton}.
 * <p>
 *     When this button is clicked, the image switches, but when it is clicked it cannot be clicked again and
 *     the player has to click any of other {@link MultiplierButton} instances located in {@link #multiplierButtons}.
 * </p>
 * The connection is pretty simple. An ArrayList reference must be sent over here featuring other instances of {@link MultiplierButton}.
 * An implementation example can be found in {@link UI.MainUI.ShopUI.ShopManagement.ShopManagementNorthUI}
 * <p>
 *     {@link #amount} stands for an amount, which is set into {@link GameData} after clicking this button.
 * </p>
 * @author Matěj Pospíšil
 * @since   1.0 - (pre-release version)
 */
public class MultiplierButton extends CustomBaseChangingButton {

    private final ArrayList<MultiplierButton> multiplierButtons;
    private final int amount;
    private final GameData gameData;

    public MultiplierButton(int width, int height, int amount, GameData gameData, ArrayList<MultiplierButton> multiplierButtons) throws InvalidUILoadException {
        super(width, height, ButtonType.ENTER, "/Sprites/Multipliers/MULTIPLIER_BUTTON_" + amount + ".png", "/Sprites/Multipliers/MULTIPLIER_BUTTON_CLICKED_" + amount + ".png");
        this.multiplierButtons = multiplierButtons;
        this.amount = amount;
        this.gameData = gameData;
        initialization();
    }

    @Override
    public void setMouseListener() {
        super.setMouseListener();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if(!clicked) {
                    Important.getAudioManagement().playSound("ButtonPoint", AudioType.SOUNDS, 0, false);
                    setCursor();
                    MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, MultiplierButton.this);
                    if(parent != null) {
                        parent.setCursor();
                    }
                    MultiplierButton.super.hoovered = true;
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, MultiplierButton.this);
                if(parent != null) {
                    parent.resetCursor();
                }
                MultiplierButton.super.hoovered = false;
            }
        });
    }

    /**
     * Special method that is executed after every clicked.
     * <p>
     *     If the button is clicked all other buttons in {@link #multiplierButtons} are reset making them clickable again.
     * </p>
     * <p>
     *     Also, if the button is clicked, then {@link GameData#setAmount(int)} is executed.
     * </p>
     */
    public void click(){
        for (MultiplierButton multiplierButton : multiplierButtons){
            multiplierButton.resetClicked();
        }
        resetCursor();
        super.img = clickedImg;
        super.clicked = true;
        repaint();
        gameData.setAmount(this.amount);
    }

    /**
     * Adds method {@link #click} into action listener
     */
    public void initialization(){
        addActionListener(e ->{
            click();
        });
    }

    /**
     * Resets the button into clickable state.
     */
    public void resetClicked(){
        this.clicked = false;
        this.img = super.idleImg;
        repaint();
    }
}
