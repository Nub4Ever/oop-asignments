package main.Write;

import com.fasterxml.jackson.databind.ObjectMapper;
import entitites.Child;
import entitites.Gift;
import entitites.Santa;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public final class WriterVisitor implements VisitorToWrite {

    @Override
    @SuppressWarnings("unchecked")
    public void visit(final Santa santa, final FileWriter file) {
        JSONObject annualChildrenList = new JSONObject();
        JSONArray annualChildrenObj = new JSONArray();
        Map<String, Object> childObj = null;

        for (LinkedList<Child> children : santa.getAnnualChildren()) {
            JSONArray childrenObj = new JSONArray();
            JSONObject childrenList = new JSONObject();
            for (Child child : children) {
                childObj = new LinkedHashMap<>();
                childObj.put("id", child.getId());
                childObj.put("lastName", child.getLastName());
                childObj.put("firstName", child.getFirstName());
                childObj.put("city", child.getCity());
                childObj.put("age", child.getAge());

                JSONArray giftsPreferencesObj = new JSONArray();
                for (String gift : child.getGiftsPreferences()) {
                    giftsPreferencesObj.add(gift);
                }
                childObj.put("giftsPreferences", giftsPreferencesObj);
                childObj.put("averageScore", child.getAverageScore());

                JSONArray scoreHistoryObj = new JSONArray();
                for (Double score : child.getScoreHistory()) {
                    scoreHistoryObj.add(score);
                }
                childObj.put("niceScoreHistory", scoreHistoryObj);
                childObj.put("assignedBudget", child.getAssignedBudget());

                JSONArray receivedGiftsObj = new JSONArray();
                for (Gift gift : child.getGiftsReceived()) {
                    Map<String, Object> giftObj = new LinkedHashMap<>();
                    giftObj.put("productName", gift.getProductName());
                    giftObj.put("price", gift.getPrice());
                    giftObj.put("category", gift.getCategory());

                    receivedGiftsObj.add(giftObj);
                }
                childObj.put("receivedGifts", receivedGiftsObj);
                childrenObj.add(childObj);
            }
            childrenList.put("children", childrenObj);
            annualChildrenObj.add(childrenList);
        }

        annualChildrenList.put("annualChildren", annualChildrenObj);

        ObjectMapper mapper = new ObjectMapper();
        try (file) {
            file.write(mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(annualChildrenList));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
