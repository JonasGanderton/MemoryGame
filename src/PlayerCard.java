public class PlayerCard extends VisibleCard {
    
    private int score;
    private int gamesWon;
    private String name;
    private String playerNumString;

    /**
     * Construct a Player card.
     * 
     * @param text Player name.
     */
    public PlayerCard(String text) {
        super(text);
        getStyleClass().clear();
        setActions();
        name = text;
        score = 0;
        gamesWon = 0;
        refreshText();
    }

    /**
     * Set player as one or two.
     * 
     * @param playerNum Player number.
     */
    public void setPlayer(int playerNum) {
        getStyleClass().clear();
        if (playerNum == 1) {
            playerNumString = "one";
            getStyleClass().add("player-one-active");
        } else if (playerNum == 2) {
            playerNumString = "two";
            getStyleClass().add("player-two-active");
        }
    }

    /**
     * Set the card as active or inactive style.
     * 
     * @param active Whether this card is active.
     */
    public void setActive(boolean active) {
        getStyleClass().clear();
        if (active) {
            getStyleClass().add("player-" + playerNumString + "-active");
        } else {
            getStyleClass().add("player-" + playerNumString + "-inactive");
        }
    }

    /**
     * Increment score.
     */
    public void incrementScore() {
        score++;
        refreshText();
    }

    /**
     * Increment number of games won.
     */
    public void incrementGamesWon() {
        gamesWon++;
        refreshText();
    }

    /**
     * Get score.
     * 
     * @return Score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Set score to 0.
     */
    public void resetScore() {
        score = 0;
        refreshText();
    }

    /**
     * Set actions for card.
     */
    private void setActions() {
        setOnMouseEntered(e -> {});
        setOnMouseExited(e -> {});
    }

    /**
     * Update the player text.
     */
    public void refreshText() {
        setText(name + " (" + gamesWon + "): " + score);
    }
}
