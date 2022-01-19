package main;

import checker.Checker;
import common.Constants;
import entitites.Child;
import entitites.Gift;
import entitites.Santa;
import main.Read.ReaderVisitor;
import main.Read.VisitorToRead;
import main.Strategy.BabyStrategy;
import main.Strategy.KidStrategy;
import main.Strategy.Strategy;
import main.Strategy.TeenStrategy;
import main.Write.WriterVisitor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * Class used to run the code
 */
public final class Main {

    private Main() {
        ///constructor for checkstyle
    }
    /**
     * This method is used to call the checker which calculates the score
     * @param args
     *          the arguments used to call the main method
     */
    public static void main(final String[] args) {
        JSONParser parser = new JSONParser();

        for (int j = 1; j <= Constants.TESTS_NUMBER; j++) {
            try {
                Object readeObj = parser.parse(new
                        FileReader(Constants.INPUT_PATH + j + Constants.FILE_EXTENSION));
                JSONObject jsonReadeObject = (JSONObject) readeObj;
                VisitorToRead readeVisitor = new ReaderVisitor();

                Santa santa = new Santa();
                LinkedList<Child> children = new LinkedList<>();

                //initialData begin
                int numberOfYears = ((Long) jsonReadeObject.get("numberOfYears")).intValue();
                santa.accept(readeVisitor, jsonReadeObject);

                JSONObject intialData = (JSONObject) jsonReadeObject.get("initialData");
                JSONArray annualChanges = (JSONArray) jsonReadeObject.get("annualChanges");
                JSONArray jsonChildren = (JSONArray) intialData.get("children");

                for (int i = 0; i < jsonChildren.size(); i++) {
                    JSONObject jsonChild = (JSONObject) jsonChildren.get(i);

                    Child child = new Child();
                    Strategy strategyType = null;
                    child.accept(readeVisitor, jsonChild);

                    if (child.getAge() < Constants.FIVE) {
                        child.setAgeRange("Baby");
                        strategyType = new BabyStrategy();
                    } else if (child.getAge() >= Constants.FIVE
                            && child.getAge() < Constants.TWELVE) {
                        child.setAgeRange("Kid");
                        strategyType = new KidStrategy();
                    } else if (child.getAge() >= Constants.TWELVE
                            && child.getAge() <= Constants.EIGHTTEEN) {
                        child.setAgeRange("Teen");
                        strategyType = new TeenStrategy();
                    } else {
                        child.setAgeRange("Young Adult");
                    }

                    if (!child.getAgeRange().equals("Young Adult")) {
                        child.setAverageScore(strategyType.getAverageScore(child));
                        children.add(child);
                    }
                }
                //initialData ends

                for (int i = 0; i < numberOfYears + 1; i++) {
                    double averageAllScore = 0d;
                    for (Child child : children) {
                        averageAllScore += child.getAverageScore();
                    }
                    double budgetUnit = santa.getSantaBudget() / averageAllScore;

                    for (Child child : children) {
                        child.setGiftsReceived(new LinkedList<>());
                        double budgetCopil = child.getAverageScore() * budgetUnit;
                        child.setAssignedBudget(budgetCopil);

                        for (String category : child.getGiftsPreferences()) {
                            Gift receivedGift = null;
                            double minPrice = Double.MAX_VALUE;
                            for (Gift gift : santa.getSantaGiftsList()) {
                                if (gift.getCategory().equals(category)) {
                                    if (Double.compare(budgetCopil, gift.getPrice()) > 0
                                            && gift.getPrice() < minPrice) {
                                        receivedGift = gift;
                                        minPrice = receivedGift.getPrice();
                                    }
                                }
                            }
                            if (receivedGift != null) {
                                child.getGiftsReceived().add(receivedGift);
                                budgetCopil -= receivedGift.getPrice();
                                santa.setSantaBudget(santa.getSantaBudget()
                                        - receivedGift.getPrice());
                            }
                        }
                    }

                    LinkedList<Child> childrenList = new LinkedList<>();
                    for (Child child : children) {
                        LinkedList<Double> scoreHistory = new LinkedList<>();
                        scoreHistory.addAll(child.getScoreHistory());
                        LinkedList<String> giftsPreferences = new LinkedList<>();
                        giftsPreferences.addAll(child.getGiftsPreferences());

                        Child childClone = new Child(child);
                        childClone.setScoreHistory(scoreHistory);
                        childClone.setGiftsPreferences(giftsPreferences);
                        childrenList.add(childClone);
                    }
                    santa.getAnnualChildren().add(childrenList);

                    //updates begin
                    for (Child child : children) {
                        child.setAge(child.getAge() + 1);
                        Strategy strategyType = null;

                        if (child.getAge() < Constants.FIVE) {
                            child.setAgeRange("Baby");
                            strategyType = new BabyStrategy();
                        } else if (child.getAge() >= Constants.FIVE
                                && child.getAge() < Constants.TWELVE) {
                            child.setAgeRange("Kid");
                            strategyType = new KidStrategy();
                        } else if (child.getAge() >= Constants.TWELVE
                                && child.getAge() <= Constants.EIGHTTEEN) {
                            child.setAgeRange("Teen");
                            strategyType = new TeenStrategy();
                        } else {
                            child.setAgeRange("Young Adult");
                        }

                        if (!child.getAgeRange().equals("Young Adult")) {
                            child.setAverageScore(strategyType.getAverageScore(child));
                        }
                    }

                    Iterator iterator = children.iterator();
                    while (iterator.hasNext()) {
                        Child child = (Child) iterator.next();
                        if (child.getAge() > Constants.EIGHTTEEN) {
                            iterator.remove();
                        }
                    }

                    if (i < numberOfYears) {
                        JSONObject update = (JSONObject) annualChanges.get(i);
                        santa.setSantaBudget(((Long) update.get("newSantaBudget")).doubleValue());

                        JSONArray newGifts = (JSONArray) update.get("newGifts");
                        if (newGifts != null) {
                            for (int k = 0; k < newGifts.size(); k++) {
                                JSONObject jsonGift = (JSONObject) newGifts.get(k);
                                Gift gift = new Gift();

                                gift.setPrice(((Long) jsonGift.get("price")).doubleValue());
                                gift.setProductName((String) jsonGift.get("productName"));
                                gift.setCategory((String) jsonGift.get("category"));

                                santa.getSantaGiftsList().add(gift);
                            }
                        }

                        JSONArray newChildren = (JSONArray) update.get("newChildren");
                        if (newChildren != null) {
                            for (int k = 0; k < newChildren.size(); k++) {
                                JSONObject jsonChild = (JSONObject) newChildren.get(k);

                                Child child = new Child();
                                Strategy strategyType = null;
                                child.accept(readeVisitor, jsonChild);

                                if (child.getAge() < Constants.FIVE) {
                                    child.setAgeRange("Baby");
                                    strategyType = new BabyStrategy();
                                } else if (child.getAge() >= Constants.FIVE
                                        && child.getAge() < Constants.TWELVE) {
                                    child.setAgeRange("Kid");
                                    strategyType = new KidStrategy();
                                } else if (child.getAge() >= Constants.TWELVE
                                        && child.getAge() <= Constants.EIGHTTEEN) {
                                    child.setAgeRange("Teen");
                                    strategyType = new TeenStrategy();
                                } else {
                                    child.setAgeRange("Young Adult");
                                }

                                if (!child.getAgeRange().equals("Young Adult")) {
                                    child.setAverageScore(strategyType.getAverageScore(child));
                                    children.add(child);
                                }
                            }
                        }
                        JSONArray childrenUpdates = (JSONArray) update.get("childrenUpdates");
                        if (!childrenUpdates.isEmpty()) {
                            for (int k = 0; k < childrenUpdates.size(); k++) {
                                JSONObject jsonChild = (JSONObject) childrenUpdates.get(k);
                                int id = ((Long) jsonChild.get("id")).intValue();

                                for (Child child : children) {
                                    if (id == child.getId()) {
                                        if (jsonChild.get("niceScore") != null) {
                                            child.setNiceScore(((Long)
                                                    jsonChild.get("niceScore")).doubleValue());

                                            Strategy strategyType = null;
                                            if (child.getAge() < Constants.FIVE) {
                                                child.setAgeRange("Baby");
                                                strategyType = new BabyStrategy();
                                            } else if (child.getAge() >= Constants.FIVE
                                                    && child.getAge() < Constants.TWELVE) {
                                                child.setAgeRange("Kid");
                                                strategyType = new KidStrategy();
                                            } else if (child.getAge() >= Constants.TWELVE
                                                    && child.getAge() <= Constants.EIGHTTEEN) {
                                                child.setAgeRange("Teen");
                                                strategyType = new TeenStrategy();
                                            } else {
                                                child.setAgeRange("Young Adult");
                                            }

                                            if (!child.getAgeRange().equals("Young Adult")) {
                                                child.setAverageScore(
                                                        strategyType.getAverageScore(child));
                                            }
                                        }

                                        JSONArray giftsPreferences = (JSONArray)
                                                jsonChild.get("giftsPreferences");
                                        if (!giftsPreferences.isEmpty()) {
                                            Iterator<String> itr = giftsPreferences.iterator();
                                            LinkedHashSet<String> newGiftsPreferences
                                                    = new LinkedHashSet<>();

                                            while (itr.hasNext()) {
                                                newGiftsPreferences.add(itr.next());
                                            }

                                            newGiftsPreferences.addAll(child.getGiftsPreferences());
                                            child.getGiftsPreferences().clear();
                                            child.getGiftsPreferences().addAll(newGiftsPreferences);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //update ends

                }

                FileWriter file = new FileWriter(Constants.OUTPUT_PATH
                        + j + Constants.FILE_EXTENSION);
                WriterVisitor writeVisitor = new WriterVisitor();
                writeVisitor.visit(santa, file);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        Checker.calculateScore();
    }
}
