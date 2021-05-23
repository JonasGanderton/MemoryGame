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
        scene = new Scene(layout, 500, 450);
        window.setResizable(false);
        window.setScene(scene);
        window.setTitle("Memory game");

        window.show();
    }

    /**
     * Configures the layout for the cards.
     */
    private void configureLayout() throws FileNotFoundException {
        // Add cards
        layout = addGridPane(readCardStrings(FILENAME));
        layout = addTilePane(readCardStrings(FILENAME));
        setCardsActions();
        randomise(layout.getChildren());

        // Some layout settings
        layout.setStyle(BACKGROUND_STYLE);
        layout.setPadding(new Insets(SPACING)); // If all different: ^ > v <

        // Add hide all card
        hideAll = new VisibleCard("Flip selected cards");
        hideAll.setDisable(true);
        hideAll.setOnAction(e -> {
            System.out.println("-Hiding selected cards-");
            for (int i = 0; i < 2; i++) {
                clicked.get(0).setSelected(false);
                clicked.remove(0);
            }
            hideAll.setDisable(true);
            canSelect = true;
        });
        layout.getChildren().add(hideAll);
    }

    /**
     * Test using grid pane for cards.
     * Each card must be assigned a row and column.
     * 
     * @return GridPane.
     */
    private GridPane addGridPane(ArrayList<String[]> cardStrings) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(SPACING);
        grid.setHgap(SPACING);

        // Populate grid with cards
        for (String[] cardPair : cardStrings) {
            Card c1 = new Card(cardPair[0]);
            Card c2 = new Card(cardPair[1]);
            c1.setPair(c2);
            c2.setPair(c1);
            grid.getChildren().addAll(c1, c2);
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

        // Populate tiles with cards
        for (String[] cardPair : cardStrings) {
            Card c1 = new Card(cardPair[0]);
            Card c2 = new Card(cardPair[1]);
            c1.setPair(c2);
            c2.setPair(c1);
            tiles.getChildren().addAll(c1,c2);
        }
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

    /**
     * When clicked, a card will try to:
     * - Print its name
     * - Get added to clicked list
     * - Go into selected mode
     */
    private void setCardsActions() {
        ObservableList<Node> nodes = layout.getChildren();
        for(Node node : nodes) {
            if (node instanceof Card) {
                Card c = (Card) node;

                c.setOnAction(e -> {
                    if (canSelect) {
                        System.out.print(c.getText());
                        clicked.add(c);
                        c.setSelected(true);
                        checkClicked();
                    } else {
                        System.out.println("Must deselect cards first");
                    }
                });
            } else {
                System.out.println(node.getClass());
            }
        }
    }
}