package main.Read;

import org.json.simple.JSONObject;

public interface ReaderVisitable {
    /**
     * @param v
     * @param jsonObject
     */
    void accept(VisitorToRead v, JSONObject jsonObject);
}
