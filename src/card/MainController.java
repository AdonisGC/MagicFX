package card;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
@SuppressWarnings("unchecked")

public class MainController implements Initializable {

    @FXML
    private Text name;
    @FXML
    private Text text;
    @FXML
    private Text nRarity;
    @FXML
    private Text type;
    @FXML
    private Text power;
    @FXML
    private Text toughness;
    @FXML
    private Text colors;
    @FXML
    private Rectangle squareMask;


    @FXML
    private ListView<Card> cardList;
    private ObservableList<Card> items =
            FXCollections.observableArrayList();

    @FXML
    private ComboBox rarity;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardList.setFocusTraversable(false);


        ConcurrentTask concurrent = new ConcurrentTask();
        concurrent.start();
        squareMask.setVisible(false);

        try {
            concurrent.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // We can populate some real data from Internet.
        cardList.setItems(items);
        cardList.setCellFactory(param -> new CustomListCell());

        // Set listener on item clicked.
        cardList.setOnMouseClicked(event -> {
            Card card = cardList.getSelectionModel().getSelectedItem();
            setContentDetails(card);
        });

        ObservableList<String> rarityList = FXCollections.observableArrayList(
                "Common",
                "Uncommon",
                "Rare",
                "Mythic Rare",
                "Special",
                "Basic Land"
        );
        rarity.setItems(rarityList);
    }

    private class ConcurrentTask extends Thread {
        @Override
        public void run() {

            ApiCard api = new ApiCard();

            ArrayList<Card> result = api.getAllCards();
            items.addAll(result);
            Gson json = new GsonBuilder()
                    .disableHtmlEscaping()
                    .setPrettyPrinting()
                    .create();
            super.run();
        }
    }

    private void setContentDetails(Card card) {
        name.setText(card.getName());
        nRarity.setText(card.getRarity());
        type.setText(card.getType());
        power.setText(card.getPower() != null ? card.getPower() : "--");
        text.setText(card.getText() != null ? card.getText() : "--");
        toughness.setText(card.getToughness() != null ? card.getToughness() : "--");
        colors.setText(card.getColor() != null ? getColors(card.getColor()) : "--");
        if (card.getUrlImage() != null) {
            Image img = new Image(card.getUrlImage(), 226, 311, true, true, true);
            img.progressProperty().addListener((observable, oldValue, progress) -> {
                if ((Double) progress == 1.0 && !img.isError()) {
                    setImagePattern(img);
                }
            });
        }
    }

    private void setImagePattern (Image img) {
        ImagePattern p = new ImagePattern(img);
        squareMask.setFill(p);
        squareMask.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), squareMask);
        ft.setFromValue(0.0);
        ft.setToValue(2.0);
        ft.play();
    }

    private String getColors(String [] colors) {
        String summary = "";
        int indexAppend = colors.length;
        for (String color : colors) {
            summary += color;
            indexAppend--;
            summary += indexAppend > 0 ? "\n" : "";
        }
        return summary;
    }
}
