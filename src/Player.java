/*
 * OOP Principles: Inheritance begins with this abstract base class, while
 * Polymorphism lets the game use HumanPlayer and ComputerPlayer through Player.
 */

public abstract class Player {
    protected String name;
    protected char mark;

    public Player(String name, char mark) {
        this.name = name;
        this.mark = mark;
    }

    public abstract int chooseMove(GameBoard board);

    public String getName() {
        return name;
    }

    public char getMark() {
        return mark;
    }
}
