import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class LandingPagePanel extends JPanel {
    private ActionListener startListener;

    public LandingPagePanel(ActionListener startListener) {
        this.startListener = startListener;
        setPreferredSize(new Dimension(1040, 760));
        setBackground(Theme.VOID);
        setLayout(new BorderLayout(0, 16));
        setBorder(new EmptyBorder(28, 34, 30, 34));
        buildContent();
    }

    private void buildContent() {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setOpaque(false);
        JLabel brand = new JLabel("XOX ORBIT");
        brand.setForeground(Theme.BONE);
        brand.setFont(Theme.font(Font.BOLD, 20));
        nav.add(brand, BorderLayout.WEST);

        JLabel status = new JLabel("BILGIYLE HAMLE YAP");
        status.setForeground(Theme.SMOKE);
        status.setFont(Theme.font(Font.BOLD, 12));
        nav.add(status, BorderLayout.EAST);
        add(nav, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);
        add(content, BorderLayout.CENTER);

        JPanel hero = new JPanel(new GridBagLayout());
        hero.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.55;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(20, 0, 0, 26);
        content.add(hero, c);

        JLabel kicker = new JLabel("KLASIK XOX DEGIL. STRATEJI + BILGI.");
        kicker.setForeground(Theme.PLUM);
        kicker.setFont(Theme.font(Font.BOLD, 13));
        GridBagConstraints h = constraints(0, 0);
        h.anchor = GridBagConstraints.WEST;
        h.insets = new Insets(0, 0, 14, 0);
        hero.add(kicker, h);

        JLabel title = new JLabel("<html>XOX'u<br>yeni bir<br>yörüngeye taşı.</html>");
        title.setForeground(Theme.BONE);
        title.setFont(Theme.font(Font.PLAIN, 58));
        h = constraints(0, 1);
        h.anchor = GridBagConstraints.WEST;
        hero.add(title, h);

        JTextArea copy = text("Soruyu dogru cevapla, hamleni kazan, rakibin stratejisini boz. 3x3, 4x4 ve 5x5 tahta; rastgele veya akilli bilgisayar; kategori bazli soru sistemi ve skor takibi tek oyunda.");
        copy.setPreferredSize(new Dimension(455, 112));
        h = constraints(0, 2);
        h.anchor = GridBagConstraints.WEST;
        h.insets = new Insets(24, 0, 18, 0);
        hero.add(copy, h);

        JButton start = new JButton("OYUNA BASLA");
        start.setFocusPainted(false);
        start.setForeground(Theme.BONE);
        start.setBackground(Theme.PLUM);
        start.setFont(Theme.font(Font.BOLD, 14));
        start.setBorder(new RoundedBorder(Theme.PLUM, Theme.RADIUS, 12, 22, 12, 22));
        start.addActionListener(startListener);
        h = constraints(0, 3);
        h.anchor = GridBagConstraints.WEST;
        hero.add(start, h);

        JPanel right = new JPanel(new GridBagLayout());
        right.setOpaque(false);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.45;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(36, 0, 0, 0);
        content.add(right, c);

        ParticleShowcase showcase = new ParticleShowcase();
        showcase.setPreferredSize(new Dimension(410, 280));
        GridBagConstraints r = constraints(0, 0);
        r.fill = GridBagConstraints.BOTH;
        r.insets = new Insets(0, 0, 20, 0);
        right.add(showcase, r);

        right.add(feature("Akilli Rakip", "Minimax stratejisiyle bilgisayar hamlelerini hesaplar."), featureConstraints(0, 1));
        right.add(feature("Soru Kilidi", "Hamle yapmak icin once bilgini kanitlaman gerekir."), featureConstraints(0, 2));
        right.add(feature("Esnek Tahta", "3x3, 4x4 ve 5x5 modlari oyunu genisletir."), featureConstraints(0, 3));

        add(rulesPanel(), BorderLayout.SOUTH);
    }

    private JPanel rulesPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(970, 138));
        panel.setBorder(new RoundedBorder(Theme.PLUM, Theme.RADIUS, 14, 18, 14, 18));

        JLabel title = new JLabel("Kurallar");
        title.setForeground(Theme.BONE);
        title.setFont(Theme.font(Font.BOLD, 16));
        GridBagConstraints c = constraints(0, 0);
        c.anchor = GridBagConstraints.WEST;
        c.gridwidth = 5;
        c.insets = new Insets(0, 0, 10, 0);
        panel.add(title, c);

        String[] rules = {
            "Mod, tahta, kategori ve zorluk sec.",
            "Dogru cevap hamle hakkini acar.",
            "Sure biterse hak rakibe gecer.",
            "Her soruda bir ipucu kullanabilirsin.",
            "Satir, sutun veya capraz kazandirir."
        };
        for (int i = 0; i < rules.length; i++) {
            panel.add(ruleCard(String.valueOf(i + 1), rules[i]), ruleConstraints(i));
        }
        return panel;
    }

    private JPanel ruleCard(String number, String rule) {
        JPanel card = new JPanel(new BorderLayout(8, 0));
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(180, 66));
        card.setBorder(new RoundedBorder(new Color(255, 255, 255, 30), Theme.RADIUS, 10, 12, 10, 12));

        JLabel numberLabel = new JLabel(number);
        numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numberLabel.setForeground(Theme.PLUM);
        numberLabel.setFont(Theme.font(Font.BOLD, 22));
        numberLabel.setPreferredSize(new Dimension(28, 44));
        card.add(numberLabel, BorderLayout.WEST);

        JTextArea ruleText = text(rule);
        ruleText.setFont(Theme.font(Font.BOLD, 12));
        ruleText.setForeground(Theme.BONE);
        card.add(ruleText, BorderLayout.CENTER);
        return card;
    }

    private GridBagConstraints ruleConstraints(int x) {
        GridBagConstraints c = constraints(x, 1);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, x == 0 ? 0 : 10, 0, 0);
        return c;
    }

    private JPanel feature(String title, String body) {
        JPanel panel = new JPanel(new BorderLayout(8, 4));
        panel.setOpaque(false);
        panel.setBorder(new RoundedBorder(Theme.BORDER, Theme.RADIUS, 12, 16, 12, 16));
        JLabel label = new JLabel(title);
        label.setForeground(Theme.BONE);
        label.setFont(Theme.font(Font.BOLD, 15));
        JTextArea text = text(body);
        text.setFont(Theme.font(Font.PLAIN, 13));
        panel.add(label, BorderLayout.NORTH);
        panel.add(text, BorderLayout.CENTER);
        return panel;
    }

    private GridBagConstraints featureConstraints(int x, int y) {
        GridBagConstraints c = constraints(x, y);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 12, 0);
        return c;
    }

    private JTextArea text(String value) {
        JTextArea area = new JTextArea(value);
        area.setOpaque(false);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFocusable(false);
        area.setForeground(Theme.ASH);
        area.setFont(Theme.font(Font.PLAIN, 16));
        area.setBorder(BorderFactory.createEmptyBorder());
        return area;
    }

    private GridBagConstraints constraints(int x, int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        return c;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Random random = new Random(17);
        Color[] colors = { Theme.PLUM, Theme.AMBER, Theme.LICHEN, Theme.BONE };
        for (int i = 0; i < 260; i++) {
            int x = 420 + random.nextInt(Math.max(1, getWidth() - 420));
            int y = 80 + random.nextInt(Math.max(1, getHeight() - 120));
            int size = 2 + random.nextInt(5);
            Color base = colors[random.nextInt(colors.length)];
            g2.setColor(new Color(base.getRed(), base.getGreen(), base.getBlue(), 70 + random.nextInt(130)));
            if (i % 3 == 0) {
                g2.drawOval(x, y, size + 4, size + 4);
            } else {
                g2.fillRect(x, y, size, size);
            }
        }
        g2.dispose();
    }

    private static class ParticleShowcase extends JPanel {
        ParticleShowcase() {
            setOpaque(false);
            setBorder(new RoundedBorder(Theme.BORDER, Theme.RADIUS, 0, 0, 0, 0));
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(255, 255, 255, 12));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), Theme.RADIUS, Theme.RADIUS);
            Random random = new Random(31);
            Color[] colors = { Theme.PLUM, Theme.AMBER, Theme.LICHEN, Theme.BONE };
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            for (int i = 0; i < 520; i++) {
                double angle = random.nextDouble() * Math.PI * 2;
                double radius = random.nextGaussian() * 44 + 98;
                int x = centerX + (int) (Math.cos(angle) * radius) + random.nextInt(36) - 18;
                int y = centerY + (int) (Math.sin(angle) * radius * 0.62) + random.nextInt(36) - 18;
                int size = 2 + random.nextInt(5);
                Color base = colors[random.nextInt(colors.length)];
                g2.setColor(new Color(base.getRed(), base.getGreen(), base.getBlue(), 85 + random.nextInt(150)));
                if (i % 4 == 0) {
                    int[] xs = { x, x + size + 4, x + size / 2 };
                    int[] ys = { y + size + 4, y + size + 4, y };
                    g2.drawPolygon(xs, ys, 3);
                } else if (i % 4 == 1) {
                    g2.drawOval(x, y, size + 3, size + 3);
                } else {
                    g2.fillRect(x, y, size, size);
                }
            }
            g2.setColor(Theme.PLUM);
            g2.setFont(Theme.font(Font.BOLD, 60));
            g2.drawString("X", centerX - 58, centerY + 18);
            g2.setColor(Theme.AMBER);
            g2.drawString("O", centerX + 12, centerY + 18);
            g2.dispose();
        }
    }
}
