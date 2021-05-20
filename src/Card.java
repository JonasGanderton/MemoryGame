import javafx.scene.control.Button;

/**
 * Card.java
 * 
 * Card used in MemoryApp.
 * 
 * @author Jonas Ganderton
 * @since 15/05/2021
 */
public class Card extends Button {

    private static final String IDLE_STYLE = "-fx-background-color: #00B299;";
    private static final String HOVER_STYLE = "-fx-background-color: #009279;";
    private static final String SELECTED_STYLE = "-fx-background-color: #00D2B9;";
    
    private Card pair;
    private boolean selected;

    /**
     * Create a card.
     * 
     * @param text Text on card.
     */
    public Card(String text) {
        super(text);
        setStyle(IDLE_STYLE);
        setOnMouseEntered(e -> setStyle(HOVER_STYLE));
        setOnMouseExited(e -> (!selected) ? setStyle(IDLE_STYLE) : setStyle(SELECTED_STYLE));
        selected = false;
    }
    
    /**
     * @return This card's pair.
     */
    public Card getPair() {
        return pair;
    }

    /**
     * @param pair This cards's pair to set.
     */
    public void setPair(Card pair) {
        this.pair = pair;
    }

    /**
     * Change style when selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            setStyle(SELECTED_STYLE);
        } else {
            setStyle(IDLE_STYLE);
        }
    }
}
