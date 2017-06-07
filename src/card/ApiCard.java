package card;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.util.ArrayList;

public class ApiCard {

    ArrayList<Card> getAllCards() {

        final String BASE_URL = "https://api.magicthegathering.io/v1/cards";
        return doCall(BASE_URL);
    }

    @Nullable
    private ArrayList<Card> doCall(String url) {
        try {
            return proccesJson(HttpUtils.get(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Card> proccesJson(String s) {
        // Get my POJO.
        ArrayList<Card> cards = new ArrayList<>();

        try {

            JSONObject data = new JSONObject(s);
            JSONArray jsonCards = data.getJSONArray("cards");

            for (int i = 0; i < jsonCards.length(); i++) {

                JSONObject jsonCard = jsonCards.getJSONObject(i);
                Card newCard = new Card();

                newCard.setName(jsonCard.getString("name"));
                newCard.setRarity(jsonCard.getString("rarity"));
                newCard.setType(jsonCard.getString("type"));
                newCard.setId(jsonCard.getString("id"));
                if (jsonCard.has("power")) newCard.setPower(jsonCard.getString("power"));
                if (jsonCard.has("text")) newCard.setText(jsonCard.getString("text"));
                if (jsonCard.has("toughness")) newCard.setToughness(jsonCard.getString("toughness"));
                if (jsonCard.has("imageUrl")) newCard.setUrlImage(jsonCard.getString("imageUrl"));
                if (jsonCard.has("colors")) {

                    String colors [] = new String[jsonCard.getJSONArray("colors").length()];

                    for (int j = 0; j < jsonCard.getJSONArray("colors").length(); j++) {
                        colors[j] = jsonCard.getJSONArray("colors").get(j).toString();
                    }
                    newCard.setColor(colors);
                }

                cards.add(newCard);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cards;
    }
}
