package entitites;

import main.Read.ReaderVisitable;
import main.Read.VisitorToRead;
import org.json.simple.JSONObject;

import java.util.LinkedList;

public final class Child implements ReaderVisitable {
    private int id;
    private int age;
    private String firstName;
    private String lastName;
    private String city;
    private String ageRange;
    private Double niceScore;
    private Double averageScore;
    private Double assignedBudget;
    private Double niceScoreBonus;
    private String elf;
    private LinkedList<Double> scoreHistory = new LinkedList<>();
    private LinkedList<String> giftsPreferences = new LinkedList<>();
    private LinkedList<Gift> giftsReceived = new LinkedList<>();

    public Child(final int id, final  int age, final String firstName,
                 final String lastName, final String city, final Double niceScore) {
        this.id = id;
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.niceScore = niceScore;
    }

    public Child(final Child childClone) {
        this.id = childClone.id;
        this.age = childClone.age;
        this.ageRange = childClone.ageRange;
        this.firstName = childClone.firstName;
        this.lastName = childClone.lastName;
        this.city = childClone.city;
        this.niceScore = childClone.niceScore;
        this.averageScore = childClone.averageScore;
        this.assignedBudget = childClone.assignedBudget;
        this.giftsReceived = childClone.giftsReceived;
    }

    public Child() { }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public Double getNiceScore() {
        return niceScore;
    }

    public LinkedList<String> getGiftsPreferences() {
        return giftsPreferences;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public Double getAssignedBudget() {
        return assignedBudget;
    }

    public String getElf() {
        return elf;
    }

    public void setElf(final String elf) {
        this.elf = elf;
    }

    public Double getNiceScoreBonus() {
        return niceScoreBonus;
    }

    public void setNiceScoreBonus(final Double niceScoreBonus) {
        this.niceScoreBonus = niceScoreBonus;
    }

    public void setAssignedBudget(final Double assignedBudget) {
        this.assignedBudget = assignedBudget;
    }

    public void setAverageScore(final Double averageScore) {
        this.averageScore = averageScore;
    }

    public void setAgeRange(final String ageRange) {
        this.ageRange = ageRange;
    }

    public LinkedList<Double> getScoreHistory() {
        return scoreHistory;
    }

    public LinkedList<Gift> getGiftsReceived() {
        return giftsReceived;
    }

    public void setGiftsReceived(final LinkedList<Gift> giftsReceived) {
        this.giftsReceived = giftsReceived;
    }

    public void setScoreHistory(final LinkedList<Double> scoreHistory) {
        this.scoreHistory = scoreHistory;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    /**
     * @param niceScore
     */
    public void setNiceScore(final Double niceScore) {
        this.niceScore = niceScore;
        scoreHistory.add(niceScore);
    }

    public void setGiftsPreferences(final LinkedList<String> giftsPreferences) {
        this.giftsPreferences = giftsPreferences;
    }

    @Override
    public void accept(final VisitorToRead v, final JSONObject jsonObject) {
        v.visit(this, jsonObject);
    }
}
