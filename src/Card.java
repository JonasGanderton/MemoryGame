import javafx.scene.control.Button;

public class Card extends Button {

    private static final String IDLE_STYLE = "-fx-background-color: #00B299;";
    private static final String HOVER_STYLE = "-fx-background-color: #009279;";

    public Card () {
        super();
    }

    public Card(String text) {
        super(text);
        setStyle(IDLE_STYLE);
        setOnMouseEntered(e -> setStyle(HOVER_STYLE));
        setOnMouseExited(e -> setStyle(IDLE_STYLE));
    }
}
