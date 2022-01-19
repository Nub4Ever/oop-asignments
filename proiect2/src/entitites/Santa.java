package entitites;

import main.Read.ReaderVisitable;
import main.Read.VisitorToRead;
import main.Write.VisitorToWrite;
import main.Write.WriterVisitable;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.util.LinkedList;

public final class Santa implements ReaderVisitable, WriterVisitable {
    private Double santaBudget;
    private String strategy = "id";
    private LinkedList<Gift> santaGiftsList = new LinkedList<>();
    private LinkedList<LinkedList<Child>> annualChildren = new LinkedList<>();

    public Santa(final Double santaBudget) {
        this.santaBudget = santaBudget;
    }

    public Santa() { }

    public Double getSantaBudget() {
        return santaBudget;
    }

    public LinkedList<Gift> getSantaGiftsList() {
        return santaGiftsList;
    }

    public LinkedList<LinkedList<Child>> getAnnualChildren() {
        return annualChildren;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(final String strategy) {
        this.strategy = strategy;
    }

    public void setAnnualChildren(final LinkedList<LinkedList<Child>> annualChildren) {
        this.annualChildren = annualChildren;
    }

    public void setSantaBudget(final Double santaBudget) {
        this.santaBudget = santaBudget;
    }

    public void setSantaGiftsList(final LinkedList<Gift> santaGiftsList) {
        this.santaGiftsList = santaGiftsList;
    }

    @Override
    public void accept(final VisitorToRead v, final  JSONObject jsonObject) {
        v.visit(this, jsonObject);
    }

    @Override
    public void accept(final VisitorToWrite v, final  FileWriter file) {
        v.visit(this, file);
    }
}
