package main.Read;

import entitites.Child;
import entitites.Gift;
import entitites.Santa;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;


public final class ReaderVisitor implements VisitorToRead {

    @Override
    public void visit(final Child child, final  JSONObject jsonObject) {
        child.setId(((Long) jsonObject.get("id")).intValue());
        child.setLastName((String) jsonObject.get("lastName"));
        child.setFirstName((String) jsonObject.get("firstName"));
        child.setAge(((Long) jsonObject.get("age")).intValue());
        child.setCity((String) jsonObject.get("city"));
        child.setNiceScore(((Long) jsonObject.get("niceScore")).doubleValue());

        JSONArray giftsPreferences = (JSONArray) jsonObject.get("giftsPreferences");
        Iterator<String> iterator = giftsPreferences.iterator();

        while (iterator.hasNext()) {
            child.getGiftsPreferences().add(iterator.next());
        }

    }

    @Override
    public void visit(final Santa santa, final JSONObject jsonObject) {
        santa.setSantaBudget(((Long) jsonObject.get("santaBudget")).doubleValue());

        JSONObject intialData = (JSONObject) jsonObject.get("initialData");
        JSONArray giftsList = (JSONArray) intialData.get("santaGiftsList");

        for (int i = 0; i < giftsList.size(); i++) {
            JSONObject jsonGift = (JSONObject) giftsList.get(i);
            Gift gift = new Gift();

            gift.setPrice(((Long) jsonGift.get("price")).doubleValue());
            gift.setProductName((String) jsonGift.get("productName"));
            gift.setCategory((String) jsonGift.get("category"));

            santa.getSantaGiftsList().add(gift);
        }
    }
}
