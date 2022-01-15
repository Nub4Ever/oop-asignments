package main.Strategy;

import entitites.Child;

public final class TeenStrategy implements Strategy {

    @Override
    public double getAverageScore(final Child child) {
        double sum = 0;
        int index = 1;
        int indexSum = 0;

        for (Double score : child.getScoreHistory()) {
            sum += score * index;
            indexSum += index;
            index++;
        }

        child.setAverageScore((double) sum / indexSum);
        return child.getAverageScore();
    }
}
