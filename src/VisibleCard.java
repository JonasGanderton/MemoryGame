/**
 * VisibleCard.java
 * 
 * A card that's always visible
 */
public class VisibleCard extends Card {

    private static final String IDLE_STYLE = "-fx-background-color: #00B299;" + FONT_STYLE;
    private static final String HOVER_STYLE = "-fx-background-color: #009279;" + FONT_STYLE;

    /**
     * Construct a visible card.
     * 
     * @param text Text on card.
     */
    public VisibleCard(String text) {
        super(text);
        setStyle(IDLE_STYLE);
        setActions();
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
        setOnMouseExited(e -> setStyle(IDLE_STYLE));
    }
}
