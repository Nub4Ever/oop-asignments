package main.Write;

import java.io.FileWriter;

public interface WriterVisitable {
    /**
     * @param v
     * @param file
     */
    void accept(VisitorToWrite v, FileWriter file);
}
