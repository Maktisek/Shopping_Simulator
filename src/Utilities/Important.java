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
import java.text.DecimalFormat;

/**
 * This class is one of my oldest practise of all time.
 * <p>
 * The name "Important" comes from the fact that Utility has been already taken.
 * </p>
 * <p>
 * This class represents a class filled by static stuff. Any static fields or static methods will always be here.
 * </p>
 * There are few static fields, let me break them down:
 * <p>
 * <li>{@link #TARGET_WIDTH} represents a reference point for width resolution</li>
 * <li>{@link #TARGET_HEIGHT} represents a reference point for height resolution</li>
 * <li>{@link #audioManagement} stands for static instance of {@link AudioManagement} - because of that,
 * audios are reachable from everywhere. But be aware, it has to be firstly loaded through {@link #loadAudioManagement()}.</li>
 * </p>
 *
 * @author Matěj Pospíšil
 * @since 1.0 - (pre-release version)
 */
public class Important {

    private static final double TARGET_WIDTH = 1920.0;
    private static final double TARGET_HEIGHT = 1080.0;
    private static AudioManagement audioManagement;


    /**
     * This method loads {@link #audioManagement} with all data from json located on res/Jsons/Audios.json
     */
    public static void loadAudioManagement() {
        Gson gson = new Gson();
        try (InputStream is = GameData.class.getResourceAsStream("/Jsons/Audios.json")) {
            if (is == null) {
                throw new IllegalStateException("The path for Json: /Jsons/Audios.json is invalid and the file could not be found");
            }
            audioManagement = gson.fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), AudioManagement.class);
            audioManagement.initializeSounds();
        } catch (IOException e) {
            throw new RuntimeException("There is an mistake withing loading the Json file while loading AchievementManagement: " + e.getMessage());
        }
    }

    /**
     * This method is designed to calculate how much is the players screen scaled to the reference points.
     * <p>
     * Even though classic ration is 16:9, it is important to count on other ratios such as 4:3.
     * In some situations the scale is not same on X as on the Y. To prevent any problems the lower scale is chosen.
     * </p>
     * It is better to have smaller icons on 800x600 resolution than having them huge, so no one can see them.
     *
     * @return the chosen scale
     */
    public static double getManualScale() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        double scaleX = screen.width / TARGET_WIDTH;
        double scaleY = screen.height / TARGET_HEIGHT;
        return Math.min(scaleX, scaleY);
    }

    public static int getWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    /**
     * Calculates actual size of component on the current screen (400 pixels on 1080p monitor is different from 4K monitor - this will convert
     * 400 pixels into 800 pixels)
     * <p>
     * Use this method anytime you want to declare length in any UI sphere.
     * </p>
     *
     * @param size the length to be scaled
     * @return the scaled length
     */
    public static int calculateDimension(int size) {
        return (int) Math.round(size * getManualScale());
    }

    /**
     * This method loads a font base on the input.
     * @param filePath stands for the file path to the font
     * @return if the font has been successfully found then it is returned, but if not, then null is returned
     */
    public static Font loadFont(String filePath) {
        try {
            InputStream in = Important.class.getResourceAsStream(filePath);
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, in);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            return customFont;
        } catch (IOException | FontFormatException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * This special method represents an algorithm that parses money into readable version.
     * <p>
     *     Imagine player having {@code 1 300 000} in game currency. If this number was displayed, it would be a total mess.
     *     This is the time when this method comes in handy and would return {@code 1,3M}.
     * </p>
     * <p>
     *     If the input is negative then it is switched to its absolute value.
     * </p>
     * <p>
     *      Here is the explanation of the process:
     *      <ul>
     *          <li>Firstly the number is parsed through {@link #formatCurrency(int)} - from {@code 100000} to {@code 100 000}</li>
     *          <li>Secondly the index where the "main part" of the number ends is found - in our example it is index {@code 3}</li>
     *          <li>Thirdly if the index is {@code 0}, then it is just returned, otherwise number of digits after the "main part" are calculated.</li>
     *          <li>And then the number is returned through {@link #findStartOfNumber(String)} and {@link #findDigitName(int)}</li>
     *      </ul>
     * </p>
     * @param number the number to be parsed
     * @return the parsed number
     */
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

    /**
     * This method formats the integer input into decimal format using {@code #,###}
     * @param amount the number to be formated
     * @return the formated number
     */
    private static String formatCurrency(int amount) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(amount).replace(",", " ");
    }

    /**
     * Finds the first index where "main part" of a number formated through {@link #formatCurrency(int)} ends.
     * <p>
     *     For number {@code 100 000} it is index {@code 3}.
     * </p>
     * @param number the number in string form to be the index found in
     * @return the found index, if {@code 0} is returned, then there is no second part of the number
     */
    private static int findStartingIndex(String number) {
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) == ' ') {
                return i;
            }
        }
        return 0;
    }

    /**
     * This method create shorter form of a desired number, which has been already formated through {@link #formatCurrency(int)}.
     * <p>
     *     {@code 13 789 000} returns {@code 13,7}.
     * </p>
     * @param number the number to be shortened
     * @return the shortened number
     */
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

    /**
     * This method determines, which suffix is suitable for a number based on its number of digits in the second part of the number.
     * @param numberOfDigits the number of digits, from which the suffix will be decided
     * @return the suffix
     */
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

    /**
     * Splits string by "\n"
     * @param text the string to be split
     * @return array of strings
     */
    public static String[] decodeString(String text) {
        return text.split("\n");
    }

    /**
     * This method represents an algorithm, which formats a string into a restaurant menu looking form.
     * <p>
     *     The string given must contain {@code :}, because that is the character, which divides text on left and on right.
     * </p>
     * <p>
     *     The process is simple:
     *     <ul>
     *         <li>It calculates how long is the data input without {@code :} and based on that it calculates how many dots to be inserted
     *         in order to achieve desired length.</li>
     *     </ul>
     * </p>
     * @param text the text to be formatted
     * @param length the desired length of the output
     * @return the formatted text
     */
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

    /**
     * This method initializes an instance of {@link JScrollPane}.
     * @param panel is the panel, which is added into the scroll pane
     * @param increment stands for the sensitivity of the scroller
     * @return the initialized instance of {@link JScrollPane}
     * @throws InvalidUILoadException if there is any problem while initializing the instance of {@link JScrollPane}
     */
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

    public static AudioManagement getAudioManagement() {
        return audioManagement;
    }
}

