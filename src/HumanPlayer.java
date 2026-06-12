/*
 * OOP Principles: Inheritance specializes Player for mouse-driven users, and
 * Single Responsibility keeps human move behavior simple.
 */

public class HumanPlayer extends Player {
    public HumanPlayer(String name, char mark) {
        super(name, mark);
    }

    public int chooseMove(GameBoard board) {
        return -1;
    }
}
