package persistence;

import model.FlashCard;
import model.FlashCardSet;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterFileDoesntExist() {
        try {
            JsonWriter writer = new JsonWriter("./data/this\0FileDoesntActuallyExist.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass the test
        }
    }

    @Test
    void testWriterEmptySets() {
        try {
            List<FlashCardSet> sets = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySets.json");
            writer.open();
            writer.write(sets);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySets.json");
            List<FlashCardSet> newSet = reader.read();
            assertEquals(0, newSet.size());
        } catch (IOException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    void testWriterSets() {
        try {
            List<FlashCardSet> sets = new ArrayList<>();

            FlashCardSet testSet1 = new FlashCardSet("testSet1");
            FlashCardSet testSet3 = new FlashCardSet("testSet3");
            FlashCardSet testSet2 = new FlashCardSet("testSet2");
            sets.add(testSet1);
            sets.add(testSet2);
            sets.add(testSet3);

            FlashCard testCard1 = new FlashCard();
            testCard1.setQuestion("hello");
            testCard1.setAnswer("goodbye");
            testSet1.addFlashCard(testCard1);

            FlashCard testCard2 = new FlashCard();
            testCard2.setQuestion("good morning");
            testCard2.setAnswer("good night");
            testCard2.setMastered();
            testSet1.addFlashCard(testCard2);

            FlashCard testCard3 = new FlashCard();
            testCard3.setQuestion("hi");
            testCard3.setAnswer("bye");
            testCard3.setMastered();
            testSet2.addFlashCard(testCard3);

            JsonWriter writer = new JsonWriter("./data/testWriterSets.json");
            writer.open();
            writer.write(sets);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterSets.json");

            List<FlashCardSet> newSets = reader.read();
            assertEquals(3,sets.size());


            FlashCardSet newSet1 = newSets.get(0);
            checkFlashCardSet("testSet1",2, newSet1);
            checkFlashCard("hello", "goodbye", false, newSet1.getFlashCard(0));
            checkFlashCard("good morning", "good night", true, newSet1.getFlashCard(1));

            FlashCardSet newSet2 = newSets.get(1);
            checkFlashCardSet("testSet2",1, newSet2);
            checkFlashCard("hi", "bye", true, newSet2.getFlashCard(0));

            FlashCardSet newSet3 = newSets.get(2);
            checkFlashCardSet("testSet3",0, newSet3);

        } catch (IOException e) {
            fail("Exception should not be thrown");
        }

    }
}
