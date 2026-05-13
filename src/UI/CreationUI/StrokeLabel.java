package UI.CreationUI;

import Utilities.Important;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

/**
 * @author Google Gemini
 */
public class StrokeLabel extends JLabel {
    private final Color strokeColor = Color.BLACK;
    private float strokeWidth = (float) (7.5f * Important.getManualScale());
    private boolean visibility;

    public StrokeLabel(String text, int size) {
        super(text);
        setBorder(new EmptyBorder(0, Important.calculateDimension(10), 0, Important.calculateDimension(10)));
        setOpaque(false);
        setFont(Objects.requireNonNull(Important.loadFont("/Fonts/Daydream.otf")).deriveFont(Font.BOLD,Important.calculateDimension(size)));
        setForeground(Color.WHITE);
        this.visibility = true;
    }


    @Override
    protected void paintComponent(Graphics g) {
        if(visibility){
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setClip(null);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        String text = getText();
        if (text == null || text.isEmpty()) {
            g2.dispose();
            return;
        }

        Font font = getFont();
        TextLayout tl = new TextLayout(text, font, g2.getFontRenderContext());
        Rectangle2D bounds = tl.getBounds();

        Insets insets = getInsets();
        double x = insets.left + (getWidth() - insets.left - insets.right - bounds.getWidth()) / 2 - bounds.getX();
        double y = insets.top + (getHeight() - insets.top - insets.bottom - bounds.getHeight()) / 2 - bounds.getY();

        Shape shape = tl.getOutline(AffineTransform.getTranslateInstance(x, y));

        g2.setColor(strokeColor);
        g2.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.draw(shape);

        g2.setColor(getForeground());
        g2.fill(shape);

        g2.dispose();
        }
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}