/*
 * OOP Principles: Encapsulation stores question data privately, and Single
 * Responsibility keeps answer checking inside the question model.
 */

import java.util.Locale;

public class Question {
    private String question;
    private String answer;
    private String category;

    public Question(String question, String answer, String category) {
        this.question = question;
        this.answer = answer;
        this.category = category;
    }

    public boolean checkAnswer(String input) {
        if (input == null) {
            return false;
        }
        return normalize(input).equals(normalize(answer));
    }

    private String normalize(String value) {
        return value.trim().toUpperCase(new Locale("tr", "TR"));
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getCategory() {
        return category;
    }
}
