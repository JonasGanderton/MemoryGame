import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
    private static final boolean SHOW_MINOR_GRID_LINES = true;
    private static final boolean CAN_RESIZE = false;
    private static final int SCREEN_WIDTH = 650;
    private static final int SCREEN_HEIGHT = 650;
    private static final int SPACING = 10;
    private static final String FILENAME = "cardPairs.txt";
    private static final String BACKGROUND_STYLE = "-fx-background-color: DAE6F3;";

    private Pane layout;
    private GridPane gridLayout;
    private TilePane tileLayout;
    private Scene scene;
    private VisibleCard hideAll;
    private Boolean canSelect;
    private ArrayList<Card> clicked = new ArrayList<>();
    
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
        configureLayout();
        canSelect = true;
        scene = new Scene(layout, SCREEN_WIDTH, SCREEN_HEIGHT);
        window.setResizable(CAN_RESIZE);
        window.setScene(scene);
        window.setTitle("Memory game");
        window.show();
    }

    /**
     * Configures the layout for the cards.
     */
    private void configureLayout() throws FileNotFoundException {
        // Add cards
        gridLayout = configurGridPane(readCardStrings(FILENAME));
        tileLayout = configureTilePane(readCardStrings(FILENAME));
        layout = gridLayout;
        configureCards();

        // Some layout settings
        layout.setStyle(BACKGROUND_STYLE);
        layout.setPadding(new Insets(SPACING)); // If all different: ^ > v <

        // Add hide all card
        configureHideAllCard();
        tileLayout.getChildren().add(hideAll);
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

        // Populate grid with cards
        int row = 0;
        int col = 0;
        for (int i = 0; i < cardStrings.size(); i++) {
            Card c1 = new Card(cardStrings.get(i)[0]);
            Card c2 = new Card(cardStrings.get(i)[1]);
            c1.setPair(c2);
            c2.setPair(c1);
            grid.add(c1, col++, row);
            grid.add(c2, col++, row);
            
            if (col >= 4) {
                col = 0;
                row++;
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
        for (int i = 0; i < row; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(15);
            rc.setVgrow(Priority.ALWAYS);
            rc.setFillHeight(true);
            grid.getRowConstraints().add(rc);
        }

        return grid;
    }

    /**
     * Test using a tile pane for cards.
     * Automatically fills tiles in order (1D array).
     * 
     * @return TilePane.
     */
    private TilePane configureTilePane(ArrayList<String[]> cardStrings) {
        TilePane tiles = new TilePane();
        tiles.setAlignment(Pos.CENTER);
        tiles.setVgap(SPACING);
        tiles.setHgap(SPACING);

        // Populate tiles with cards
        for (String[] cardPair : cardStrings) {
            Card c1 = new Card(cardPair[0]);
            Card c2 = new Card(cardPair[1]);
            c1.setPair(c2);
            c2.setPair(c1);
            tiles.getChildren().addAll(c1,c2);
        }
        randomiseTiles(tiles.getChildren());
        return tiles;
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
        
        return cardPairs;
    }

    /**
     * Check to see if a pair has been found.
     */
    private void checkClicked() {
        if (clicked.size() == 2) {
            if (clicked.get(0).getPair() == clicked.get(1)) {
                System.out.println(" Pair found!");
                clicked.get(0).setDisable(true);
                clicked.get(1).setDisable(true);
                clicked.remove(0);
                clicked.remove(0);
                canSelect = true;
            } else {
                System.out.println(" Not a pair");
                clicked.get(1).setSelected(true);
                hideAll.setDisable(false);
                canSelect = false;
            }
        }
    }

    /**
     * Randomise the order of nodes in an ObservableList.
     * 
     * @param nodes Nodes to randomise order of.
     */
    private void randomiseTiles(ObservableList<Node> nodes) {
        Random randomGenerator = new Random();
        Card nullCard = new Card(""); // Place holder while nodes are switched.
        for (int i = 0; i < nodes.size(); i++) {
            int randomPos = randomGenerator.nextInt(nodes.size());

            // Need two temps, as can't have duplicates in ObservableList.
            Node tempA = nodes.get(i);
            Node tempB = nodes.get(randomPos);
            nodes.set(i, nullCard);
            nodes.set(randomPos, tempA);
            nodes.set(i, tempB);
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
                        System.out.println("Card already selected");
                    } else if (canSelect) {
                        System.out.print(c.getText());
                        clicked.add(c);
                        c.setSelected(true);
                        checkClicked();
                    } else {
                        System.out.println("Must deselect cards first");
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
        hideAll.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Fill row and col
        hideAll.setOnAction(e -> {
            System.out.println("-Hiding selected cards-");
            for (int i = 0; i < 2; i++) {
                clicked.get(0).setSelected(false);
                clicked.remove(0);
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

    private void configurePlayersPane() {
        GridPane playersPane = new GridPane();
        playersPane.setAlignment(Pos.CENTER);
        playersPane.setVgap(SPACING);
        playersPane.setHgap(SPACING);
        playersPane.setGridLinesVisible(SHOW_MINOR_GRID_LINES);

        // Players
        Text[] players = new Text[2];
        Text p1 = new Text("Player 1");
        Text p2 = new Text("Player 2");
        players[0] = p1;
        players[1] = p2;

        for (Text p : players) {
            p.setTextAlignment(TextAlignment.CENTER);
            p.setStyle("-fx-background-color: #00B299;-fx-text-fill: #004040;-fx-font-size: 16px;");
        }

        playersPane.add(p1, 0, 0);
        playersPane.add(p2, 2, 0);

        for (int i = 0; i < 3; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(33);
            cc.setHgrow(Priority.ALWAYS);
            cc.setFillWidth(true);
            playersPane.getColumnConstraints().add(cc);
        }

        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(20);
        rc.setVgrow(Priority.ALWAYS);
        rc.setFillHeight(true);
        playersPane.getRowConstraints().add(rc);

        gridLayout.add(playersPane, 0, gridLayout.getRowCount(), 4, 2);
    }
}