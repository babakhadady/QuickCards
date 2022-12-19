package persistence;

import model.FlashCard;
import model.FlashCardSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkFlashCard(String question, String answer, Boolean mastered, FlashCard flashcard) {
        assertEquals(question, flashcard.getQuestion());
        assertEquals(answer, flashcard.getAnswer());
        assertEquals(mastered, flashcard.isMastered());
    }

    protected void checkFlashCardSet(String name, int size, FlashCardSet flashCardSet) {
        assertEquals(name, flashCardSet.getName());
        assertEquals(size, flashCardSet.getSize());
    }
}
