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

    protected static final String FONT_STYLE = "-fx-text-fill: #004040;-fx-font-size: 16px;";
    protected static final String PLAYER_ONE_STYLE = "-fx-background-color: #26ABFF;" + FONT_STYLE;
    protected static final String PLAYER_TWO_STYLE = "-fx-background-color: #E18AAA;" + FONT_STYLE;
    private static final String HIDDEN_STYLE = "-fx-background-color: #00B299;-fx-text-fill: transparent;";
    private static final String HOVER_STYLE = "-fx-background-color: #009279;-fx-text-fill: transparent;";
    private static final String SELECTED_STYLE = "-fx-background-color: #00D2A9;" + FONT_STYLE;

    private Card pair;
    protected boolean selected;
    private int playerNum;

    /**
     * Create a card.
     * 
     * @param text Text on card.
     */
    public Card(String text) {
        super(text);
        selected = false;
        setStyle(HIDDEN_STYLE);
        setActions();
        playerNum = -1;
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
                // Has been clicked on too.
                if (playerNum == 0) {
                    setStyle(PLAYER_ONE_STYLE);
                } else if (playerNum == 1) {
                    setStyle(PLAYER_TWO_STYLE);
                } else {
                    setStyle(SELECTED_STYLE);
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

    /**
     * Set a card as selected by a player.
     * @param playerNum Player that selected card.
     */
    public void selectedByPlayer(int playerNum) {
        this.playerNum = playerNum;
        if (playerNum == 0) {
            setStyle(PLAYER_ONE_STYLE);
        } else if (playerNum == 1) {
            setStyle(PLAYER_TWO_STYLE);
        }
    }
}
