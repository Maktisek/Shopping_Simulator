package UI.CreationUI.Buttons;

import AudioSystem.AudioType;
import Game.GameData;
import UI.Exceptions.InvalidUILoadException;
import Utilities.Important;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChangingButton extends CustomBaseChangingButton {

    private final GameData gameData;

    public ChangingButton(String filePathOne, String filePathTwo,int width, int height, GameData gameData) throws InvalidUILoadException {
        super(width, height, ButtonType.ENTER, filePathOne, filePathTwo);
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
                    Important.getAudioManagement().playSound("ButtonPoint", AudioType.SOUNDS, 0);
                    setCursor();
                    ChangingButton.super.hoovered = true;
                    repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                resetCursor();
                ChangingButton.super.hoovered = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                ChangingButton.super.clicked = !ChangingButton.super.clicked;
            }
        });
    }

    public void changeToFirst(){
        this.clicked = false;
        this.img = super.idleImg;
        repaint();
    }

    public void changeToSecond(){
        super.img = clickedImg;
        super.clicked = true;
        repaint();
    }

    public void initialization(){
        addActionListener(e ->{
            if(clicked){
                changeToSecond();
            }else {
                changeToFirst();
            }
        });
    }

    @Override
    public int calculateOffset() {
        if (hoovered) {
            return 0;
        }
        return 5;
    }

    public GameData getGameData() {
        return gameData;
    }
}
