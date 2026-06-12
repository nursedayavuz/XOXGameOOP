/*
 * OOP Principles: Inheritance specializes Player for AI, Interface usage injects
 * AIStrategy behavior, and Polymorphism allows strategies to be swapped.
 */

public class ComputerPlayer extends Player {
    private AIStrategy strategy;

    public ComputerPlayer(String name, char mark, AIStrategy strategy) {
        super(name, mark);
        this.strategy = strategy;
    }

    public int chooseMove(GameBoard board) {
        return strategy.chooseMove(board, mark);
    }

    public void setStrategy(AIStrategy strategy) {
        this.strategy = strategy;
    }
}
