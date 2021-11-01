import javafx.geometry.Pos;
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

    protected boolean selected;
    private Card pair;
    private int playerNum;

    /**
     * Create a card.
     * 
     * @param text Text on card.
     */
    public Card(String text) {
        super(text);
        setAlignment(Pos.CENTER);
        selected = false;
        getStyleClass().add("hidden-idle");
        setActions();
        playerNum = -1;
    }

    /**
     * Set actions for card.
     */
    private void setActions() {
        setOnMouseEntered(e -> {
            if (!selected) {
                getStyleClass().clear();
                getStyleClass().add("hidden-hover");
            }
        });

        setOnMouseExited(e -> {
            getStyleClass().clear();
            if (!selected) {
                getStyleClass().add("hidden-idle");
            } else {
                // Has been clicked on
                if (playerNum == 0) {
                    getStyleClass().add("selected-player-one");
                } else if (playerNum == 1) {
                    getStyleClass().add("selected-player-two");
                } else {
                    getStyleClass().add("selected");
                }
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
     * 
     * @param selected. Whether this card is selected or not.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
        getStyleClass().clear();
        if (selected) {
            getStyleClass().add("selected");
        } else {
            getStyleClass().add("hidden-idle");
        }
    }

    /**
     * Set the card to hover style.
     */
    public void setStyleHover() {
        getStyleClass().clear();
        getStyleClass().add("hidden-idle");
    }

    /**
     * Set a card as selected by a player.
     * 
     * @param playerNum Player that selected card.
     */
    public void selectedByPlayer(int playerNum) {
        this.playerNum = playerNum;
        getStyleClass().clear();
        if (playerNum == 0) {
            getStyleClass().add("selected-player-one");
        } else if (playerNum == 1) {
            getStyleClass().add("selected-player-two");
        }
    }
}
