package UI.CreationUI;

import Game.GameData;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MultiplierButton extends CustomTitleButton {

    private int amount;
    private final GameData gameData;
    private final ArrayList<MultiplierButton> givenButtons;

    public MultiplierButton(int width, int height, int amount, GameData gameData, ArrayList<MultiplierButton> givenButtons) throws InvalidUILoadException {
        super("/Sprites/Multipliers/MULTIPLIER_BUTTON_" + amount + ".png", "/Sprites/Multipliers/MULTIPLIER_BUTTON_CLICKED_" + amount + ".png", width, height, ButtonType.ENTER);
        this.amount = amount;
        this.gameData = gameData;
        this.givenButtons = givenButtons;
        initialization();
    }


    private void initialization(){
        addActionListener(e ->{
            click();
        });
    }

    public void click(){
        for (MultiplierButton multiplierButton : givenButtons){
            multiplierButton.resetClicked();
        }
        resetCursor();
        super.img = clickedImg;
        super.clicked = true;
        repaint();
        this.gameData.setAmount(this.amount);
    }

    @Override
    public void setMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if(!clicked) {
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

    public void resetClicked(){
        this.clicked = false;
        this.img = super.idleImg;
        repaint();
    }

    public void resetCursor(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImg = toolkit.getImage(getClass().getResource("/Sprites/MainSprites/MAIN_CURSOR.png"));
        Cursor customCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "cursorName");
        super.setCursor(customCursor);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
