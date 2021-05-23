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

    protected static final String FONT_STYLE = "-fx-text-fill: #004040;";
    private static final String HIDDEN_STYLE = "-fx-background-color: #00B299;-fx-text-fill: transparent;";
    private static final String HOVER_STYLE = "-fx-background-color: #009279;-fx-text-fill: transparent;";
    private static final String SELECTED_STYLE = "-fx-background-color: #00D2A9;" + FONT_STYLE;

    private Card pair;
    protected boolean selected;
    

    /**
     * Create a card.
     * 
     * @param text Text on card.
     */
    public Card(String text) {
        super(text);
        selected = false;
        setStyle(HIDDEN_STYLE);

        this.setActions();
        
    }

    /**
     * Set actions for card.
     */
    private void setActions() {
        setOnMouseEntered(e -> {
            if (!selected) {
                setStyle(HOVER_STYLE);
            }
        });
        setOnMouseExited(e -> {
            if (!selected) {
                setStyle(HIDDEN_STYLE); // Currently scrolling over.
            } else {
                setStyle(SELECTED_STYLE); // Has been clicked on too.
            }
        });
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
            setStyle(HIDDEN_STYLE);
        }
    }

    /**
     * Set the card to hover style.
     */
    public void setStyleHover() {
        setStyle(HOVER_STYLE);
    }
}
