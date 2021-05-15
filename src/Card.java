import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class Card extends Button {
    public Card () {
        super();
    }

    public Card(String text) {
        super(text);
        this.setBackground(new Background(new BackgroundFill(new Color(0, 0.7, 0.6, 0.9), null, getInsets())));
    }

}
