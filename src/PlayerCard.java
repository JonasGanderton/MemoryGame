public class PlayerCard extends VisibleCard {
    
    private int score;
    private int gamesWon;
    private String name;

    /**
     * Construct a Player card.
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

    public void setPlayer(int playerNum) {
        getStyleClass().clear();
        if (playerNum == 1) {
            getStyleClass().add("player-one");
            //setStyle(PLAYER_ONE_STYLE);
        } else if (playerNum == 2) {
            getStyleClass().add("player-two");
            //setStyle(PLAYER_TWO_STYLE);
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
        setText(name + "(" + gamesWon + "): " + score);
    }
}
