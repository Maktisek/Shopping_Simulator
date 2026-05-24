package Utilities;

import AudioSystem.AudioManagement;
import Game.GameData;
import UI.Exceptions.InvalidUILoadException;
import UI.CreationUI.Scrollers.CustomScrollBarUI;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Important {

    private static final double TARGET_WIDTH = 1920.0;
    private static final double TARGET_HEIGHT = 1080.0;
    private static AudioManagement audioManagement;


    public static void loadAudioManagement() {
        Gson gson = new Gson();
        try (InputStream is = GameData.class.getResourceAsStream("/Jsons/Audios.json")) {
            if (is == null) {
                throw new IllegalStateException("The path for Json: /Jsons/Audios.json is invalid and the file could not be found");
            }
            audioManagement = gson.fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), AudioManagement.class);
            audioManagement.initializeSounds();
            } catch(IOException e){
                throw new RuntimeException("There is an mistake withing loading the Json file while loading AchievementManagement: " + e.getMessage());
        }
    }

    public static double getManualScale() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        double scaleX = screen.width / TARGET_WIDTH;
        double scaleY = screen.height / TARGET_HEIGHT;
        return Math.min(scaleX, scaleY);
    }

    public static int getWidth(){
       return Toolkit.getDefaultToolkit().getScreenSize().width;
    }


    public static int calculateDimension(int size){
        return (int) Math.round(size * getManualScale());
    }

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

    private static String formatCurrency(int amount) {
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
        if (number < 0) {
            number = number * -1;
        }
        String parsed = formatCurrency(number);
        int startingIndex = findStartingIndex(parsed);
        int numberOfDigits = 0;
        if (startingIndex == 0) {
            return parsed;
        }
        for (int i = startingIndex + 1; i < parsed.length(); i++) {
            if (parsed.charAt(i) != ' ') {
                numberOfDigits++;
            }
        }
        return findStartOfNumber(parsed) + findDigitName(numberOfDigits);
    }

    private static String findStartOfNumber(String number) {
        String result = "";
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) != ' ') {
                result += number.charAt(i);
            } else {
                String next = "";
                if (number.charAt(i + 1) != '0') {
                    next = "," + number.charAt(i + 1);
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

    public static String[] decodeString(String text) {
        String[] data = text.split("\n");
        return data;
    }

    public static String insertDots(String text, int length) {
        int currentLength = 0;
        String[] data = text.split(":");
        if (data.length != 1) {
            for (String s : data) {
                currentLength += s.length();
            }
            length = length - currentLength;
            return data[0] + ". ".repeat(length) + data[1];
        }
        return null;
    }

    public static JScrollPane initializeScrollPane(JPanel panel, int increment) throws InvalidUILoadException {
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(increment);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        return scrollPane;
    }

    public static int choseOver(int over, int calculation){
       return Math.max(over, calculation);
    }

    public static AudioManagement getAudioManagement() {
        return audioManagement;
    }
}

