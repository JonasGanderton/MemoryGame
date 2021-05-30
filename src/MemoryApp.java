import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 * MemoryApp.java
 * 
 * A card matching game developed with JavaFX
 * 
 * @author Jonas Ganderton
 * @since 15/05/2021
 */
public class MemoryApp extends Application {

    private static final boolean SHOW_GRID_LINES = false;
    private static final boolean SHOW_MINOR_GRID_LINES = false;
    private static final boolean CAN_RESIZE = true;
    private static final int SCREEN_WIDTH = 680;
    private static final int SCREEN_HEIGHT = 630;
    private static final int SPACING = 10;
    private static final String FILENAME = "cardPairs.txt";
    private static final String BACKGROUND_STYLE = "-fx-background-color: DAE603;";
    private static final String PLAYER_ONE_NAME = "Jonas";
    private static final String PLAYER_TWO_NAME = "Katharina";

    private Scene scene;
    private Pane layout;
    private GridPane gridLayout;
    private VisibleCard hideAll;
    private int currentPlayer;
    private int pairsRemaining;
    private PlayerCard[] players = new PlayerCard[2];
    private Boolean canSelect;
    private ArrayList<Card> clicked = new ArrayList<>();
    private Stage window;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Start the app.
     * 
     * @param window
     */
    @Override
    public void start(Stage window) throws FileNotFoundException {
        // Configure the scene and window
        this.window = window;
        configurePlayers();
        Random r = new Random();
        currentPlayer = r.nextInt(2); // 0 or 1
        switchPlayer();
        configureGame();
    }

    /**
     * Configures the layout for the cards.
     */
    private void configureLayout() throws FileNotFoundException {
        // Add cards
        gridLayout = configurGridPane(readCardStrings(FILENAME));
        layout = gridLayout;
        configureCards();

        // Some layout settings
        //layout.setStyle(BACKGROUND_STYLE); // Uncomment if no other background
        layout.setPadding(new Insets(SPACING)); // If all different: ^ > v <

        // Add hide all card
        configureHideAllCard();
        gridLayout.add(hideAll, 1, gridLayout.getRowCount() - 1, 2, 1);
        configurePlayersPane();
    }

    /**
     * Test using grid pane for cards.
     * Each card must be assigned a row and column.
     * 
     * @return GridPane.
     */
    private GridPane configurGridPane(ArrayList<String[]> cardStrings) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(SPACING);
        grid.setHgap(SPACING);
        grid.setGridLinesVisible(SHOW_GRID_LINES);

        // Grid size
        int cols = 4;
        int cardNum = cardStrings.size() * 2;
        int rows = cardNum / cols + ((cardNum % cols != 0) ? 1 : 0);

        // Add cards to ArrayList to shuffen
        ArrayList<Card> allCards = new ArrayList<>();
        for (int i = 0; i < cardStrings.size(); i++) {
            Card c1 = new Card(cardStrings.get(i)[0]);
            Card c2 = new Card(cardStrings.get(i)[1]);
            c1.setPair(c2);
            c2.setPair(c1);
            allCards.add(c1);
            allCards.add(c2);
        }
        Collections.shuffle(allCards);

        // Add cards to grid.
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid.add(allCards.get(r*(rows-1) + c), c, r);
            }
        }

        // Set column constraints
        for (int i = 0; i < 4; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(22);
            cc.setHgrow(Priority.ALWAYS);
            cc.setFillWidth(true);
            grid.getColumnConstraints().add(cc);
        }

        // Set row constraints
        for (int i = 0; i < rows; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(15);
            rc.setVgrow(Priority.ALWAYS);
            rc.setFillHeight(true);
            grid.getRowConstraints().add(rc);
        }

        return grid;
    }

    /**
     * Reads text from a file.
     * 
     * Expected format : <card1a>,<card1b><\n><card2a>,<card2b><\n><...>
     * Other format (card pairs with self) : <card1><\n><card2><\n><card3><\n><...>
     * 
     * @param filename File containing cards.
     * @return Pairs of strings from the file.
     * @throws FileNotFoundException File not found.
     */
    private ArrayList<String[]> readCardStrings (String filename) throws FileNotFoundException {
        ArrayList<String[]> cardPairs = new ArrayList<>();
        File data = new File(filename);
        Scanner pairIn = new Scanner(data);

        while (pairIn.hasNext()) {
            // Get card pair
            Scanner singleIn = new Scanner(pairIn.nextLine());
            singleIn.useDelimiter(",");    

            // Set the pair
            String[] pair = new String[2];
            pair[0] = singleIn.next();
            if (singleIn.hasNext()) {
                pair[1] = singleIn.next(); // If card has a pair.
            } else {
                pair[1] = pair[0]; // If not, duplicate card.
            }
            cardPairs.add(pair);
        }
        pairIn.close();

        //for (String[] strings : cardPairs) {
        //    System.out.println(strings[0] + " " + strings[1]);
        //}

        pairsRemaining = cardPairs.size();
        return cardPairs;
    }

    /**
     * Check to see if a pair has been found.
     */
    private void checkClicked() {
        if (clicked.size() == 2) {
            if (clicked.get(0).getPair() == clicked.get(1)) {
                for (int i = 0; i < 2; i++) {
                    clicked.get(0).selectedByPlayer(currentPlayer);
                    clicked.get(0).setDisable(true);
                    clicked.remove(0);
                }
                canSelect = true;
                players[currentPlayer].incrementScore();
                pairsRemaining--;
                if (pairsRemaining == 0) {
                    gameEnd();
                }
            } else {
                clicked.get(1).setSelected(true);
                hideAll.setDisable(false);
                canSelect = false;
            }
        }
    }

    /**
     * When clicked, a card will try to:
     * - Print its name
     * - Get added to clicked list
     * - Go into selected mode
     */
    private void configureCards() {
        ObservableList<Node> nodes = layout.getChildren();
        for(Node node : nodes) {
            if (node instanceof Card) {
                Card c = (Card) node;

                // When card clicked on
                c.setOnAction(e -> {
                    if (clicked.contains(c)) {
                        //System.out.println("Card already selected");
                    } else if (canSelect) {
                        //System.out.print(c.getText());
                        clicked.add(c);
                        c.setSelected(true);
                        checkClicked();
                    } else {
                        //System.out.println("Must deselect cards first");
                    }
                });

                c.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                c.setWrapText(true);
            } else {
                System.out.println(node.getClass());
            }
        }
    }

    /**
     * Configure the hide all card.
     */
    private void configureHideAllCard() {
        hideAll = new VisibleCard("Flip selected cards");
        hideAll.setDisable(true); // Can't press until two cards to unflip.
        hideAll.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Fill row and column
        hideAll.setOnAction(e -> {
            if (pairsRemaining > 0) {
                for (int i = 0; i < 2; i++) {
                    clicked.get(0).setSelected(false);
                    clicked.remove(0);
                }
                switchPlayer();
            } else {
                configureGame();
            }
            hideAll.setDisable(true);
            canSelect = true;
        });

        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(7);
        rc.setVgrow(Priority.ALWAYS);
        rc.setFillHeight(true);
        gridLayout.getRowConstraints().add(rc);
    }

    /**
     * Create the players
     */
    private void configurePlayers() {
        PlayerCard p1 = new PlayerCard(PLAYER_ONE_NAME);
        PlayerCard p2 = new PlayerCard(PLAYER_TWO_NAME);
        p1.setPlayer(1);
        p2.setPlayer(2);
        players[0] = p1;
        players[1] = p2;
        
        for (PlayerCard pc : players) {
            pc.setDisable(false);
            pc.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Fill row and column
        }
    }
    /**
     * Configure the player pane.
     */
    private void configurePlayersPane() {
        GridPane playersPane = new GridPane();
        playersPane.setAlignment(Pos.CENTER);
        playersPane.setVgap(SPACING);
        playersPane.setHgap(SPACING);
        playersPane.setGridLinesVisible(SHOW_MINOR_GRID_LINES);
        playersPane.add(players[0], 0, 0);
        playersPane.add(players[1], 2, 0);
        gridLayout.add(playersPane, 0, gridLayout.getRowCount(), 4, 1);


        // Set column sizes
        for (int i = 0; i < 3; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(25);
            cc.setHgrow(Priority.ALWAYS);
            cc.setFillWidth(true);
            playersPane.getColumnConstraints().add(cc);
        }
    }

    /**
     * Switch the current player.
     */
    private void switchPlayer() {
        players[currentPlayer].setDisable(true);
        if (currentPlayer == 0) {
            currentPlayer = 1;
        } else {
            currentPlayer = 0;
        }
        players[currentPlayer].setDisable(false);
    }

    /**
     * Change player scores at game end.
     * Sets the player for the next game.
     * Restarts the game.
     */
    private void gameEnd() {
        if (players[0].getScore() > players[1].getScore()) {
            players[0].incrementGamesWon();
            if (currentPlayer == 0) {
                switchPlayer();
            }
        } else if (players[0].getScore() < players[1].getScore()) {
            players[1].incrementGamesWon();
            if (currentPlayer == 1) {
                switchPlayer();
            }
        } else {
            players[0].incrementGamesWon();
            players[1].incrementGamesWon();
            switchPlayer();
        }
        hideAll.setText("Play again");
        hideAll.setDisable(false);
    }

    /**
     * Configure the game.
     */
    private void configureGame() {
        players[0].resetScore();
        players[1].resetScore();

        try {
            configureLayout();
        } catch (FileNotFoundException e) {
            System.out.println(e);
            System.exit(0);
        }
        hideAll.setText("Flip selected cards");
        canSelect = true;
        scene = new Scene(layout, SCREEN_WIDTH, SCREEN_HEIGHT);
        
        layout.setBackground(new Background(new BackgroundImage(new Image(
            "file:background.png", SCREEN_WIDTH, SCREEN_HEIGHT, true, false),
            BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
            BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        window.setResizable(CAN_RESIZE);
        window.setScene(scene);
        window.setTitle("Memory game");
        window.show();
    }
}
