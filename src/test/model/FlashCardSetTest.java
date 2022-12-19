package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlashCardSetTest {
    private FlashCardSet testSet;

    @BeforeEach
    void runBefore() {
        testSet = new FlashCardSet("testSet");
    }

    @Test
    void testConstructor() {
        assertEquals(0,testSet.getSize());
        assertEquals("testSet", testSet.getName());
    }

    @Test
    void testSetName() {
        assertEquals("testSet", testSet.getName());
        testSet.setName("newSet");
        assertEquals("newSet", testSet.getName());
    }

    @Test
    void testAdd() {
        FlashCard testCard = new FlashCard();
        testSet.addFlashCard(testCard);

        assertEquals(1,testSet.getSize());
        assertEquals(testCard, testSet.getFlashCard(0));

        FlashCard testCard2 = new FlashCard();
        testSet.addFlashCard(testCard2);

        assertEquals(2,testSet.getSize());
        assertEquals(testCard2,testSet.getFlashCard(1));


        FlashCard testCard3 = new FlashCard();
        testSet.addFlashCard(testCard3, 2); // Adding flashcard to last index position

        assertEquals(3, testSet.getSize());
        assertEquals(testCard, testSet.getFlashCard(0)); // checking that no cards move
        assertEquals(testCard2,testSet.getFlashCard(1));
        assertEquals(testCard3, testSet.getFlashCard(2));

        List<FlashCard> testList = testSet.getFlashCardList(); // checking List getter is same as list

        assertEquals(3, testList.size());
        assertEquals(testCard, testList.get(0));
        assertEquals(testCard2,testList.get(1));
        assertEquals(testCard3, testList.get(2));


        testSet.addFlashCard(testCard);  // adding duplicate card
        assertEquals(testCard,testSet.getFlashCard(3));
    }

    @Test
    void testAddFirst() {
        FlashCard testCard = new FlashCard();
        FlashCard testCard2 = new FlashCard();
        FlashCard testCard3 = new FlashCard();
        FlashCard testCard4 = new FlashCard();

        testSet.addFlashCard(testCard);       // at index 0
        testSet.addFlashCard(testCard2);      // at index 1
        testSet.addFlashCard(testCard3);      // at index 2
        testSet.addFlashCard(testCard4,0); // Adding flashcard to first index position

        assertEquals(4,testSet.getSize());
        assertEquals(testCard4,testSet.getFlashCard(0));
        assertEquals(testCard, testSet.getFlashCard(1)); // checking that cards that are at or above index move
        assertEquals(testCard2, testSet.getFlashCard(2)); // moved from 1 to 2
        assertEquals(testCard3,testSet.getFlashCard(3)); // moved from 2 to 3
    }

    @Test
    void testClearSet() {
        FlashCard testCard = new FlashCard();
        FlashCard testCard2 = new FlashCard();
        FlashCard testCard3 = new FlashCard();
        FlashCard testCard4 = new FlashCard();

        testSet.addFlashCard(testCard);
        testSet.addFlashCard(testCard2);
        testSet.addFlashCard(testCard3);
        testSet.addFlashCard(testCard4);


        assertEquals(4,testSet.getSize());

        testSet.clearSet();
        assertEquals(0,testSet.getSize());
    }

    @Test
    void testRemoveSet() {
        FlashCard testCard = new FlashCard();
        FlashCard testCard2 = new FlashCard();

        testSet.addFlashCard(testCard);
        testSet.addFlashCard(testCard2);

        testSet.removeFlashCard(0);

        assertEquals(1,testSet.getSize());
        assertEquals(testCard2,testSet.getFlashCard(0));

        testSet.removeFlashCard(0);
        assertEquals(0,testSet.getSize());
    }

    @Test
    void testRemoveMastered() {
        FlashCard testCard = new FlashCard();
        FlashCard testCard2 = new FlashCard();
        FlashCard testCard3 = new FlashCard();
        FlashCard testCard4 = new FlashCard();
        FlashCard testCard5 = new FlashCard();

        testSet.addFlashCard(testCard);
        testSet.addFlashCard(testCard2);
        testSet.addFlashCard(testCard3);
        testSet.addFlashCard(testCard4);
        testSet.addFlashCard(testCard5);

        testCard2.setMastered();
        testCard3.setMastered();

        FlashCardSet newSet = new FlashCardSet(testSet.notMasteredSet(), "newSet");

        assertEquals("newSet", newSet.getName());
        assertEquals(3, newSet.getSize());
        assertEquals(testCard, newSet.getFlashCard(0));
        assertEquals(testCard4, newSet.getFlashCard(1));
        assertEquals(testCard5, newSet.getFlashCard(2));
    }
}
