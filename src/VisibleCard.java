/**
 * VisibleCard.java
 * 
 * A card that's always visible
 */
public class VisibleCard extends Card {

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
