/*
 * OOP Principles: Single Responsibility keeps the decorative bottom panel
 * separate from game state, AI, questions, and JFrame coordination.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;
import javax.swing.JPanel;

public class GraphicsPanel extends JPanel {
    public GraphicsPanel() {
        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Random random = new Random(42);
        Color[] colors = { Theme.PLUM, Theme.AMBER, Theme.LICHEN, Theme.BONE };
        int width = Math.max(1, getWidth());
        int height = Math.max(1, getHeight());

        for (int i = 0; i < 190; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int size = 2 + random.nextInt(5);
            Color base = colors[random.nextInt(colors.length)];
            int alpha = 65 + random.nextInt(145);
            g2.setColor(new Color(base.getRed(), base.getGreen(), base.getBlue(), alpha));

            if (i % 5 == 0) {
                int[] xs = { x, x + size + 5, x + size / 2 };
                int[] ys = { y + size + 5, y + size + 5, y };
                g2.drawPolygon(xs, ys, 3);
            } else if (i % 3 == 0) {
                g2.drawOval(x, y, size + 4, size + 4);
            } else {
                g2.fillRect(x, y, size, size);
            }
        }

        g2.dispose();
    }
}
