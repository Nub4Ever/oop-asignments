package main.Strategy;

import entitites.Child;

public interface Strategy {
    /**
     * @param child
     * @return
     */
    double getAverageScore(Child child);
}
