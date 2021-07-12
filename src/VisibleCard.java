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
        getStyleClass().clear();
        getStyleClass().add("visible-idle");
        //setStyle(IDLE_STYLE);
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
                //setStyle(HOVER_STYLE);
            }
        });
        setOnMouseExited(e -> {
            getStyleClass().clear();
            getStyleClass().add("visible-idle");
        });
        //setOnMouseExited(e -> setStyle(IDLE_STYLE));
    }
}
