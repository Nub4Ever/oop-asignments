package main.Strategy;

import entitites.Child;

public final class BabyStrategy implements Strategy {
    @Override
    public double getAverageScore(final Child child) {
        child.setAverageScore(10d);
        return child.getAverageScore();
    }
}
