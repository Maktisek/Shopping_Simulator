package UI.MainUI;


import Items.ItemShop;
import UI.BackgroundPanel;
import UI.InvalidUILoadException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class ItemUI extends BackgroundPanel {

    private ItemShop item;
    private Font currentFont;

    public ItemUI(String imgFile, ItemShop item) throws InvalidUILoadException {
        super(imgFile);
        this.item = item;

        Dimension dimension = new Dimension(180, 180);
        setMinimumSize(new Dimension(dimension));
        setPreferredSize(new Dimension(dimension));
        setMaximumSize(new Dimension(dimension));

        initialize();
    }

    private void initialize(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(15, 0, 40, 0));
        loadFont();
        initializeLabel();
    }

    private void initializeLabel(){
        StrokeLabel label = new StrokeLabel(this.item.getItem().getName().toString());
        label.setFont(currentFont);
        label.setOpaque(false);
        label.setFont(this.currentFont.deriveFont(Font.BOLD,14.0f));
        label.setForeground(Color.BLACK);

        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(label);

    }

    private void loadFont(){
        try {
            InputStream in = getClass().getResourceAsStream("/Fonts/Daydream.otf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, in);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            this.currentFont = customFont;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

}
