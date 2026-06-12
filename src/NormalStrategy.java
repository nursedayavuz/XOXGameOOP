/*
 * OOP Principles: Strategy implementation composes existing AI behaviors to
 * create a medium difficulty without changing ComputerPlayer.
 */

import java.util.Random;

public class NormalStrategy implements AIStrategy {
    private Random random = new Random();
    private AIStrategy randomStrategy = new RandomStrategy();
    private AIStrategy smartStrategy = new MinimaxStrategy();

    public int chooseMove(GameBoard board, char aiMark) {
        if (random.nextInt(100) < 55) {
            return smartStrategy.chooseMove(board, aiMark);
        }
        return randomStrategy.chooseMove(board, aiMark);
    }
}
