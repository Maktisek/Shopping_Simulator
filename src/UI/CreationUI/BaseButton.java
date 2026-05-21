package UI.CreationUI;

import AudioSystem.Audio;
import AudioSystem.AudioType;
import Game.GameData;
import UI.Exceptions.InvalidUILoadException;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class BaseButton extends JButton {

    protected boolean hoovered;
    protected boolean clicked;

    public BaseButton(int width, int height, ButtonType type) {
        setSizeOfButton(width, height);
        initializeSound(type);
        setMouseListener();
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }

    public BaseButton() {
    }

    private void initializeSound(ButtonType type) {
        this.addActionListener(e -> {
            switch (type) {
                case EXIT -> Important.getAudioManagement().playSound("ExitClick", AudioType.SOUNDS, 0);
                case ENTER -> Important.getAudioManagement().playSound("EnterClick", AudioType.SOUNDS, 0);
            }
        });
    }


    public abstract void setImages() throws InvalidUILoadException;

    public void setSizeOfButton(int width, int height) {
        Dimension dimension = new Dimension(Important.calculateDimension(width), Important.calculateDimension(height));
        setMaximumSize(dimension);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
    }

    public void setMouseListener(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                Important.getAudioManagement().playSound("ButtonPoint", AudioType.SOUNDS, 0);
            }
        });
    }

    public int calculateOffset() {
        if (clicked) {
            return 5;
        }
        if (hoovered) {
            return 0;
        }
        return 5;
    }
}
