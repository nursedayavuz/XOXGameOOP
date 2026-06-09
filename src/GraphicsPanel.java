/*
 * OOP Principles: Single Responsibility keeps the decorative bottom panel
 * separate from game state, AI, questions, and JFrame coordination.
 */

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GraphicsPanel extends JPanel {
    public GraphicsPanel() {
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        super.paintComponent(g2D);
        Image myImage = new ImageIcon("").getImage();
        g2D.drawImage(myImage, 0, 0, 70, 100, this);
        myImage = new ImageIcon("").getImage();
        g2D.drawImage(myImage, 70, 0, 70, 100, this);
        myImage = new ImageIcon("").getImage();
        g2D.drawImage(myImage, 140, 0, 70, 100, this);
        myImage = new ImageIcon("").getImage();
        g2D.drawImage(myImage, 210, 0, 70, 100, this);
        myImage = new ImageIcon("").getImage();
        g2D.drawImage(myImage, 280, 0, 70, 100, this);
        myImage = new ImageIcon("").getImage();
        g2D.drawImage(myImage, 350, 0, 70, 100, this);
        myImage = new ImageIcon("").getImage();
        g2D.drawImage(myImage, 420, 0, 70, 100, this);
        g2D.dispose();
    }
}
