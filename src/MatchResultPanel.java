import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MatchResultPanel extends JPanel {
    public MatchResultPanel(String title, String detail, String scoreSummary,
            ActionListener newMatchListener, ActionListener menuListener) {
        setPreferredSize(new Dimension(1040, 680));
        setBackground(Theme.VOID);
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(28, 34, 30, 34));

        JPanel content = new JPanel(new GridBagLayout());
        content.setPreferredSize(new Dimension(620, 440));
        content.setBackground(Theme.PANEL);
        content.setBorder(new RoundedBorder(Theme.PLUM, Theme.RADIUS, 28, 32, 28, 32));

        JLabel kicker = new JLabel("MAC SONUCU");
        kicker.setForeground(Theme.PLUM);
        kicker.setFont(Theme.font(Font.BOLD, 13));
        GridBagConstraints c = constraints(0, 0);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(0, 0, 12, 0);
        content.add(kicker, c);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Theme.BONE);
        titleLabel.setFont(Theme.font(Font.BOLD, 44));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        c = constraints(0, 1);
        c.anchor = GridBagConstraints.CENTER;
        content.add(titleLabel, c);

        JTextArea detailArea = text(detail);
        detailArea.setPreferredSize(new Dimension(520, 70));
        c = constraints(0, 2);
        c.insets = new Insets(18, 0, 8, 0);
        content.add(detailArea, c);

        JLabel scoreLabel = new JLabel(scoreSummary);
        scoreLabel.setForeground(Theme.AMBER);
        scoreLabel.setFont(Theme.font(Font.BOLD, 18));
        c = constraints(0, 3);
        c.insets = new Insets(8, 0, 24, 0);
        content.add(scoreLabel, c);

        JPanel buttons = new JPanel(new BorderLayout(12, 0));
        buttons.setOpaque(false);
        JButton newMatchButton = button("YENI MAC", true);
        JButton menuButton = button("ANA MENU", false);
        newMatchButton.addActionListener(newMatchListener);
        menuButton.addActionListener(menuListener);
        buttons.add(newMatchButton, BorderLayout.WEST);
        buttons.add(menuButton, BorderLayout.EAST);
        c = constraints(0, 4);
        content.add(buttons, c);

        GridBagConstraints wrapper = constraints(0, 0);
        add(content, wrapper);
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
        return area;
    }

    private JButton button(String label, boolean primary) {
        JButton button = new JButton(label);
        button.setFocusPainted(false);
        button.setForeground(Theme.BONE);
        button.setBackground(primary ? Theme.PLUM : Theme.PANEL_ALT);
        button.setFont(Theme.font(Font.BOLD, 14));
        button.setBorder(new RoundedBorder(primary ? Theme.PLUM : Theme.BORDER,
                Theme.RADIUS, 12, 22, 12, 22));
        return button;
    }

    private GridBagConstraints constraints(int x, int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        return c;
    }
}
