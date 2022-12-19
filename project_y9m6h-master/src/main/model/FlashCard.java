package model;

import org.json.JSONObject;
import persistence.Writable;

// represents an individual flashcard with a question and answer, that belongs to a set of flashcards
public class FlashCard implements Writable {

    private String question;
    private String answer;
    private boolean mastered;

    // EFFECTS: creates a new empty flashcard (no question or answer) and marks mastered as false
    public FlashCard() {
        mastered = false;
    }

    // EFFECTS: creates a new flashcard with given question and answer, and marks mastered as false
    public FlashCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        mastered = false;
    }

    public boolean isMastered() {
        return mastered;
    }

    // MODIFIES: this
    // REQUIRES: question is not an empty string
    // EFFECTS: set the question to given string
    public void setQuestion(String question) {
        this.question = question;
    }

    // MODIFIES: this
    // REQUIRES: answer is not an empty string
    // EFFECTS: set the answer to given string
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    // MODIFIES: this
    // EFFECTS: sets the flashcard to mastered
    public void setMastered() {
        mastered = true;
    }

    // MODIFIES: this
    // EFFECTS: changes mastered to !mastered
    public void updateMastered() {
        mastered = !mastered;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    // EFFECTS: creates a new json object representing a flashcard
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("question", question);
        json.put("answer", answer);
        json.put("mastered", mastered);
        return json;
    }

    @Override
    public String toString() {
        return question;
    }
}
