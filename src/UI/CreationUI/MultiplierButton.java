package UI.CreationUI;

import Game.GameData;
import UI.Exceptions.InvalidUILoadException;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MultiplierButton extends CustomButton {

    private int amount;
    private final GameData gameData;
    private final ArrayList<MultiplierButton> givenButtons;

    public MultiplierButton(int width, int height, int amount, GameData gameData, ArrayList<MultiplierButton> givenButtons) throws InvalidUILoadException {
        super("/MainUI/ShopUI/Multipliers/MULTIPLIER_BUTTON_"+ amount + ".png", "/MainUI/ShopUI/Multipliers/MULTIPLIER_BUTTON_CLICKED_"+ amount + ".png", width, height);
        this.amount = amount;
        this.gameData = gameData;
        this.givenButtons = givenButtons;
        initialization();
    }

    private void initialization(){
        addActionListener(e ->{
            for (MultiplierButton multiplierButton : givenButtons){
                multiplierButton.resetClicked();
            }
            super.img = clickedImg;
            super.clicked = true;
            repaint();
            this.gameData.setAmount(this.amount);
            deleteCursor();
        });
    }

    @Override
    protected void setMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if(!clicked) {
                    MultiplierButton.super.hoovered = true;
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                MultiplierButton.super.hoovered = false;
            }
        });
    }

    public void resetClicked(){
        this.clicked = false;
        this.img = super.idleImg;
        setCursor();
        repaint();
    }

    private void deleteCursor(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImg = toolkit.getImage(getClass().getResource("/MainUI/MAIN_CURSOR.png"));
        Cursor customCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "cursorName");
        this.setCursor(customCursor);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
