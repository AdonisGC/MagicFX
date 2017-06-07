package card;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class CustomListCell extends ListCell<Card> {

    // Final vars
    private static  final String RARITY = "Rarity";
    private static  final String TYPE = "Type";
    private static  final String CARD_LIST_NAME_CLASS = "card-list-name";
    private static  final String CARD_LIST_RARITY_CLASS = "card-list-rarity";
    private static  final String CARD_LIST_TYPE_CLASS = "card-list-color";
    private static  final String CARD_LIST_IMAGE_CLASS = "card-list-image";

    // Fields
    private GridPane gridPane = new GridPane();
    private Label name = new Label();
    private Label rarity = new Label();
    private Label type = new Label();
    private Circle imageView = new Circle(30);

    CustomListCell() {
        configureGrid();
        configureName();
        configureRarity();
        configureType();
        addControlsToGrid();
        configureImageView();
    }

    private void configureGrid() {
        gridPane.setPadding(new Insets(5,0,5,0));
        // gridPane.setGridLinesVisible(true);

        // Horizontal fill behavior, col1 50% -><- 50% col2.
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);

        gridPane.getColumnConstraints().addAll(col1,col2);
    }

    // Add css class.
    private void configureName () {
        name.getStyleClass().add(CARD_LIST_NAME_CLASS);
    }

    private void configureRarity () {
        rarity.getStyleClass().add(CARD_LIST_RARITY_CLASS);
    }

    private void configureType () {
        type.getStyleClass().add(CARD_LIST_TYPE_CLASS);
    }

    private void configureImageView () {
        imageView.getStyleClass().add(CARD_LIST_IMAGE_CLASS);
    }

    private void addControlsToGrid () {
        gridPane.add(name, 0, 0, 1, 1);
        gridPane.add(type, 0, 1, 1, 1);
        gridPane.add(rarity, 0, 2, 1, 1);
        gridPane.add(imageView, 1, 0, 1, 3);

        // Set the horizontal alignment to RIGHT for the imageView.
        GridPane.setHalignment(imageView, HPos.RIGHT);
    }

    @Override
    protected void updateItem(Card item, boolean empty) {
        // calling super here is very important - don't skip this!
        super.updateItem(item, empty);

        if (empty) {
            clearContent();
        } else {
            addContent(item);
        }
    }

    private void clearContent () {
        setText(null);
        setGraphic(null);
    }

    private void addContent(Card item) {
        setText(null);

        // Set fields.
        name.setFont(Font.font(24));
        name.setText(item.getName());

        rarity.setFont(Font.font(null, FontPosture.ITALIC, 16));
        rarity.setText(RARITY+": "+item.getRarity());

        type.setFont(Font.font(16));
        type.setText(TYPE+": "+item.getType());

        // Load image from Internet.
        // Image img = new Image(item.getUrlImage(), 226, 311, true, true, true);
        // ImagePattern p = new ImagePattern(img, 20, 20, 40, 40, true);
        // setImage(p);
        setColor(item);

        // Setting the graphics components for the cell.
        setGraphic(gridPane);
    }

    private void setImage(ImagePattern p){
        // We can put a circle mask image.
        if (p != null) imageView.setFill(p);
    }

    private void setColor(Card item) {
        // Set card color.
        if (item.getColor() != null) {
            switch (item.getColor()[0]) {
                case "Green":
                    imageView.setFill(Color.web("#a5d6a7"));
                    break;
                case "Black":
                    imageView.setFill(Color.web("#212121"));
                    break;
                case "Red":
                    imageView.setFill(Color.web("#ef9a9a"));
                    break;
                case "Blue":
                    imageView.setFill(Color.web("#90caf9"));
                    break;
                case "White":
                    imageView.setFill(Color.web("#b0bec5"));
            }
        } else {
            imageView.setFill(Color.web("#ffcc80"));
        }
    }
}
