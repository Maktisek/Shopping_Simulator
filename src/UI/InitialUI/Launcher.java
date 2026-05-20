package UI.InitialUI;

import UI.Exceptions.InvalidUILoadException;
import UI.TitleUI.TitleScreenUI;

import java.awt.*;

public class Launcher {
    public static void main(String[] args){
        System.setProperty("sun.java2d.uiScale", "1");

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    TitleScreenUI title = new TitleScreenUI();
                    title.initializeFirstGameData();
                    title.makeVisible();
                }catch (InvalidUILoadException e){
                    System.err.println(e.getMessage());
                }
            }
        });
    }

}
