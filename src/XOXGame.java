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
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Locale;
import javax.swing.BorderFactory;
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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class XOXGame extends JFrame {
    private static final Color APP_BACKGROUND = new Color(245, 247, 251);
    private static final Color PANEL_BACKGROUND = Color.WHITE;
    private static final Color PRIMARY = new Color(31, 78, 121);
    private static final Color PRIMARY_DARK = new Color(16, 42, 67);
    private static final Color ACCENT = new Color(218, 165, 32);
    private static final Color GRID_COLOR = new Color(22, 92, 170);
    private static final Color TEXT_COLOR = new Color(32, 43, 54);
    private static final Color ERROR_COLOR = new Color(180, 35, 24);
    private static final Color SUCCESS_COLOR = new Color(18, 122, 74);

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
    private JButton passButton = new JButton();
    private GraphicsPanel displayPanel = new GraphicsPanel();

    private boolean canClick = false;
    private int tries;
    private boolean correctAnswer;
    private int wrongAnswersForQuestion;
    private boolean drawScoreAddedForQuestion;
    private boolean passUsedForQuestion;
    private String correctAnswerToShowAfterComputerMove;
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
        setTitle("Tic Tac Toe");
        getContentPane().setBackground(APP_BACKGROUND);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                exitForm(evt);
            }
        });
        getContentPane().setLayout(new GridBagLayout());
        buildInterface();
        messageTextField.setText("Game Stopped");
        setOnePlayerOptionsEnabled(false);
        setQuestionControlsVisible(false);
        refreshScoreLabels();
        updateTurnLabel();
        packAndCenter();
    }

    private void buildInterface() {
        GridBagConstraints gridConstraints;

        messageTextField.setPreferredSize(new Dimension(350, 64));
        messageTextField.setEditable(false);
        messageTextField.setBackground(PRIMARY_DARK);
        messageTextField.setForeground(Color.WHITE);
        messageTextField.setHorizontalAlignment(SwingConstants.CENTER);
        messageTextField.setFont(new Font("Segoe UI", Font.BOLD, 28));
        messageTextField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(ACCENT, 2),
                new EmptyBorder(8, 12, 8, 12)));
        gridConstraints = constraints(0, 0);
        gridConstraints.insets = new Insets(10, 10, 10, 10);
        getContentPane().add(messageTextField, gridConstraints);

        turnLabel.setPreferredSize(new Dimension(160, 32));
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        turnLabel.setForeground(PRIMARY_DARK);
        turnLabel.setOpaque(true);
        turnLabel.setBackground(new Color(232, 238, 246));
        turnLabel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(198, 210, 224), 1),
                new EmptyBorder(5, 12, 5, 12)));
        gridConstraints = constraints(1, 0);
        gridConstraints.insets = new Insets(10, 10, 10, 10);
        getContentPane().add(turnLabel, gridConstraints);

        gamePanel.setPreferredSize(new Dimension(280, 280));
        gamePanel.setBackground(APP_BACKGROUND);
        gamePanel.setLayout(new GridBagLayout());
        gridConstraints = constraints(0, 1);
        gridConstraints.gridheight = 3;
        gridConstraints.insets = new Insets(10, 10, 10, 10);
        getContentPane().add(gamePanel, gridConstraints);
        rebuildGameGrid(3);

        buildPlayersPanel();
        buildFirstPanel();
        buildComputerPanel();
        buildGridSizePanel();
        buildScorePanel();
        buildCategoryPanel();
        buildButtonsPanel();
        buildQuestionControls();
    }

    private void buildPlayersPanel() {
        preparePanel(playersPanel, new Dimension(200, 92));
        GridBagConstraints gridConstraints = constraints(1, 1);
        gridConstraints.insets = new Insets(5, 10, 5, 10);
        getContentPane().add(playersPanel, gridConstraints);

        twoPlayersRadioButton.setText("Two Players");
        styleRadio(twoPlayersRadioButton);
        twoPlayersRadioButton.setSelected(true);
        playersButtonGroup.add(twoPlayersRadioButton);
        gridConstraints = constraints(0, 0);
        gridConstraints.anchor = GridBagConstraints.WEST;
        playersPanel.add(twoPlayersRadioButton, gridConstraints);
        twoPlayersRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setOnePlayerOptionsEnabled(false);
            }
        });

        onePlayerRadioButton.setText("One Player");
        styleRadio(onePlayerRadioButton);
        playersButtonGroup.add(onePlayerRadioButton);
        gridConstraints = constraints(0, 1);
        gridConstraints.anchor = GridBagConstraints.WEST;
        playersPanel.add(onePlayerRadioButton, gridConstraints);
        onePlayerRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setOnePlayerOptionsEnabled(true);
            }
        });
    }

    private void buildFirstPanel() {
        preparePanel(firstPanel, new Dimension(200, 92));
        GridBagConstraints gridConstraints = constraints(1, 2);
        gridConstraints.insets = new Insets(5, 10, 5, 10);
        getContentPane().add(firstPanel, gridConstraints);

        youFirstRadioButton.setText("You First");
        styleRadio(youFirstRadioButton);
        youFirstRadioButton.setSelected(true);
        firstButtonGroup.add(youFirstRadioButton);
        gridConstraints = constraints(0, 0);
        gridConstraints.anchor = GridBagConstraints.WEST;
        firstPanel.add(youFirstRadioButton, gridConstraints);

        computerFirstRadioButton.setText("Computer First");
        styleRadio(computerFirstRadioButton);
        firstButtonGroup.add(computerFirstRadioButton);
        gridConstraints = constraints(0, 1);
        gridConstraints.anchor = GridBagConstraints.WEST;
        firstPanel.add(computerFirstRadioButton, gridConstraints);
    }

    private void buildComputerPanel() {
        preparePanel(computerPanel, new Dimension(200, 92));
        GridBagConstraints gridConstraints = constraints(1, 3);
        gridConstraints.insets = new Insets(5, 10, 5, 10);
        getContentPane().add(computerPanel, gridConstraints);

        randomRadioButton.setText("Random Computer");
        styleRadio(randomRadioButton);
        randomRadioButton.setSelected(true);
        computerButtonGroup.add(randomRadioButton);
        gridConstraints = constraints(0, 0);
        gridConstraints.anchor = GridBagConstraints.WEST;
        computerPanel.add(randomRadioButton, gridConstraints);

        smartRadioButton.setText("Smart Computer");
        styleRadio(smartRadioButton);
        computerButtonGroup.add(smartRadioButton);
        gridConstraints = constraints(0, 1);
        gridConstraints.anchor = GridBagConstraints.WEST;
        computerPanel.add(smartRadioButton, gridConstraints);
    }

    private void buildGridSizePanel() {
        preparePanel(gridSizePanel, new Dimension(200, 116));
        GridBagConstraints gridConstraints = constraints(2, 0);
        gridConstraints.gridheight = 2;
        gridConstraints.insets = new Insets(5, 10, 5, 10);
        getContentPane().add(gridSizePanel, gridConstraints);

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

    private void buildScorePanel() {
        preparePanel(scorePanel, new Dimension(220, 136));
        GridBagConstraints gridConstraints = constraints(2, 2);
        gridConstraints.gridheight = 2;
        gridConstraints.insets = new Insets(5, 10, 5, 10);
        getContentPane().add(scorePanel, gridConstraints);

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

    private void buildCategoryPanel() {
        preparePanel(categoryPanel, new Dimension(220, 124));
        GridBagConstraints gridConstraints = constraints(2, 4);
        gridConstraints.insets = new Insets(5, 10, 5, 10);
        getContentPane().add(categoryPanel, gridConstraints);

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

        gridConstraints = constraints(0, 0);
        gridConstraints.anchor = GridBagConstraints.WEST;
        categoryPanel.add(allQuestionsRadioButton, gridConstraints);
        gridConstraints = constraints(0, 1);
        gridConstraints.anchor = GridBagConstraints.WEST;
        categoryPanel.add(bursaRadioButton, gridConstraints);
        gridConstraints = constraints(0, 2);
        gridConstraints.anchor = GridBagConstraints.WEST;
        categoryPanel.add(technologyRadioButton, gridConstraints);
    }

    private void buildButtonsPanel() {
        buttonsPanel.setPreferredSize(new Dimension(200, 86));
        buttonsPanel.setBackground(APP_BACKGROUND);
        buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridConstraints = constraints(1, 4);
        getContentPane().add(buttonsPanel, gridConstraints);

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

    private void buildQuestionControls() {
        questionTextArea.setPreferredSize(new Dimension(700, 58));
        questionTextArea.setFont(new Font("Segoe UI", Font.BOLD, 15));
        questionTextArea.setLineWrap(true);
        questionTextArea.setWrapStyleWord(true);
        questionTextArea.setEditable(false);
        questionTextArea.setBackground(PANEL_BACKGROUND);
        questionTextArea.setForeground(TEXT_COLOR);
        questionTextArea.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(215, 221, 230), 1),
                new EmptyBorder(8, 12, 8, 12)));
        GridBagConstraints gridConstraints = constraints(0, 5);
        gridConstraints.gridwidth = 3;
        gridConstraints.insets = new Insets(10, 10, 4, 10);
        getContentPane().add(questionTextArea, gridConstraints);

        answerTextField.setPreferredSize(new Dimension(420, 34));
        answerTextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        answerTextField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(170, 184, 200), 1),
                new EmptyBorder(4, 8, 4, 8)));
        answerTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkButtonActionPerformed();
            }
        });
        gridConstraints = constraints(0, 6);
        gridConstraints.gridwidth = 2;
        gridConstraints.insets = new Insets(0, 10, 10, 10);
        getContentPane().add(answerTextField, gridConstraints);

        JPanel answerButtonsPanel = new JPanel(new GridBagLayout());
        answerButtonsPanel.setBackground(APP_BACKGROUND);
        checkButton.setText("Check Answer");
        passButton.setText("PAS");
        styleButton(checkButton, true);
        styleButton(passButton, false);
        passButton.setEnabled(false);

        gridConstraints = constraints(0, 0);
        gridConstraints.insets = new Insets(0, 0, 0, 8);
        answerButtonsPanel.add(checkButton, gridConstraints);
        gridConstraints = constraints(1, 0);
        answerButtonsPanel.add(passButton, gridConstraints);

        gridConstraints = constraints(2, 6);
        gridConstraints.insets = new Insets(0, 10, 10, 10);
        getContentPane().add(answerButtonsPanel, gridConstraints);

        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkButtonActionPerformed();
            }
        });
        passButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                passButtonActionPerformed();
            }
        });

        displayPanel.setPreferredSize(new Dimension(700, 100));
        displayPanel.setBackground(APP_BACKGROUND);
        gridConstraints = constraints(0, 7);
        gridConstraints.gridwidth = 3;
        gridConstraints.insets = new Insets(0, 0, 10, 0);
        getContentPane().add(displayPanel, gridConstraints);
    }

    private void rebuildGameGrid(int size) {
        gamePanel.removeAll();
        int cellSize = getCellPixelSize(size);
        boxTextField = new JTextField[size * size];
        gridLabel = new JLabel[2 * (size - 1)];

        for (int i = 0; i < boxTextField.length; i++) {
            final int index = i;
            boxTextField[i] = new JTextField();
            boxTextField[i].setPreferredSize(new Dimension(cellSize, cellSize));
            boxTextField[i].setEditable(false);
            boxTextField[i].setBackground(PANEL_BACKGROUND);
            boxTextField[i].setForeground(PRIMARY_DARK);
            boxTextField[i].setHorizontalAlignment(SwingConstants.CENTER);
            boxTextField[i].setFont(new Font("Segoe UI", Font.BOLD, getCellFontSize(size)));
            boxTextField[i].setBorder(null);
            GridBagConstraints gridConstraints = constraints(2 * (i % size), 2 * (i / size));
            gamePanel.add(boxTextField[i], gridConstraints);
            boxTextField[i].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    boxTextFieldMousePressed(index, e);
                }
            });
        }

        int labelIndex = 0;
        for (int rowLine = 1; rowLine < size; rowLine++) {
            JLabel line = createGridLine(new Dimension(280, 10));
            gridLabel[labelIndex++] = line;
            GridBagConstraints gridConstraints = constraints(0, 2 * rowLine - 1);
            gridConstraints.gridwidth = 2 * size - 1;
            gridConstraints.insets = new Insets(5, 0, 5, 0);
            gamePanel.add(line, gridConstraints);
        }
        for (int colLine = 1; colLine < size; colLine++) {
            JLabel line = createGridLine(new Dimension(10, 280));
            gridLabel[labelIndex++] = line;
            GridBagConstraints gridConstraints = constraints(2 * colLine - 1, 0);
            gridConstraints.gridheight = 2 * size - 1;
            gridConstraints.insets = new Insets(0, 5, 0, 5);
            gamePanel.add(line, gridConstraints);
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
        startStopButton.setText("Start Game");
        if (!gameFinished) {
            messageTextField.setText("Game Stopped");
        }
        gameState.stopGame();
        setSetupControlsEnabled(true);
        exitButton.setEnabled(true);
        canClick = false;
        questionTextArea.setText("");
        answerTextField.setText("");
        passButton.setEnabled(false);
        updateTurnLabel();
    }

    private void configurePlayers() {
        if (twoPlayersRadioButton.isSelected()) {
            xPlayer = new HumanPlayer("Player X", GameBoard.PLAYER_X);
            oPlayer = new HumanPlayer("Player O", GameBoard.PLAYER_O);
            return;
        }

        AIStrategy strategy = randomRadioButton.isSelected() ? new RandomStrategy() : new MinimaxStrategy();
        if (computerFirstRadioButton.isSelected()) {
            xPlayer = new ComputerPlayer("Computer", GameBoard.PLAYER_X, strategy);
            oPlayer = new HumanPlayer("You", GameBoard.PLAYER_O);
        } else {
            xPlayer = new HumanPlayer("You", GameBoard.PLAYER_X);
            oPlayer = new ComputerPlayer("Computer", GameBoard.PLAYER_O, strategy);
        }
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
            return;
        }
        makeMove(index);
    }

    private void makeMove(int index) {
        char currentMark = gameState.getCurrentMark();
        if (!gameBoard.makeMove(index, currentMark)) {
            return;
        }
        boxTextField[index].setText(String.valueOf(currentMark));

        if (gameBoard.checkWin(currentMark)) {
            highlightWinningCells(currentMark);
            if (currentMark == GameBoard.PLAYER_X) {
                gameState.setXWins();
                messageTextField.setText("X wins!");
            } else {
                gameState.setOWins();
                messageTextField.setText("O wins!");
            }
            refreshScoreLabels();
            stopGame(true);
            return;
        }

        if (gameBoard.isFull()) {
            gameState.setDraw();
            messageTextField.setText("It's a draw!");
            refreshScoreLabels();
            stopGame(true);
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
        tries = 0;
        correctAnswer = false;
        wrongAnswersForQuestion = 0;
        drawScoreAddedForQuestion = false;
        if (resetPassChance) {
            passUsedForQuestion = false;
        }
        passButton.setEnabled(!passUsedForQuestion);
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
        answerTextField.setForeground(ERROR_COLOR);
        wrongAnswersForQuestion++;
        correctAnswer = false;

        transferTurnAfterWrongAnswer();
    }

    private void grantMoveAfterAnswer(String answerMessage, String statusMessage) {
        answerTextField.setText(answerMessage);
        questionTextArea.setText("Doğru Cevap: " + currentQuestion.getAnswer());
        messageTextField.setText(statusMessage);
        correctAnswer = true;
        checkButton.setEnabled(false);
        passButton.setEnabled(false);
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
        passUsedForQuestion = true;
        passButton.setEnabled(false);
        answerTextField.setEnabled(false);
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

    private void highlightWinningCells(char mark) {
        List<Integer> winningCells = gameBoard.getWinningCells(mark);
        for (Integer index : winningCells) {
            boxTextField[index.intValue()].setBackground(Color.RED);
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
            return 65;
        }
        if (size == 5) {
            return 55;
        }
        return 80;
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
        }
    }

    private void refreshScoreLabels() {
        xWinsLabel.setText("X Kazandı: " + gameState.getXWins());
        oWinsLabel.setText("O Kazandı: " + gameState.getOWins());
        drawsLabel.setText("Beraberlik: " + gameState.getDraws());
    }

    private void setQuestionControlsVisible(boolean visible) {
        questionTextArea.setVisible(visible);
        answerTextField.setVisible(visible);
        checkButton.setVisible(visible);
        passButton.setVisible(visible);
    }

    private void setOnePlayerOptionsEnabled(boolean enabled) {
        youFirstRadioButton.setEnabled(enabled);
        computerFirstRadioButton.setEnabled(enabled);
        randomRadioButton.setEnabled(enabled);
        smartRadioButton.setEnabled(enabled);
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
    }

    private void preparePanel(JPanel panel, Dimension size) {
        panel.setPreferredSize(size);
        panel.setBackground(PANEL_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(198, 210, 224), 1),
                new EmptyBorder(8, 12, 8, 12)));
        panel.setLayout(new GridBagLayout());
    }

    private void stylePanelTitle(JLabel label) {
        label.setForeground(PRIMARY_DARK);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private void styleScoreLabel(JLabel label) {
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private void styleRadio(JRadioButton radioButton) {
        radioButton.setBackground(PANEL_BACKGROUND);
        radioButton.setForeground(TEXT_COLOR);
        radioButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        radioButton.setFocusPainted(false);
    }

    private void styleButton(JButton button, boolean primary) {
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(primary ? Color.WHITE : PRIMARY_DARK);
        button.setBackground(primary ? PRIMARY : new Color(232, 238, 246));
        button.setBorder(BorderFactory.createCompoundBorder(new LineBorder(primary ? PRIMARY_DARK : new Color(150, 166, 184), 1),
                new EmptyBorder(6, 16, 6, 16)));
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
