package UI.InitialUI;

import AudioSystem.AudioType;
import UI.Exceptions.InvalidUILoadException;
import UI.TitleUI.TitleScreenUI;
import Utilities.Important;

import java.awt.*;

public class Launcher {
    public static void main(String[] args){
        System.setProperty("sun.java2d.uiScale", "1");

        Thread loadThread = new Thread(() ->{
            Important.loadAudioManagement();
            Important.getAudioManagement().playSound("MenuOST", AudioType.MUSIC, 0);

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
