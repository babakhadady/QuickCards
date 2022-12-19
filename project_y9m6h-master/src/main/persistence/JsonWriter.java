package persistence;


import model.FlashCardSet;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

// Represents a writer which writes JSON representation of the FlashCardSet to file
// Code is based off of JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriter {
    public static final int TAB = 4;
    private PrintWriter writer;
    private String destination;


    // EFFECT: construct the writer to write at the destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }


    // MODIFIES: this
    // EFFECTS: opens the writer; throws a FileNotFoundException if the destination
    //          file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes the JSON representation of the FlashCardSet to file
    public void write(List<FlashCardSet> sets)  {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (FlashCardSet flashCardSet : sets) {
            jsonArray.put(flashCardSet.toJson());
        }

        json.put("sets", jsonArray);
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes the string to file
    public void saveToFile(String json) {
        writer.println(json);
    }
}
