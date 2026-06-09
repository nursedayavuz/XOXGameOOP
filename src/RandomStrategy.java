/*
 * OOP Principles: Interface implementation supplies one AI behavior, while
 * Single Responsibility keeps random move selection isolated.
 */

import java.util.List;
import java.util.Random;

public class RandomStrategy implements AIStrategy {
    private Random random = new Random();

    public int chooseMove(GameBoard board, char aiMark) {
        List<Integer> emptyCells = board.getEmptyCells();
        if (emptyCells.isEmpty()) {
            return -1;
        }
        return emptyCells.get(random.nextInt(emptyCells.size())).intValue();
    }
}
