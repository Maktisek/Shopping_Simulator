package UI.CreationUI;

import AudioSystem.AudioType;
import Game.GameData;
import UI.Exceptions.InvalidUILoadException;
import Utilities.Important;

import javax.swing.*;
import java.awt.*;

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
            System.out.println("DEBUG: Přehrávám zvuk pro typ: " + type + " na objektu: " + this.hashCode());
            switch (type) {
                case EXIT -> GameData.audioManagement.playSound("ExitClick", AudioType.SOUNDS, 0);
                case ENTER -> {
                    GameData.audioManagement.playSound("EnterClick", AudioType.SOUNDS, 0);
                }

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

    public abstract void setMouseListener();

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
