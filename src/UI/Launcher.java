package UI;

import UI.TitleUI.TitleScreenUI;

import java.awt.*;

public class Launcher {
    public static void main(String[] args){

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    TitleScreenUI title = new TitleScreenUI();
                    title.show();
                }catch (InvalidUILoadException e){
                    System.err.println(e.getMessage());
                }
            }
        });
    }

}
