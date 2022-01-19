package main.Strategy;

import entitites.Child;

public final class KidStrategy implements Strategy {
    @Override
    public double getAverageScore(final Child child) {
        double sum = 0;

        for (Double score : child.getScoreHistory()) {
            sum += score;
        }

        child.setAverageScore((double) sum / child.getScoreHistory().size());
        return child.getAverageScore();
    }
}
