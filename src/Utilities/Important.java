package Utilities;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Important {

    public static Font loadFont(String filePath) {
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

    public static String formatCurrency(int amount) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#,###");
        return df.format(amount).replace(",", " ");
    }

    private static int findStartingIndex(String number) {
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) == ' ') {
                return i;
            }
        }
        return 0;
    }

    public static String parseMoney(int number) {
        String parsed = formatCurrency(number);
        int startingIndex = findStartingIndex(parsed);
        int numberOfDigits = 0;
        if(startingIndex == 0){
            return parsed;
        }
        for (int i = startingIndex + 1; i < parsed.length(); i++) {
            if (parsed.charAt(i) != ' ') {
                numberOfDigits++;
            }
        }
        return findStartOfNumber(parsed) + findDigitName(numberOfDigits);
    }

    private static String findStartOfNumber(String number){
        String result = "";
        for (int i = 0; i < number.length(); i++) {
            if(number.charAt(i) != ' ' ){
                result += number.charAt(i);
            }else {
                String next = "";
                if(number.charAt(i+1) != '0'){
                    next = ","+number.charAt(i+1);
                }
                return result + next;
            }
        }
        return "";
    }

    private static String findDigitName(int numberOfDigits) {
        switch (numberOfDigits) {
            case 3 -> {
                return "K";

            }

            case 6 -> {
                return "M";
            }

            case 9 -> {
                return "T";
            }
        }
        return "";
    }
}
