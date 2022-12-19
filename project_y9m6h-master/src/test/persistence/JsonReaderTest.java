package persistence;

import model.FlashCard;
import model.FlashCardSet;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderFileDoesntExist() {
        JsonReader reader = new JsonReader("./data/doesntexist.json");
        try {
            List<FlashCardSet> sets = reader.read();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNoSets() {
        JsonReader reader = new JsonReader("./data/testReaderNoSets.json");
        try {
            List<FlashCardSet> sets = reader.read();
            assertEquals(0,sets.size());
        } catch (IOException e) {
            fail("Couldn't read from the file");
        }
    }

    @Test
    void testReaderSets() {
        JsonReader reader = new JsonReader("./data/testReaderSets.json");
        try {
            List<FlashCardSet> sets = reader.read();
            assertEquals(3,sets.size());

            FlashCardSet testSet1 = sets.get(0);
            checkFlashCardSet("testSet1",2, testSet1);
            checkFlashCard("hello", "goodbye", false, testSet1.getFlashCard(0));
            checkFlashCard("good morning", "good night", true, testSet1.getFlashCard(1));

            FlashCardSet testSet2 = sets.get(1);
            checkFlashCardSet("testSet2",1, testSet2);
            checkFlashCard("hi", "bye", true, testSet2.getFlashCard(0));

            FlashCardSet testSet3 = sets.get(2);
            checkFlashCardSet("testSet3",0, testSet3);
        } catch (IOException e) {
            fail("Couldn't read from given file");
        }
    }
}
