/**
 * VisibleCard.java
 * 
 * A card that's always visible *
 * 
 * @author Jonas Ganderton
 * @since 23/05/2021
 */
public class VisibleCard extends Card {

    /**
     * Construct a visible card.
     * 
     * @param text Text on card.
     */
    public VisibleCard(String text) {
        super(text);
        getStyleClass().clear();
        getStyleClass().add("visible-idle");
        setActions();
    }

    /**
     * Set actions for card.
     */
    private void setActions() {
        setOnMouseEntered(e -> {
            if (!selected) {
                getStyleClass().clear();
                getStyleClass().add("visible-hover");
            }
        });
        setOnMouseExited(e -> {
            getStyleClass().clear();
            getStyleClass().add("visible-idle");
        });
    }
}
