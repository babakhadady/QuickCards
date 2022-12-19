package persistence;

import org.json.JSONObject;

// Code is based from JSONSerializationDemo
public interface Writable {
    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
