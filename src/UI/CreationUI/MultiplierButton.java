package UI.CreationUI;

import AudioSystem.AudioType;
import Game.GameData;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MultiplierButton extends ChangingButton {

    private final ArrayList<MultiplierButton> multiplierButtons;
    private final int amount;

    public MultiplierButton(int width, int height, int amount, GameData gameData, ArrayList<MultiplierButton> multiplierButtons) throws InvalidUILoadException {
        super("/Sprites/Multipliers/MULTIPLIER_BUTTON_" + amount + ".png", "/Sprites/Multipliers/MULTIPLIER_BUTTON_CLICKED_" + amount + ".png",width, height,gameData);
        this.multiplierButtons = multiplierButtons;
        this.amount = amount;
        initialization();
    }

    @Override
    public void setMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if(!clicked) {
                    Important.getAudioManagement().playSound("ButtonPoint", AudioType.SOUNDS, 0);
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

    public void click(){
        for (ChangingButton changingButton : multiplierButtons){
            changingButton.resetClicked();
        }
        resetCursor();
        super.img = clickedImg;
        super.clicked = true;
        repaint();
        super.getGameData().setAmount(this.amount);
    }

    public void initialization(){
        addActionListener(e ->{
            click();
        });
    }


}
