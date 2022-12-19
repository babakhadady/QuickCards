package persistence;

import model.FlashCard;
import model.FlashCardSet;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader which reads workroom from JSON data which is stored in file
// Code is based off JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader which read from source file
    public JsonReader(String source) {
        this.source = source;
    }


    // EFFECTS: reads workroom from file and returns it;
    //          throws an IOException if an error occurs reading data from file
    public List<FlashCardSet> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFlashCardSets(jsonObject);
    }

    // EFFECTS: reads source file as a string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));

        }
        return contentBuilder.toString();
    }

    // EFFECT: parses the flashCardSets from JSON object into a set and returns it
    private List<FlashCardSet> parseFlashCardSets(JSONObject jsonObject) {
        List<FlashCardSet> sets = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("sets");

        for (Object json : jsonArray) {
            JSONObject nextFlashCardSet = (JSONObject) json;
            sets.add(parseFlashCardSet(nextFlashCardSet));
        }
        return sets;
    }

    // EFFECTS: parses the flashCardSet from JSON object and returns it
    private FlashCardSet parseFlashCardSet(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        FlashCardSet flashCardSet = new FlashCardSet(name);
        addFlashCards(flashCardSet, jsonObject);
        return flashCardSet;
    }

    // MODIFIES: flashCardSet
    // EFFECTS: parses flashCards from JSON object and adds them to the workroom
    private void addFlashCards(FlashCardSet flashCardSet, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("flashcards");

        for (Object json : jsonArray) {
            JSONObject nextFlashCard = (JSONObject) json;
            addFlashcard(flashCardSet, nextFlashCard);
        }
    }

    // MODIFIES: flashCardSet
    // EFFECTS: parses flashCard from JSON object and adds it to the workroom
    private void addFlashcard(FlashCardSet flashCardSet, JSONObject jsonObject) {
        String question = jsonObject.getString("question");
        String answer = jsonObject.getString("answer");
        Boolean mastered = jsonObject.getBoolean("mastered");

        FlashCard flashCard = new FlashCard();
        flashCard.setQuestion(question);
        flashCard.setAnswer(answer);

        if (mastered) {
            flashCard.setMastered();
        }
        flashCardSet.addFlashCard(flashCard);
    }
}
