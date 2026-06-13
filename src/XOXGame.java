/*
 * OOP Principles: This JFrame coordinates UI only, while Inheritance,
 * Polymorphism, Interface strategy, Encapsulation, and Single Responsibility are
 * implemented across Player, GameBoard, GameState, AI, and question classes.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Locale;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class XOXGame extends JFrame {
    private static final int QUESTION_TIME_LIMIT = 20;
    private static final String LANDING_CARD = "landing";
    private static final String GAME_CARD = "game";
    private static final String RESULT_CARD = "result";
    private static final Color APP_BACKGROUND = Theme.VOID;
    private static final Color PANEL_BACKGROUND = Theme.PANEL;
    private static final Color PRIMARY = Theme.PLUM;
    private static final Color PRIMARY_DARK = Theme.BONE;
    private static final Color ACCENT = Theme.AMBER;
    private static final Color GRID_COLOR = Theme.PLUM;
    private static final Color TEXT_COLOR = Theme.BONE;
    private static final Color ERROR_COLOR = Theme.AMBER;
    private static final Color SUCCESS_COLOR = Theme.LICHEN;

    private JTextField messageTextField = new JTextField();
    private JLabel turnLabel = new JLabel();
    private JPanel gamePanel = new JPanel();
    private JTextField[] boxTextField = new JTextField[9];
    private JLabel[] gridLabel = new JLabel[4];

    private JPanel playersPanel = new JPanel();
    private ButtonGroup playersButtonGroup = new ButtonGroup();
    private JRadioButton twoPlayersRadioButton = new JRadioButton();
    private JRadioButton onePlayerRadioButton = new JRadioButton();

    private JPanel firstPanel = new JPanel();
    private ButtonGroup firstButtonGroup = new ButtonGroup();
    private JRadioButton youFirstRadioButton = new JRadioButton();
    private JRadioButton computerFirstRadioButton = new JRadioButton();

    private JPanel computerPanel = new JPanel();
    private ButtonGroup computerButtonGroup = new ButtonGroup();
    private JRadioButton randomRadioButton = new JRadioButton();
    private JRadioButton normalRadioButton = new JRadioButton();
    private JRadioButton smartRadioButton = new JRadioButton();

    private JPanel gridSizePanel = new JPanel();
    private ButtonGroup gridSizeButtonGroup = new ButtonGroup();
    private JRadioButton size3RadioButton = new JRadioButton();
    private JRadioButton size4RadioButton = new JRadioButton();
    private JRadioButton size5RadioButton = new JRadioButton();

    private JPanel scorePanel = new JPanel();
    private JLabel xWinsLabel = new JLabel();
    private JLabel oWinsLabel = new JLabel();
    private JLabel drawsLabel = new JLabel();
    private JButton resetScoreButton = new JButton();

    private JPanel historyPanel = new JPanel();
    private JLabel[] historyLabels = new JLabel[5];
    private String[] matchHistory = new String[5];
    private int matchHistoryCount;

    private JPanel categoryPanel = new JPanel();
    private ButtonGroup categoryButtonGroup = new ButtonGroup();
    private JRadioButton allQuestionsRadioButton = new JRadioButton();
    private JRadioButton bursaRadioButton = new JRadioButton();
    private JRadioButton technologyRadioButton = new JRadioButton();

    private JPanel buttonsPanel = new JPanel();
    private JButton startStopButton = new JButton();
    private JButton exitButton = new JButton();
    private JTextArea questionTextArea = new JTextArea();
    private JTextField answerTextField = new JTextField();
    private JButton checkButton = new JButton();
    private JButton hintButton = new JButton();
    private JButton passButton = new JButton();
    private JLabel timerLabel = new JLabel();
    private GraphicsPanel displayPanel = new GraphicsPanel();
    private boolean boardReadyForMove;
    private JPanel rootPanel = new JPanel();
    private CardLayout rootLayout = new CardLayout();
    private boolean gamePageBuilt;
    private JPanel resultPanel;

    private boolean canClick = false;
    private int tries;
    private int secondsLeft;
    private boolean correctAnswer;
    private int wrongAnswersForQuestion;
    private boolean drawScoreAddedForQuestion;
    private boolean passUsedForQuestion;
    private boolean hintUsedForQuestion;
    private String correctAnswerToShowAfterComputerMove;
    private Timer questionCountdownTimer;
    private Question currentQuestion;
    private QuestionBank questionBank = new QuestionBank();
    private GameBoard gameBoard = new GameBoard(3);
    private GameState gameState = new GameState();
    private Player xPlayer = new HumanPlayer("Player X", GameBoard.PLAYER_X);
    private Player oPlayer = new HumanPlayer("Player O", GameBoard.PLAYER_O);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new XOXGame().setVisible(true);
            }
        });
    }

    public XOXGame() {
        setTitle("XOX Orbit");
        getContentPane().setBackground(APP_BACKGROUND);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                exitForm(evt);
            }
        });
        buildRoot();
        packAndCenter();
    }

    private void buildRoot() {
        rootPanel.setLayout(rootLayout);
        rootPanel.setBackground(APP_BACKGROUND);
        rootPanel.add(new LandingPagePanel(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showGamePage();
            }
        }), LANDING_CARD);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(rootPanel, BorderLayout.CENTER);
        rootLayout.show(rootPanel, LANDING_CARD);
    }

    private void showGamePage() {
        if (!gamePageBuilt) {
            JPanel gamePage = new JPanel(new BorderLayout(18, 14));
            gamePage.setBackground(APP_BACKGROUND);
            gamePage.setBorder(new RoundedBorder(new Color(255, 255, 255, 0), 18, 18, 18, 18, 18));
            rootPanel.add(gamePage, GAME_CARD);
            buildInterface(gamePage);
            gamePageBuilt = true;
        }
        messageTextField.setText("Ayarlarini sec");
        setOnePlayerOptionsEnabled(true);
        setQuestionControlsVisible(false);
        refreshScoreLabels();
        updateTurnLabel();
        refreshOptionStyles();
        rootLayout.show(rootPanel, GAME_CARD);
        revalidate();
        repaint();
        packAndCenter();
    }

    private void showLandingPage() {
        stopQuestionTimer();
        if (gameState.isGameStarted()) {
            stopGame(false);
        }
        rootLayout.show(rootPanel, LANDING_CARD);
        revalidate();
        repaint();
        packAndCenter();
    }

    private void showResultPage(String title, String detail) {
        String scoreSummary = "X: " + gameState.getXWins()
                + "   O: " + gameState.getOWins()
                + "   Beraberlik: " + gameState.getDraws();
        if (resultPanel != null) {
            rootPanel.remove(resultPanel);
        }
        resultPanel = new MatchResultPanel(title, detail, scoreSummary,
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        showGamePage();
                    }
                },
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        showLandingPage();
                    }
                });
        rootPanel.add(resultPanel, RESULT_CARD);
        rootLayout.show(rootPanel, RESULT_CARD);
        revalidate();
        repaint();
        packAndCenter();
    }

    private void buildInterface(JPanel page) {
        GridBagConstraints gridConstraints;

        JPanel headerPanel = buildGameHeader();
        page.add(headerPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(18, 0));
        mainPanel.setOpaque(false);
        page.add(mainPanel, BorderLayout.CENTER);

        JPanel boardColumn = new JPanel(new GridBagLayout());
        boardColumn.setOpaque(false);
        mainPanel.add(boardColumn, BorderLayout.CENTER);

        JPanel controlsColumn = new JPanel(new GridBagLayout());
        controlsColumn.setOpaque(false);
        controlsColumn.setPreferredSize(new Dimension(520, 700));
        mainPanel.add(controlsColumn, BorderLayout.EAST);

        messageTextField.setPreferredSize(new Dimension(520, 64));
        messageTextField.setEditable(false);
        messageTextField.setBackground(Theme.PANEL_ALT);
        messageTextField.setForeground(Color.WHITE);
        messageTextField.setHorizontalAlignment(SwingConstants.CENTER);
        messageTextField.setFont(new Font("Segoe UI", Font.BOLD, 24));
        messageTextField.setBorder(new RoundedBorder(PRIMARY, Theme.RADIUS, 8, 12, 8, 12));
        gridConstraints = constraints(0, 0);
        gridConstraints.insets = new Insets(10, 10, 10, 10);
        boardColumn.add(messageTextField, gridConstraints);

        turnLabel.setPreferredSize(new Dimension(160, 32));
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        turnLabel.setForeground(Theme.BONE);
        turnLabel.setOpaque(true);
        turnLabel.setBackground(Theme.PANEL_ALT);
        turnLabel.setBorder(new RoundedBorder(Theme.BORDER, Theme.RADIUS, 5, 12, 5, 12));
        gridConstraints = constraints(0, 1);
        gridConstraints.insets = new Insets(10, 10, 10, 10);
        boardColumn.add(turnLabel, gridConstraints);

        gamePanel.setPreferredSize(new Dimension(380, 380));
        gamePanel.setBackground(APP_BACKGROUND);
        gamePanel.setBorder(new RoundedBorder(Theme.BORDER, Theme.RADIUS, 12, 12, 12, 12));
        gamePanel.setLayout(new GridBagLayout());
        gridConstraints = constraints(0, 2);
        gridConstraints.insets = new Insets(10, 10, 10, 10);
        boardColumn.add(gamePanel, gridConstraints);
        rebuildGameGrid(3);

        buildPlayersPanel(controlsColumn);
        buildFirstPanel(controlsColumn);
        buildComputerPanel(controlsColumn);
        buildGridSizePanel(controlsColumn);
        buildScorePanel(controlsColumn);
        buildCategoryPanel(controlsColumn);
        buildHistoryPanel(controlsColumn);
        buildButtonsPanel(controlsColumn);
        buildQuestionControls(page);
    }

    private JPanel buildGameHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout(18, 0));
        headerPanel.setPreferredSize(new Dimension(840, 96));
        headerPanel.setBackground(Theme.PANEL);
        headerPanel.setBorder(new RoundedBorder(Theme.BORDER, Theme.RADIUS, 14, 18, 14, 18));

        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setOpaque(false);
        JLabel title = new JLabel("XOX ORBIT");
        title.setForeground(Theme.BONE);
        title.setFont(Theme.font(Font.BOLD, 30));
        GridBagConstraints c = constraints(0, 0);
        c.anchor = GridBagConstraints.WEST;
        titlePanel.add(title, c);

        JLabel subtitle = new JLabel("Soru kilitli strateji tahtasi");
        subtitle.setForeground(Theme.ASH);
        subtitle.setFont(Theme.font(Font.PLAIN, 14));
        c = constraints(0, 1);
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(4, 0, 0, 0);
        titlePanel.add(subtitle, c);
        headerPanel.add(titlePanel, BorderLayout.WEST);

        JPanel factsPanel = new JPanel(new GridBagLayout());
        factsPanel.setOpaque(false);
        factsPanel.add(makeMetric("MOD", "1P / 2P"), metricConstraints(0));
        factsPanel.add(makeMetric("TAHTA", "3x3 - 5x5"), metricConstraints(1));
        factsPanel.add(makeMetric("AI", "Easy / Normal / Hard"), metricConstraints(2));
        headerPanel.add(factsPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel makeMetric(String label, String value) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(130, 58));
        panel.setBorder(new RoundedBorder(new Color(255, 255, 255, 28), Theme.RADIUS, 8, 10, 8, 10));

        JLabel labelView = new JLabel(label);
        labelView.setForeground(Theme.SMOKE);
        labelView.setFont(Theme.font(Font.BOLD, 11));
        GridBagConstraints c = constraints(0, 0);
        c.anchor = GridBagConstraints.WEST;
        panel.add(labelView, c);

        JLabel valueView = new JLabel(value);
        valueView.setForeground(Theme.BONE);
        valueView.setFont(Theme.font(Font.BOLD, 13));
        c = constraints(0, 1);
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(4, 0, 0, 0);
        panel.add(valueView, c);
        return panel;
    }

    private GridBagConstraints metricConstraints(int x) {
        GridBagConstraints c = constraints(x, 0);
        c.insets = new Insets(0, 8, 0, 0);
        return c;
    }

    private void buildPlayersPanel(JPanel page) {
        preparePanel(playersPanel, new Dimension(230, 116));
        GridBagConstraints gridConstraints = constraints(0, 0);
        gridConstraints.insets = new Insets(5, 10, 5, 10);
        page.add(playersPanel, gridConstraints);

        addPanelTitle(playersPanel, "OYUNCU MODU", 0);

        twoPlayersRadioButton.setText("Two Players");
        styleRadio(twoPlayersRadioButton);
        playersButtonGroup.add(twoPlayersRadioButton);
        gridConstraints = constraints(0, 1);
        gridConstraints.anchor = GridBagConstraints.WEST;
        playersPanel.add(twoPlayersRadioButton, gridConstraints);
        twoPlayersRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setOnePlayerOptionsEnabled(false);
            }
        });

        onePlayerRadioButton.setText("One Player");
        styleRadio(onePlayerRadioButton);
        onePlayerRadioButton.setSelected(true);
        playersButtonGroup.add(onePlayerRadioButton);
        gridConstraints = constraints(0, 2);
        gridConstraints.anchor = GridBagConstraints.WEST;
        playersPanel.add(onePlayerRadioButton, gridConstraints);
        onePlayerRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setOnePlayerOptionsEnabled(true);
            }
        });
        refreshOptionStyles();
    }

    private void buildFirstPanel(JPanel page) {
        preparePanel(firstPanel, new Dimension(230, 116));
        GridBagConstraints gridConstraints = constraints(0, 1);
        gridConstraints.insets = new Insets(5, 10, 5, 10);
        page.add(firstPanel, gridConstraints);

        addPanelTitle(firstPanel, "ILK HAMLE", 0);

        youFirstRadioButton.setText("You First");
        styleRadio(youFirstRadioButton);
        youFirstRadioButton.setSelected(true);
        firstButtonGroup.add(youFirstRadioButton);
        gridConstraints = constraints(0, 1);
        gridConstraints.anchor = GridBagConstraints.WEST;
        firstPanel.add(youFirstRadioButton, gridConstraints);

        computerFirstRadioButton.setText("Computer First");
        styleRadio(computerFirstRadioButton);
        firstButtonGroup.add(computerFirstRadioButton);
        gridConstraints = constraints(0, 2);
        gridConstraints.anchor = GridBagConstraints.WEST;
        firstPanel.add(computerFirstRadioButton, gridConstraints);
    }

    private void buildComputerPanel(JPanel page) {
        preparePanel(computerPanel, new Dimension(230, 150));
        GridBagConstraints gridConstraints = constraints(0, 2);
        gridConstraints.insets = new Insets(5, 10, 5, 10);
        page.add(computerPanel, gridConstraints);

        addPanelTitle(computerPanel, "ZORLUK", 0);

        randomRadioButton.setText("Easy");
        styleRadio(randomRadioButton);
        computerButtonGroup.add(randomRadioButton);
        gridConstraints = constraints(0, 1);
        gridConstraints.anchor = GridBagConstraints.WEST;
        computerPanel.add(randomRadioButton, gridConstraints);

        normalRadioButton.setText("Normal");
        styleRadio(normalRadioButton);
        normalRadioButton.setSelected(true);
        computerButtonGroup.add(normalRadioButton);
        gridConstraints = constraints(0, 2);
        gridConstraints.anchor = GridBagConstraints.WEST;
        computerPanel.add(normalRadioButton, gridConstraints);

        smartRadioButton.setText("Hard");
        styleRadio(smartRadioButton);
        computerButtonGroup.add(smartRadioButton);
        gridConstraints = constraints(0, 3);
        gridConstraints.anchor = GridBagConstraints.WEST;
        computerPanel.add(smartRadioButton, gridConstraints);
    }

    private void buildGridSizePanel(JPanel page) {
        preparePanel(gridSizePanel, new Dimension(230, 150));
        GridBagConstraints gridConstraints = constraints(1, 0);
        gridConstraints.insets = new Insets(5, 10, 5, 10);
        page.add(gridSizePanel, gridConstraints);

        JLabel label = new JLabel("Grid Boyutu:");
        stylePanelTitle(label);
        gridConstraints = constraints(0, 0);
        gridConstraints.anchor = GridBagConstraints.WEST;
        gridSizePanel.add(label, gridConstraints);

        size3RadioButton.setText("3x3");
        size4RadioButton.setText("4x4");
        size5RadioButton.setText("5x5");
        styleRadio(size3RadioButton);
        styleRadio(size4RadioButton);
        styleRadio(size5RadioButton);
        size3RadioButton.setSelected(true);
        gridSizeButtonGroup.add(size3RadioButton);
        gridSizeButtonGroup.add(size4RadioButton);
        gridSizeButtonGroup.add(size5RadioButton);

        gridConstraints = constraints(0, 1);
        gridConstraints.anchor = GridBagConstraints.WEST;
        gridSizePanel.add(size3RadioButton, gridConstraints);
        gridConstraints = constraints(0, 2);
        gridConstraints.anchor = GridBagConstraints.WEST;
        gridSizePanel.add(size4RadioButton, gridConstraints);
        gridConstraints = constraints(0, 3);
        gridConstraints.anchor = GridBagConstraints.WEST;
        gridSizePanel.add(size5RadioButton, gridConstraints);
    }

    private void addPanelTitle(JPanel panel, String title, int row) {
        JLabel label = new JLabel(title);
        stylePanelTitle(label);
        GridBagConstraints gridConstraints = constraints(0, row);
        gridConstraints.anchor = GridBagConstraints.WEST;
        gridConstraints.insets = new Insets(0, 0, 8, 0);
        panel.add(label, gridConstraints);
    }

    private void buildScorePanel(JPanel page) {
        preparePanel(scorePanel, new Dimension(220, 136));
        GridBagConstraints gridConstraints = constraints(1, 1);
        gridConstraints.insets = new Insets(5, 10, 5, 10);
        page.add(scorePanel, gridConstraints);

        styleScoreLabel(xWinsLabel);
        styleScoreLabel(oWinsLabel);
        styleScoreLabel(drawsLabel);

        gridConstraints = constraints(0, 0);
        gridConstraints.anchor = GridBagConstraints.WEST;
        scorePanel.add(xWinsLabel, gridConstraints);
        gridConstraints = constraints(0, 1);
        gridConstraints.anchor = GridBagConstraints.WEST;
        scorePanel.add(oWinsLabel, gridConstraints);
        gridConstraints = constraints(0, 2);
        gridConstraints.anchor = GridBagConstraints.WEST;
        scorePanel.add(drawsLabel, gridConstraints);

        resetScoreButton.setText("Skoru Sıfırla");
        styleButton(resetScoreButton, false);
        resetScoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameState.resetScores();
                refreshScoreLabels();
            }
        });
        gridConstraints = constraints(0, 3);
        gridConstraints.insets = new Insets(10, 0, 0, 0);
        scorePanel.add(resetScoreButton, gridConstraints);
    }

    private void buildCategoryPanel(JPanel page) {
        preparePanel(categoryPanel, new Dimension(250, 160));
        GridBagConstraints gridConstraints = constraints(1, 2);
        gridConstraints.insets = new Insets(5, 10, 5, 10);
        page.add(categoryPanel, gridConstraints);

        addPanelTitle(categoryPanel, "SORU KATEGORİSİ", 0);

        allQuestionsRadioButton.setText("Tüm Sorular");
        bursaRadioButton.setText("BURSA");
        technologyRadioButton.setText("TEKNOLOJİ");
        styleRadio(allQuestionsRadioButton);
        styleRadio(bursaRadioButton);
        styleRadio(technologyRadioButton);
        allQuestionsRadioButton.setSelected(true);
        categoryButtonGroup.add(allQuestionsRadioButton);
        categoryButtonGroup.add(bursaRadioButton);
        categoryButtonGroup.add(technologyRadioButton);

        gridConstraints = constraints(0, 1);
        gridConstraints.anchor = GridBagConstraints.WEST;
        categoryPanel.add(allQuestionsRadioButton, gridConstraints);
        gridConstraints = constraints(0, 2);
        gridConstraints.anchor = GridBagConstraints.WEST;
        categoryPanel.add(bursaRadioButton, gridConstraints);
        gridConstraints = constraints(0, 3);
        gridConstraints.anchor = GridBagConstraints.WEST;
        categoryPanel.add(technologyRadioButton, gridConstraints);
    }

    private void buildHistoryPanel(JPanel page) {
        preparePanel(historyPanel, new Dimension(250, 150));
        GridBagConstraints gridConstraints = constraints(1, 3);
        gridConstraints.insets = new Insets(5, 10, 5, 10);
        page.add(historyPanel, gridConstraints);

        addPanelTitle(historyPanel, "MAC GECMISI", 0);
        for (int i = 0; i < historyLabels.length; i++) {
            historyLabels[i] = new JLabel("-");
            historyLabels[i].setForeground(Theme.ASH);
            historyLabels[i].setFont(Theme.font(Font.BOLD, 12));
            gridConstraints = constraints(0, i + 1);
            gridConstraints.anchor = GridBagConstraints.WEST;
            gridConstraints.insets = new Insets(2, 0, 0, 0);
            historyPanel.add(historyLabels[i], gridConstraints);
        }
        refreshHistoryLabels();
    }

    private void buildButtonsPanel(JPanel page) {
        buttonsPanel.setPreferredSize(new Dimension(200, 86));
        buttonsPanel.setBackground(APP_BACKGROUND);
        buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints = constraints(0, 4);
        gridConstraints.gridwidth = 2;
        page.add(buttonsPanel, gridConstraints);

        startStopButton.setText("Start Game");
        styleButton(startStopButton, true);
        gridConstraints = constraints(0, 0);
        buttonsPanel.add(startStopButton, gridConstraints);
        startStopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startStopButtonActionPerformed();
            }
        });

        exitButton.setText("Exit");
        styleButton(exitButton, false);
        gridConstraints = constraints(0, 1);
        gridConstraints.insets = new Insets(10, 0, 0, 0);
        buttonsPanel.add(exitButton, gridConstraints);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void buildQuestionControls(JPanel page) {
        JPanel questionPanel = new JPanel(new GridBagLayout());
        questionPanel.setOpaque(false);
        page.add(questionPanel, BorderLayout.SOUTH);

        questionTextArea.setPreferredSize(new Dimension(700, 58));
        questionTextArea.setFont(new Font("Segoe UI", Font.BOLD, 15));
        questionTextArea.setLineWrap(true);
        questionTextArea.setWrapStyleWord(true);
        questionTextArea.setEditable(false);
        questionTextArea.setBackground(PANEL_BACKGROUND);
        questionTextArea.setForeground(TEXT_COLOR);
        questionTextArea.setBorder(new RoundedBorder(Theme.BORDER, Theme.RADIUS, 8, 12, 8, 12));
        GridBagConstraints gridConstraints = constraints(0, 6);
        gridConstraints.gridwidth = 3;
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.weightx = 1.0;
        gridConstraints.insets = new Insets(10, 10, 4, 10);
        questionPanel.add(questionTextArea, gridConstraints);

        answerTextField.setPreferredSize(new Dimension(420, 34));
        answerTextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        answerTextField.setBackground(Theme.PANEL_ALT);
        answerTextField.setForeground(TEXT_COLOR);
        answerTextField.setCaretColor(Theme.BONE);
        answerTextField.setBorder(new RoundedBorder(Theme.BORDER, Theme.RADIUS, 4, 10, 4, 10));
        answerTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkButtonActionPerformed();
            }
        });
        gridConstraints = constraints(0, 7);
        gridConstraints.gridwidth = 2;
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.weightx = 1.0;
        gridConstraints.insets = new Insets(0, 10, 10, 10);
        questionPanel.add(answerTextField, gridConstraints);

        JPanel answerButtonsPanel = new JPanel(new GridBagLayout());
        answerButtonsPanel.setBackground(APP_BACKGROUND);
        timerLabel.setPreferredSize(new Dimension(92, 34));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setForeground(Theme.BONE);
        timerLabel.setBackground(Theme.PANEL_ALT);
        timerLabel.setOpaque(true);
        timerLabel.setFont(Theme.font(Font.BOLD, 13));
        timerLabel.setBorder(new RoundedBorder(Theme.BORDER, Theme.RADIUS, 6, 12, 6, 12));
        checkButton.setText("Check Answer");
        hintButton.setText("IPUCU");
        passButton.setText("PAS");
        styleButton(checkButton, true);
        styleButton(hintButton, false);
        styleButton(passButton, false);
        hintButton.setEnabled(false);
        passButton.setEnabled(false);

        gridConstraints = constraints(0, 0);
        gridConstraints.insets = new Insets(0, 0, 0, 8);
        answerButtonsPanel.add(timerLabel, gridConstraints);
        gridConstraints = constraints(1, 0);
        gridConstraints.insets = new Insets(0, 0, 0, 8);
        answerButtonsPanel.add(checkButton, gridConstraints);
        gridConstraints = constraints(2, 0);
        gridConstraints.insets = new Insets(0, 0, 0, 8);
        answerButtonsPanel.add(hintButton, gridConstraints);
        gridConstraints = constraints(3, 0);
        answerButtonsPanel.add(passButton, gridConstraints);

        gridConstraints = constraints(2, 7);
        gridConstraints.insets = new Insets(0, 10, 10, 10);
        questionPanel.add(answerButtonsPanel, gridConstraints);

        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkButtonActionPerformed();
            }
        });
        hintButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hintButtonActionPerformed();
            }
        });
        passButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                passButtonActionPerformed();
            }
        });

        displayPanel.setPreferredSize(new Dimension(700, 100));
        displayPanel.setBackground(APP_BACKGROUND);
        displayPanel.setBorder(new RoundedBorder(new Color(255, 255, 255, 24), Theme.RADIUS, 0, 0, 0, 0));
        gridConstraints = constraints(0, 8);
        gridConstraints.gridwidth = 3;
        gridConstraints.insets = new Insets(0, 0, 10, 0);
        questionPanel.add(displayPanel, gridConstraints);
    }

    private void rebuildGameGrid(int size) {
        gamePanel.removeAll();
        int cellSize = getCellPixelSize(size);
        int boardSpan = cellSize * size;
        gamePanel.setLayout(new GridLayout(size, size, 10, 10));
        gamePanel.setPreferredSize(new Dimension(boardSpan + (size - 1) * 10, boardSpan + (size - 1) * 10));
        boxTextField = new JTextField[size * size];
        gridLabel = new JLabel[0];

        for (int i = 0; i < boxTextField.length; i++) {
            final int index = i;
            boxTextField[i] = new JTextField();
            boxTextField[i].setPreferredSize(new Dimension(cellSize, cellSize));
            boxTextField[i].setEditable(false);
            boxTextField[i].setBackground(PANEL_BACKGROUND);
            boxTextField[i].setForeground(PRIMARY_DARK);
            boxTextField[i].setHorizontalAlignment(SwingConstants.CENTER);
            boxTextField[i].setFont(new Font("Segoe UI", Font.BOLD, getCellFontSize(size)));
            boxTextField[i].setBorder(new RoundedBorder(GRID_COLOR, 18, 0, 0, 0, 0));
            gamePanel.add(boxTextField[i]);
            boxTextField[i].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    boxTextFieldMousePressed(index, e);
                }
            });
        }

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    private JLabel createGridLine(Dimension size) {
        JLabel line = new JLabel();
        line.setPreferredSize(size);
        line.setOpaque(true);
        line.setBackground(GRID_COLOR);
        return line;
    }

    private void startStopButtonActionPerformed() {
        if (startStopButton.getText().equals("Start Game")) {
            startGame();
        } else {
            stopGame(false);
        }
    }

    private void startGame() {
        int selectedSize = getSelectedGridSize();
        gameBoard = new GameBoard(selectedSize);
        rebuildGameGrid(selectedSize);
        configurePlayers();
        gameState.resetForNewGame(true);
        canClick = true;
        boardReadyForMove = false;
        correctAnswer = false;
        wrongAnswersForQuestion = 0;
        drawScoreAddedForQuestion = false;
        tries = 0;

        startStopButton.setText("Stop Game");
        setSetupControlsEnabled(false);
        exitButton.setEnabled(false);
        messageTextField.setText("Game Started");
        updateTurnLabel();
        clearBoxes();
        setBoardReady(false);

        setQuestionControlsVisible(true);
        questionTextArea.setText("");
        answerTextField.setText("");
        if (onePlayerRadioButton.isSelected() && computerFirstRadioButton.isSelected()) {
            computerTurn();
        } else {
            askQuestion();
        }

        packAndCenter();
    }

    private void stopGame(boolean gameFinished) {
        stopQuestionTimer();
        startStopButton.setText("Start Game");
        if (!gameFinished) {
            messageTextField.setText("Game Stopped");
        }
        gameState.stopGame();
        setSetupControlsEnabled(true);
        exitButton.setEnabled(true);
        canClick = false;
        boardReadyForMove = false;
        setBoardReady(false);
        questionTextArea.setText("");
        answerTextField.setText("");
        timerLabel.setText("");
        passButton.setEnabled(false);
        setQuestionControlsVisible(false);
        updateTurnLabel();
    }

    private void configurePlayers() {
        if (twoPlayersRadioButton.isSelected()) {
            xPlayer = new HumanPlayer("Player X", GameBoard.PLAYER_X);
            oPlayer = new HumanPlayer("Player O", GameBoard.PLAYER_O);
            return;
        }

        AIStrategy strategy = getSelectedStrategy();
        if (computerFirstRadioButton.isSelected()) {
            xPlayer = new ComputerPlayer("Computer", GameBoard.PLAYER_X, strategy);
            oPlayer = new HumanPlayer("You", GameBoard.PLAYER_O);
        } else {
            xPlayer = new HumanPlayer("You", GameBoard.PLAYER_X);
            oPlayer = new ComputerPlayer("Computer", GameBoard.PLAYER_O, strategy);
        }
    }

    private AIStrategy getSelectedStrategy() {
        if (randomRadioButton.isSelected()) {
            return new RandomStrategy();
        }
        if (smartRadioButton.isSelected()) {
            return new MinimaxStrategy();
        }
        return new NormalStrategy();
    }

    private void boxTextFieldMousePressed(int index, MouseEvent e) {
        if (!canClick || !gameState.isGameStarted()) {
            return;
        }
        Point p = e.getComponent().getLocation();
        if (p == null || !(getCurrentPlayer() instanceof HumanPlayer)) {
            return;
        }
        if (questionTextArea.isVisible() && !correctAnswer) {
            messageTextField.setText("Once soruyu cevapla");
            return;
        }
        makeMove(index);
    }

    private void makeMove(int index) {
        stopQuestionTimer();
        char currentMark = gameState.getCurrentMark();
        if (!gameBoard.makeMove(index, currentMark)) {
            return;
        }
        boardReadyForMove = false;
        setBoardReady(false);
        boxTextField[index].setText(String.valueOf(currentMark));
        boxTextField[index].setForeground(currentMark == GameBoard.PLAYER_X ? Theme.PLUM : Theme.AMBER);

        if (gameBoard.checkWin(currentMark)) {
            highlightWinningCells(currentMark);
            if (currentMark == GameBoard.PLAYER_X) {
                gameState.setXWins();
                messageTextField.setText("X wins!");
                refreshScoreLabels();
                addMatchHistory("X Kazandi");
                stopGame(true);
                showResultPage("X Kazandi", "X oyuncusu dogru cevap ve stratejik hamleyle maci bitirdi.");
            } else {
                gameState.setOWins();
                messageTextField.setText("O wins!");
                refreshScoreLabels();
                addMatchHistory("O Kazandi");
                stopGame(true);
                showResultPage("O Kazandi", "O oyuncusu tahtadaki hattini tamamlayarak maci kazandi.");
            }
            return;
        }

        if (gameBoard.isFull()) {
            gameState.setDraw();
            messageTextField.setText("It's a draw!");
            refreshScoreLabels();
            addMatchHistory("Beraberlik");
            stopGame(true);
            showResultPage("Beraberlik", "Tahta doldu ve iki taraf da ustunluk kuramadi.");
            return;
        }

        gameState.switchTurn();
        messageTextField.setText("Hamle tamamlandi");
        updateTurnLabel();

        if (onePlayerRadioButton.isSelected()) {
            if (getCurrentPlayer() instanceof ComputerPlayer) {
                computerTurn();
            } else {
                if (correctAnswerToShowAfterComputerMove != null) {
                    showCorrectAnswerThenAskNextQuestion();
                } else {
                    askQuestion();
                }
            }
        } else {
            askQuestion();
        }
    }

    private void computerTurn() {
        if (!gameState.isGameStarted() || gameState.isGameOver()) {
            return;
        }
        Player currentPlayer = getCurrentPlayer();
        if (!(currentPlayer instanceof ComputerPlayer)) {
            return;
        }
        int move = currentPlayer.chooseMove(gameBoard);
        if (move >= 0) {
            makeMove(move);
        }
    }

    private void askQuestion() {
        askQuestion(true);
    }

    private void askQuestion(boolean resetPassChance) {
        stopQuestionTimer();
        tries = 0;
        correctAnswer = false;
        boardReadyForMove = false;
        setBoardReady(false);
        wrongAnswersForQuestion = 0;
        hintUsedForQuestion = false;
        drawScoreAddedForQuestion = false;
        if (resetPassChance) {
            passUsedForQuestion = false;
        }
        passButton.setEnabled(!passUsedForQuestion);
        hintButton.setEnabled(true);
        answerTextField.setEnabled(true);
        answerTextField.setForeground(TEXT_COLOR);
        checkButton.setEnabled(true);
        if (bursaRadioButton.isSelected()) {
            currentQuestion = questionBank.getRandomQuestion("BURSA");
        } else if (technologyRadioButton.isSelected()) {
            currentQuestion = questionBank.getRandomQuestion("TEKNOLOJI");
        } else {
            currentQuestion = questionBank.getRandomQuestion();
        }
        questionTextArea.setText(currentQuestion.getQuestion());
        answerTextField.setText("");
        messageTextField.setText(passUsedForQuestion ? "Pas hakkı kullanıldı" : "Soruyu cevapla");
        updateTurnLabel();
        startQuestionTimer();
        answerTextField.requestFocusInWindow();
    }

    private void checkButtonActionPerformed() {
        if (currentQuestion == null || !gameState.isGameStarted()) {
            return;
        }
        String userAnswer = answerTextField.getText().trim().toUpperCase(new Locale("tr", "TR"));
        if (currentQuestion.checkAnswer(userAnswer)) {
            answerTextField.setForeground(SUCCESS_COLOR);
            grantMoveAfterAnswer(userAnswer + " - DOĞRU. HAMLE YAP.", "Doğru Yanıt");
        } else {
            handleWrongAnswer();
        }
    }

    private void handleWrongAnswer() {
        stopQuestionTimer();
        answerTextField.setForeground(ERROR_COLOR);
        wrongAnswersForQuestion++;
        correctAnswer = false;
        boardReadyForMove = false;
        setBoardReady(false);

        transferTurnAfterWrongAnswer();
    }

    private void hintButtonActionPerformed() {
        if (!gameState.isGameStarted() || currentQuestion == null || correctAnswer || hintUsedForQuestion) {
            return;
        }
        String answer = currentQuestion.getAnswer();
        String compactAnswer = answer.replace(" ", "");
        if (compactAnswer.length() == 0) {
            return;
        }
        hintUsedForQuestion = true;
        hintButton.setEnabled(false);
        questionTextArea.setText(currentQuestion.getQuestion()
                + " | IPUCU: Cevap '" + compactAnswer.charAt(0)
                + "' ile baslar, " + compactAnswer.length() + " karakter.");
        messageTextField.setText("Ipucu kullanildi");
        answerTextField.requestFocusInWindow();
    }

    private void grantMoveAfterAnswer(String answerMessage, String statusMessage) {
        stopQuestionTimer();
        answerTextField.setText(answerMessage);
        questionTextArea.setText("Doğru Cevap: " + currentQuestion.getAnswer());
        messageTextField.setText(statusMessage);
        correctAnswer = true;
        boardReadyForMove = true;
        setBoardReady(true);
        checkButton.setEnabled(false);
        hintButton.setEnabled(false);
        passButton.setEnabled(false);
        answerTextField.setEnabled(false);
        updateTurnLabel();
    }

    private void showCorrectAnswerThenAskNextQuestion() {
        final String answer = correctAnswerToShowAfterComputerMove;
        correctAnswerToShowAfterComputerMove = null;
        questionTextArea.setText("Doğru Cevap: " + answer);
        answerTextField.setForeground(TEXT_COLOR);
        answerTextField.setText("");
        answerTextField.setEnabled(false);
        checkButton.setEnabled(false);
        hintButton.setEnabled(false);
        passButton.setEnabled(false);
        messageTextField.setText("Bilgisayar hamlesi tamamlandı");
        Timer timer = new Timer(1800, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gameState.isGameStarted() && !gameState.isGameOver()
                        && getCurrentPlayer() instanceof HumanPlayer) {
                    askQuestion();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void transferTurnAfterWrongAnswer() {
        final String answer = currentQuestion.getAnswer();
        questionTextArea.setText("Yanlış Yanıt. Doğru Cevap: " + answer);
        answerTextField.setText("Hak rakibe geçti");
        answerTextField.setEnabled(false);
        checkButton.setEnabled(false);
        hintButton.setEnabled(false);
        passButton.setEnabled(false);
        messageTextField.setText("Yanlış Yanıt");
        gameState.switchTurn();
        updateTurnLabel();

        if (onePlayerRadioButton.isSelected() && getCurrentPlayer() instanceof ComputerPlayer) {
            correctAnswerToShowAfterComputerMove = answer;
            Timer timer = new Timer(900, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (gameState.isGameStarted() && !gameState.isGameOver()) {
                        computerTurn();
                    }
                }
            });
            timer.setRepeats(false);
            timer.start();
            return;
        }

        Timer timer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gameState.isGameStarted() && !gameState.isGameOver()
                        && getCurrentPlayer() instanceof HumanPlayer) {
                    askQuestion();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void passButtonActionPerformed() {
        if (!gameState.isGameStarted() || currentQuestion == null || correctAnswer || passUsedForQuestion) {
            return;
        }
        stopQuestionTimer();
        passUsedForQuestion = true;
        boardReadyForMove = false;
        setBoardReady(false);
        passButton.setEnabled(false);
        answerTextField.setEnabled(false);
        hintButton.setEnabled(false);
        checkButton.setEnabled(false);
        questionTextArea.setText("Doğru Cevap: " + currentQuestion.getAnswer());
        answerTextField.setForeground(TEXT_COLOR);
        answerTextField.setText("PAS kullanıldı");
        messageTextField.setText("Yeni soru geliyor");
        Timer timer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (gameState.isGameStarted() && !gameState.isGameOver()) {
                    askQuestion(false);
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void startQuestionTimer() {
        secondsLeft = QUESTION_TIME_LIMIT;
        updateTimerLabel();
        questionCountdownTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                secondsLeft--;
                updateTimerLabel();
                if (secondsLeft <= 0) {
                    stopQuestionTimer();
                    handleQuestionTimeout();
                }
            }
        });
        questionCountdownTimer.start();
    }

    private void stopQuestionTimer() {
        if (questionCountdownTimer != null) {
            questionCountdownTimer.stop();
            questionCountdownTimer = null;
        }
    }

    private void updateTimerLabel() {
        timerLabel.setText("SURE " + secondsLeft);
        if (secondsLeft <= 5) {
            timerLabel.setForeground(Theme.AMBER);
            timerLabel.setBorder(new RoundedBorder(Theme.AMBER, Theme.RADIUS, 6, 12, 6, 12));
        } else {
            timerLabel.setForeground(Theme.BONE);
            timerLabel.setBorder(new RoundedBorder(Theme.BORDER, Theme.RADIUS, 6, 12, 6, 12));
        }
    }

    private void handleQuestionTimeout() {
        if (!gameState.isGameStarted() || correctAnswer || currentQuestion == null) {
            return;
        }
        answerTextField.setForeground(ERROR_COLOR);
        answerTextField.setText("SURE DOLDU");
        messageTextField.setText("Sure Doldu");
        transferTurnAfterWrongAnswer();
    }

    private void highlightWinningCells(char mark) {
        List<Integer> winningCells = gameBoard.getWinningCells(mark);
        for (Integer index : winningCells) {
            boxTextField[index.intValue()].setBackground(new Color(128, 82, 255, 72));
        }
    }

    private Player getCurrentPlayer() {
        return gameState.getCurrentMark() == GameBoard.PLAYER_X ? xPlayer : oPlayer;
    }

    private int getSelectedGridSize() {
        if (size5RadioButton.isSelected()) {
            return 5;
        }
        if (size4RadioButton.isSelected()) {
            return 4;
        }
        return 3;
    }

    private int getCellPixelSize(int size) {
        if (size == 4) {
            return 72;
        }
        if (size == 5) {
            return 58;
        }
        return 92;
    }

    private int getCellFontSize(int size) {
        if (size == 4) {
            return 42;
        }
        if (size == 5) {
            return 34;
        }
        return 48;
    }

    private void clearBoxes() {
        for (int i = 0; i < boxTextField.length; i++) {
            boxTextField[i].setText("");
            boxTextField[i].setBackground(PANEL_BACKGROUND);
            boxTextField[i].setForeground(PRIMARY_DARK);
        }
    }

    private void setBoardReady(boolean ready) {
        boardReadyForMove = ready;
        gamePanel.setBorder(new RoundedBorder(ready ? Theme.PLUM : Theme.BORDER,
                Theme.RADIUS, 12, 12, 12, 12));
        gamePanel.setCursor(Cursor.getPredefinedCursor(ready ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
        for (int i = 0; i < boxTextField.length; i++) {
            boxTextField[i].setCursor(Cursor.getPredefinedCursor(ready ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
            if (boxTextField[i].getText().length() == 0) {
                boxTextField[i].setBackground(ready ? new Color(128, 82, 255, 38) : PANEL_BACKGROUND);
            }
        }
        gamePanel.repaint();
    }

    private void refreshScoreLabels() {
        xWinsLabel.setText("X Kazandı: " + gameState.getXWins());
        oWinsLabel.setText("O Kazandı: " + gameState.getOWins());
        drawsLabel.setText("Beraberlik: " + gameState.getDraws());
    }

    private void addMatchHistory(String result) {
        String record = result + " | " + getSelectedGridSize() + "x" + getSelectedGridSize()
                + " | " + getSelectedModeName()
                + " | " + getSelectedDifficultyName();
        int limit = Math.min(matchHistory.length - 1, matchHistoryCount);
        for (int i = limit; i > 0; i--) {
            matchHistory[i] = matchHistory[i - 1];
        }
        matchHistory[0] = record;
        if (matchHistoryCount < matchHistory.length) {
            matchHistoryCount++;
        }
        refreshHistoryLabels();
    }

    private void refreshHistoryLabels() {
        for (int i = 0; i < historyLabels.length; i++) {
            if (historyLabels[i] == null) {
                continue;
            }
            if (i < matchHistoryCount && matchHistory[i] != null) {
                historyLabels[i].setText((i + 1) + ". " + matchHistory[i]);
                historyLabels[i].setForeground(i == 0 ? Theme.BONE : Theme.ASH);
            } else {
                historyLabels[i].setText("-");
                historyLabels[i].setForeground(Theme.SMOKE);
            }
        }
    }

    private String getSelectedModeName() {
        return onePlayerRadioButton.isSelected() ? "1P" : "2P";
    }

    private String getSelectedDifficultyName() {
        if (twoPlayersRadioButton.isSelected()) {
            return "Human";
        }
        if (randomRadioButton.isSelected()) {
            return "Easy";
        }
        if (smartRadioButton.isSelected()) {
            return "Hard";
        }
        return "Normal";
    }

    private void setQuestionControlsVisible(boolean visible) {
        questionTextArea.setVisible(visible);
        answerTextField.setVisible(visible);
        checkButton.setVisible(visible);
        hintButton.setVisible(visible);
        passButton.setVisible(visible);
        timerLabel.setVisible(visible);
    }

    private void setOnePlayerOptionsEnabled(boolean enabled) {
        youFirstRadioButton.setEnabled(enabled);
        computerFirstRadioButton.setEnabled(enabled);
        randomRadioButton.setEnabled(enabled);
        normalRadioButton.setEnabled(enabled);
        smartRadioButton.setEnabled(enabled);
        refreshOptionStyles();
    }

    private void setSetupControlsEnabled(boolean enabled) {
        twoPlayersRadioButton.setEnabled(enabled);
        onePlayerRadioButton.setEnabled(enabled);
        size3RadioButton.setEnabled(enabled);
        size4RadioButton.setEnabled(enabled);
        size5RadioButton.setEnabled(enabled);
        allQuestionsRadioButton.setEnabled(enabled);
        bursaRadioButton.setEnabled(enabled);
        technologyRadioButton.setEnabled(enabled);
        if (enabled && onePlayerRadioButton.isSelected()) {
            setOnePlayerOptionsEnabled(true);
        } else {
            setOnePlayerOptionsEnabled(false);
        }
        refreshOptionStyles();
    }

    private void preparePanel(JPanel panel, Dimension size) {
        panel.setPreferredSize(size);
        panel.setBackground(PANEL_BACKGROUND);
        panel.setOpaque(false);
        panel.setBorder(new RoundedBorder(Theme.BORDER, Theme.RADIUS, 8, 12, 8, 12));
        panel.setLayout(new GridBagLayout());
    }

    private void stylePanelTitle(JLabel label) {
        label.setForeground(Theme.ASH);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private void styleScoreLabel(JLabel label) {
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private void styleRadio(JRadioButton radioButton) {
        radioButton.setPreferredSize(new Dimension(210, 32));
        radioButton.setOpaque(true);
        radioButton.setContentAreaFilled(true);
        radioButton.setBorderPainted(true);
        radioButton.setBackground(PANEL_BACKGROUND);
        radioButton.setForeground(Theme.ASH);
        radioButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        radioButton.setFocusPainted(false);
        radioButton.setBorder(new RoundedBorder(new Color(255, 255, 255, 22), 18, 4, 8, 4, 8));
        radioButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                refreshOptionStyles();
            }
        });
    }

    private void refreshOptionStyles() {
        applyRadioState(twoPlayersRadioButton);
        applyRadioState(onePlayerRadioButton);
        applyRadioState(youFirstRadioButton);
        applyRadioState(computerFirstRadioButton);
        applyRadioState(randomRadioButton);
        applyRadioState(normalRadioButton);
        applyRadioState(smartRadioButton);
        applyRadioState(size3RadioButton);
        applyRadioState(size4RadioButton);
        applyRadioState(size5RadioButton);
        applyRadioState(allQuestionsRadioButton);
        applyRadioState(bursaRadioButton);
        applyRadioState(technologyRadioButton);
    }

    private void applyRadioState(JRadioButton radioButton) {
        if (radioButton == null) {
            return;
        }
        if (!radioButton.isEnabled()) {
            radioButton.setBackground(new Color(18, 20, 29));
            radioButton.setForeground(new Color(120, 124, 138));
            radioButton.setBorder(new RoundedBorder(new Color(255, 255, 255, 14), 18, 4, 8, 4, 8));
            return;
        }
        if (radioButton.isSelected()) {
            radioButton.setBackground(new Color(128, 82, 255, 95));
            radioButton.setForeground(Theme.BONE);
            radioButton.setBorder(new RoundedBorder(Theme.PLUM, 18, 4, 8, 4, 8));
        } else {
            radioButton.setBackground(Theme.PANEL_ALT);
            radioButton.setForeground(Theme.ASH);
            radioButton.setBorder(new RoundedBorder(new Color(255, 255, 255, 28), 18, 4, 8, 4, 8));
        }
    }

    private void styleButton(JButton button, boolean primary) {
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(primary ? Color.WHITE : Theme.BONE);
        button.setBackground(primary ? PRIMARY : Theme.PANEL_ALT);
        button.setBorder(new RoundedBorder(primary ? PRIMARY : Theme.BORDER, Theme.RADIUS, 6, 16, 6, 16));
    }

    private void updateTurnLabel() {
        if (gameState.isGameStarted()) {
            turnLabel.setText("Sıra " + gameState.getCurrentMark() + "'te");
        } else {
            turnLabel.setText("Sıra X'te");
        }
    }

    private GridBagConstraints constraints(int gridx, int gridy) {
        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = gridx;
        gridConstraints.gridy = gridy;
        return gridConstraints;
    }

    private void packAndCenter() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((int) (0.5 * (screenSize.width - getWidth())),
                (int) (0.5 * (screenSize.height - getHeight())), getWidth(), getHeight());
    }

    private void exitForm(WindowEvent evt) {
        System.exit(0);
    }
}
