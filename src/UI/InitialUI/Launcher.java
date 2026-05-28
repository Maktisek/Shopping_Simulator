package UI.InitialUI;

import AudioSystem.AudioType;
import UI.Exceptions.InvalidUILoadException;
import UI.TitleUI.TitleScreenUI;
import Utilities.Important;

import java.awt.*;

/**
 * This class represents the "main" class, from which the game is started.
 */
public class Launcher {
    public static void main(String[] args){
        //Disables adapting to Windows percentual resolution.
        System.setProperty("sun.java2d.uiScale", "1");

        Thread loadThread = new Thread(() ->{
            Important.loadAudioManagement();
            Important.getAudioManagement().playSound("MenuOST", AudioType.MUSIC, 0, true);

            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        TitleScreenUI title = new TitleScreenUI();
                        title.makeVisible();
                    }catch (InvalidUILoadException e){
                        System.err.println(e.getMessage());
                    }
                }
            });

        });
        loadThread.start();



    }

}
