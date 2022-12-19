package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;


// represents a Set of FlashCards with a given name
public class FlashCardSet implements Writable {

    private List<FlashCard> flashCardList;
    private String name;

    // REQUIRES: name is not the empty string
    // EFFECTS: creates a new empty set of flashcards with given name
    public FlashCardSet(String name) {
        flashCardList = new ArrayList<>();
        this.name = name;
    }

    // REQUIRES: name is not the empty string
    // EFFECTS: creates a new flashcard set of given list with given name
    public FlashCardSet(List<FlashCard> flashCardSet, String name) {
        this.flashCardList = flashCardSet;
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: adds given flashcard to the end of the set
    public void addFlashCard(FlashCard flashCard) {
        flashCardList.add(flashCard);
        Event event = new Event("Added FlashCard with question " + flashCard + " to " + name);
        EventLog.getInstance().logEvent(event);
    }


    // MODIFIES: this
    // REQUIRES: i>= 0, i <= getSize()
    // EFFECTS: adds given flashcard to index i
    public void addFlashCard(FlashCard flashCard, int i) {
        flashCardList.add(i, flashCard);
    }

    // MODIFIES: this
    // EFFECTS: removes every flashcard from the set (creates a new list)
    public void clearSet() {
        flashCardList = new ArrayList<>();
    }


    // MODIFIES: this
    // REQUIRES: i >= 0, i < getSize()
    // EFFECTS: removes the flashcard at index i
    public void removeFlashCard(int i) {
        Event event = new Event("Removed FlashCard with question " + flashCardList.get(i) + " from " + name);
        EventLog.getInstance().logEvent(event);
        flashCardList.remove(i);
    }

    // REQUIRES: flashCardList is not empty
    // EFFECTS: returns an ArrayList containing all flashcards not mastered
    public List<FlashCard> notMasteredSet() {
        List<FlashCard> newSet = new ArrayList<>();

        for (FlashCard fc : flashCardList) {
            if (!fc.isMastered()) {
                newSet.add(fc);
            }
        }

        return newSet;
    }

    // MODIFIES: this
    // REQUIRES: name is not the empty string
    // EFFECTS: renames the flashCardSet to given name
    public void setName(String name) {
        this.name = name;
    }


    // REQUIRES: i >= 0
    // EFFECTS: returns the flashcard at index i
    public FlashCard getFlashCard(int i) {
        return flashCardList.get(i);
    }

    // EFFECTS: returns the flashcard list size
    public int getSize() {
        return flashCardList.size();
    }

    public String getName() {
        return name;
    }

    public List<FlashCard> getFlashCardList() {
        return flashCardList;
    }

    @Override
    // EFFECTS: creates a new Json object representing the flashcard set
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("flashcards", flashcardsToJson());
        return json;
    }

    // EFFECTS: returns flashcards in this flashcard set as a JSON array
    private JSONArray flashcardsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (FlashCard fc : flashCardList) {
            jsonArray.put(fc.toJson());
        }
        return jsonArray;
    }
}
