/*
 * OOP Principles: Encapsulation protects game status and scores, and Single
 * Responsibility keeps turn/result tracking separate from UI and board logic.
 */

public class GameState {
    public enum Result {
        NONE, X_WINS, O_WINS, DRAW
    }

    private boolean xTurn;
    private boolean gameOver;
    private boolean gameStarted;
    private Result result;
    private int xWins;
    private int oWins;
    private int draws;

    public GameState() {
        result = Result.NONE;
    }

    public void resetForNewGame(boolean xGoesFirst) {
        xTurn = xGoesFirst;
        gameOver = false;
        gameStarted = true;
        result = Result.NONE;
    }

    public void switchTurn() {
        xTurn = !xTurn;
    }

    public char getCurrentMark() {
        return xTurn ? GameBoard.PLAYER_X : GameBoard.PLAYER_O;
    }

    public void setXWins() {
        xWins++;
        result = Result.X_WINS;
        gameOver = true;
    }

    public void setOWins() {
        oWins++;
        result = Result.O_WINS;
        gameOver = true;
    }

    public void setDraw() {
        draws++;
        result = Result.DRAW;
        gameOver = true;
    }

    public void addDrawScore() {
        draws++;
    }

    public void resetScores() {
        xWins = 0;
        oWins = 0;
        draws = 0;
    }

    public void stopGame() {
        gameStarted = false;
    }

    public boolean isXTurn() {
        return xTurn;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public Result getResult() {
        return result;
    }

    public int getXWins() {
        return xWins;
    }

    public int getOWins() {
        return oWins;
    }

    public int getDraws() {
        return draws;
    }
}
