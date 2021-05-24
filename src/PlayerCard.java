public class PlayerCard extends VisibleCard {
    
    private int score;
    private String name;

    public PlayerCard(String text) {
        super(text);
        setActions();
        name = text;
        score = 0;
    }

    public void incrementScore() {
        score++;
        setText(name + ": " + score);
    }

    /**
     * Set actions for card.
     */
    private void setActions() {
        setOnMouseEntered(e -> {});
        setOnMouseExited(e -> {});
    }
}
