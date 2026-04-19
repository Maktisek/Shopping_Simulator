package Utilities;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Important {

    public static Font loadFont(String filePath){
        try {
            InputStream in = Important.class.getResourceAsStream(filePath);
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, in);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            return customFont;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
