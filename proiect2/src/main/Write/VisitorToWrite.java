package main.Write;

import entitites.Santa;
import java.io.FileWriter;

public interface VisitorToWrite {
    /**
     * @param santa
     * @param file
     */
    void visit(Santa santa, FileWriter file);
}
