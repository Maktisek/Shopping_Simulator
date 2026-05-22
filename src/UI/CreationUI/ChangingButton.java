package UI.CreationUI;

import AudioSystem.AudioType;
import Game.GameData;
import UI.Exceptions.InvalidUILoadException;
import UI.MainUI.MainUI;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ChangingButton extends CustomTitleButton {

    private final GameData gameData;

    public ChangingButton(String filePathOne, String filePathTwo,int width, int height, GameData gameData) throws InvalidUILoadException {
        super(filePathOne, filePathTwo, width, height, ButtonType.ENTER);
        this.gameData = gameData;
        initialization();
    }

    @Override
    public void setMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                System.out.println(ChangingButton.super.clicked);
                    Important.getAudioManagement().playSound("ButtonPoint", AudioType.SOUNDS, 0);
                    setCursor();
                    MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, ChangingButton.this);
                    if(parent != null) {
                        parent.setCursor();
                    }
                    ChangingButton.super.hoovered = true;
                    repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                MainUI parent = (MainUI) SwingUtilities.getAncestorOfClass(MainUI.class, ChangingButton.this);
                if(parent != null) {
                    parent.resetCursor();
                }
                ChangingButton.super.hoovered = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                ChangingButton.super.clicked = !ChangingButton.super.clicked;
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

    public void click(){
        resetCursor();
        super.img = clickedImg;
        super.clicked = true;
        repaint();
    }

    public void initialization(){
        addActionListener(e ->{
            if(clicked){
                click();
            }else {
                resetClicked();
            }
        });
    }

    public GameData getGameData() {
        return gameData;
    }
}
