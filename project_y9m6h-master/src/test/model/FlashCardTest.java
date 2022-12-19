package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlashCardTest {
    private FlashCard testCard;

    @BeforeEach
    void runBefore() {
        testCard = new FlashCard();
    }

    @Test
    void testConstructor() {
        assertFalse(testCard.isMastered());
        assertNull(testCard.getAnswer());
        assertNull(testCard.getQuestion());
    }

    @Test
    void testConstructorTwo() {
        FlashCard newCard = new FlashCard("hello", "goodbye");
        assertEquals("hello", newCard.getQuestion());
        assertEquals("goodbye", newCard.getAnswer());
        assertFalse(newCard.isMastered());
    }

    @Test
    void testUpdateMastered() {
        testCard.updateMastered();
        assertTrue(testCard.isMastered());
        testCard.updateMastered();
        assertFalse(testCard.isMastered());
    }

    @Test
    void testQuestion() {
        testCard.setQuestion("What is the day today");
        assertEquals("What is the day today", testCard.getQuestion());

        testCard.setQuestion("What is the day tomorrow");
        assertEquals("What is the day tomorrow", testCard.getQuestion());
    }

    @Test
    void testAnswer() {
        testCard.setAnswer("Today is Monday");
        assertEquals("Today is Monday", testCard.getAnswer());

        testCard.setAnswer("Tomorrow is Tuesday");
        assertEquals("Tomorrow is Tuesday", testCard.getAnswer());
    }

    @Test
    void testMastered() {
        assertFalse(testCard.isMastered());
        testCard.setMastered();
        assertTrue(testCard.isMastered());
    }
}