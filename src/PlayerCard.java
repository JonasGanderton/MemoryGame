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
        setActions();
        name = text;
        score = 0;
        gamesWon = 0;
        refreshText();
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
