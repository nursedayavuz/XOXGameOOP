/*
 * OOP Principles: Interface defines a shared AI contract, enabling Polymorphism
 * between RandomStrategy and MinimaxStrategy.
 */

public interface AIStrategy {
    int chooseMove(GameBoard board, char aiMark);
}
