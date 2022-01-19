package main.Read;

import entitites.Child;
import entitites.Santa;
import org.json.simple.JSONObject;

public interface VisitorToRead {
    /**
     * @param child
     * @param jsonObject
     */
    void visit(Child child, JSONObject jsonObject);

    /**
     * @param santa
     * @param jsonObject
     */
    void visit(Santa santa, JSONObject jsonObject);
}
