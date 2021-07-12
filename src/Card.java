import javafx.geometry.Pos;
import javafx.scene.control.Button;
/*
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;*/

/**
 * Card.java
 * 
 * Card used in MemoryApp.
 * 
 * @author Jonas Ganderton
 * @since 15/05/2021
 */
public class Card extends Button {

    protected static final String FONT_STYLE = "-fx-text-fill: #004040; -fx-font-size: 16px;";
    protected static final String PLAYER_ONE_STYLE = "-fx-background-color: #A3DCFF;" + FONT_STYLE;
    protected static final String PLAYER_TWO_STYLE = "-fx-background-color: #B4E8B3;" + FONT_STYLE;
    protected static final String IDLE_STYLE = "-fx-background-color: #F8EB77;";
    protected static final String HOVER_STYLE = "-fx-background-color: #FFFA99; -fx-border-style: solid; -fx-border-width: 10px; -fx-border-color: black; -fx-border-radius: 20px;";
    protected static final String SELECTED_STYLE = "-fx-background-color: #FFE75C;" + FONT_STYLE;
    private static final String HIDDEN_FONT_STYLE = "-fx-text-fill: red;";

    protected boolean selected;
    private Card pair;
    private int playerNum;
    //private Background bg;

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
        //setStyle(HIDDEN_FONT_STYLE);
        setActions();
        playerNum = -1;
        /*bg = new Background(new BackgroundImage(new Image(
            "file:cardBG4.jpg", 148, 88, true, true),
            BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
            BackgroundPosition.CENTER, BackgroundSize.DEFAULT));
        setBackground(bg);*/
    }

    /**
     * Set actions for card.
     */
    private void setActions() {
        setOnMouseEntered(e -> {
            if (!selected) {
                getStyleClass().clear();
                getStyleClass().add("hidden-hover");
                // setStyle(HOVER_STYLE + HIDDEN_FONT_STYLE);
            }
        });

        setOnMouseExited(e -> {
            getStyleClass().clear();
            if (!selected) {
                getStyleClass().add("hidden-idle");
                // setStyle(HIDDEN_FONT_STYLE);
                // setBackground(bg);
            } else {
                // Has been clicked on
                if (playerNum == 0) {
                    getStyleClass().add("selected-player-one");
                    // setStyle(PLAYER_ONE_STYLE);
                } else if (playerNum == 1) {
                    getStyleClass().add("selected-player-two");
                    // setStyle(PLAYER_TWO_STYLE);
                } else {
                    getStyleClass().add("selected");
                    // setStyle(SELECTED_STYLE);
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
        getStyleClass().clear();
        if (selected) {
            getStyleClass().add("selected");
        } else {
            getStyleClass().add("hidden-idle");
        }
    }
    public void setSelectedOLD(boolean selected) {
        this.selected = selected;
        if (selected) {
            setStyle(SELECTED_STYLE);
        } else {
            setStyle(HIDDEN_FONT_STYLE);
            //setBackground(bg);
        }
    }

    /**
     * Set the card to hover style.
     */
    public void setStyleHover() {
        getStyleClass().clear();
        getStyleClass().add("hidden-idle");
        //setStyle(HOVER_STYLE + HIDDEN_FONT_STYLE);
    }

    /**
     * Set a card as selected by a player.
     * @param playerNum Player that selected card.
     */
    public void selectedByPlayer(int playerNum) {
        this.playerNum = playerNum;
        getStyleClass().clear();
        if (playerNum == 0) {
            getStyleClass().add("selected-player-one");
            //setStyle(PLAYER_ONE_STYLE);
        } else if (playerNum == 1) {
            getStyleClass().add("selected-player-two");
            //setStyle(PLAYER_TWO_STYLE);
        }
    }
}
