/*
 * OOP Principles: Interface implementation supplies smart AI behavior, and
 * Encapsulation keeps minimax helper logic private.
 */

import java.util.List;

public class MinimaxStrategy implements AIStrategy {
    public int chooseMove(GameBoard board, char aiMark) {
        List<Integer> emptyCells = board.getEmptyCells();
        if (emptyCells.isEmpty()) {
            return -1;
        }

        int bestMove = emptyCells.get(0).intValue();
        int bestScore = Integer.MIN_VALUE;
        int maxDepth = getDepthLimit(board.getSize());

        for (Integer moveObject : emptyCells) {
            int move = moveObject.intValue();
            board.makeMove(move, aiMark);
            int score = minimax(board, getOpponent(aiMark), aiMark, 1, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
            board.undoMove(move);
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private int minimax(GameBoard board, char currentMark, char aiMark, int depth, int maxDepth, int alpha, int beta) {
        char humanMark = getOpponent(aiMark);

        if (board.checkWin(aiMark)) {
            return 10 - depth;
        }
        if (board.checkWin(humanMark)) {
            return -10 + depth;
        }
        if (board.isFull() || depth >= maxDepth) {
            return 0;
        }

        List<Integer> emptyCells = board.getEmptyCells();
        if (currentMark == aiMark) {
            int bestScore = Integer.MIN_VALUE;
            for (Integer moveObject : emptyCells) {
                int move = moveObject.intValue();
                board.makeMove(move, currentMark);
                int score = minimax(board, getOpponent(currentMark), aiMark, depth + 1, maxDepth, alpha, beta);
                board.undoMove(move);
                bestScore = Math.max(bestScore, score);
                alpha = Math.max(alpha, bestScore);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestScore;
        }

        int bestScore = Integer.MAX_VALUE;
        for (Integer moveObject : emptyCells) {
            int move = moveObject.intValue();
            board.makeMove(move, currentMark);
            int score = minimax(board, getOpponent(currentMark), aiMark, depth + 1, maxDepth, alpha, beta);
            board.undoMove(move);
            bestScore = Math.min(bestScore, score);
            beta = Math.min(beta, bestScore);
            if (beta <= alpha) {
                break;
            }
        }
        return bestScore;
    }

    private int getDepthLimit(int size) {
        if (size == 3) {
            return 9;
        }
        if (size == 4) {
            return 6;
        }
        return 4;
    }

    private char getOpponent(char mark) {
        return mark == GameBoard.PLAYER_X ? GameBoard.PLAYER_O : GameBoard.PLAYER_X;
    }
}
