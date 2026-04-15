package UI;

import java.awt.*;

public class Launcher {
    public static void main(String[] args){

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                TitleScreenUI title = new TitleScreenUI();
                title.show();
            }
        });
    }

}
