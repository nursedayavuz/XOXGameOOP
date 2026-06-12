import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.EmptyBorder;

public class RoundedBorder extends EmptyBorder {
    private Color color;
    private int radius;

    public RoundedBorder(Color color, int radius, int top, int left, int bottom, int right) {
        super(top, left, bottom, right);
        this.color = color;
        this.radius = radius;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
        g2.dispose();
    }
}
