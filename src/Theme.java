import java.awt.Color;
import java.awt.Font;

public final class Theme {
    public static final Color VOID = new Color(0x000000);
    public static final Color BONE = new Color(0xffffff);
    public static final Color ASH = new Color(0xbdbdbd);
    public static final Color SMOKE = new Color(0x9a9a9a);
    public static final Color PLUM = new Color(0x8052ff);
    public static final Color AMBER = new Color(0xffb829);
    public static final Color LICHEN = new Color(0x15846e);
    public static final Color PANEL = new Color(10, 11, 16);
    public static final Color PANEL_ALT = new Color(18, 20, 29);
    public static final Color BORDER = new Color(255, 255, 255, 42);

    public static final int RADIUS = 24;

    private Theme() {
    }

    public static Font font(int style, int size) {
        return new Font("Segoe UI", style, size);
    }
}
