import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
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

    private static final String BACKGROUND_STYLE = "-fx-background-color: DAE6F3;";
    private static final String FILENAME = "cardPairs.txt";
    private static final int SPACING = 10;

    private Pane layout;
    private Scene scene;
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
    public void start(Stage window) {
        // Configure the scene and window
        configureLayout();
        scene = new Scene(layout, 500, 450);
        window.setResizable(false);
        window.setScene(scene);
        window.setTitle("Memory game");

        window.show();
    }

    /**
     * Configures the layout for the cards.
     */
    private void configureLayout() {
        layout = addGridPane(); // Removes error while I keep both options open.
        try {
            layout = addTilePane(readCardStrings(FILENAME));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        layout.setStyle(BACKGROUND_STYLE);
        layout.setPadding(new Insets(SPACING)); // If all different: ^ > v <
    }

    /**
     * Test using grid pane for cards.
     * Each card must be assigned a row and column.
     * 
     * @return GridPane.
     */
    private GridPane addGridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(SPACING);
        grid.setHgap(SPACING);

        // Populate grid
        for (int i = 0; i < 5; i++) { // Column
            for (int j = 0; j < 3; j++) { // Row
                grid.add(new Card("Card " + i + " " + j), i, j);
            }
        }
        return grid;
    }

    /**
     * Test using a tile pane for cards.
     * Automatically fills tiles in order (1D array).
     * 
     * @return TilePane.
     */
    private TilePane addTilePane(ArrayList<String[]> cardStrings) {
        TilePane tiles = new TilePane();
        tiles.setAlignment(Pos.CENTER);
        tiles.setVgap(SPACING);
        tiles.setHgap(SPACING);

        /*for (String[] strings : cardStrings) {
            System.out.println(strings[0] + " " + strings[1]);
        }*/

        // Populate tiles with cards
        for (String[] cardPair : cardStrings) {
            Card c1 = new Card(cardPair[0]);
            Card c2 = new Card(cardPair[1]);
            c1.setPair(c2);
            c2.setPair(c1);
            tiles.getChildren().addAll(c1,c2);
        }

        // Make all cards print their name.
        ObservableList<Node> nodes = tiles.getChildren();
        for (Node node : nodes) {
            if (node instanceof Card) {
                Card c = (Card) node;
                c.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        System.out.print(c.getText()); // Print name on click.
                        clicked.add(c);
                        c.setSelected(true);
                        checkClicked();
                    }
                });
            } else {
                System.out.println(node.getClass());
            }
        }
        randomise(nodes);
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
            } else {
                System.out.println(" Not a pair");
                clicked.get(0).setSelected(false);
                Card temp = clicked.get(1);
                temp.setSelected(false);
                temp.setStyleHover();
            }
            clicked.remove(0);
            clicked.remove(0);
        }
    }

    /**
     * Randomise the order of nodes in an ObservableList.
     * 
     * @param nodes Nodes to randomise order of.
     */
    private void randomise(ObservableList<Node> nodes) {
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
}