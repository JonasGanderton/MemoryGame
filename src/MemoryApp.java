import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class MemoryApp extends Application {

    private Pane layout;
    private Scene scene;
    private static final int SPACING = 10;

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
        // Configure the layout
        layout = addGridPane();
        layout = addTilePane();
        

        layout.setStyle("-fx-background-color: DAE6F3;");
        layout.setPadding(new Insets(SPACING)); // If all different: ^ > v <
        
        // Configure the scene and window
        scene = new Scene(layout, 500, 450);
        window.setResizable(false);
        window.setScene(scene);
        window.setTitle("Memory game");

        window.show();
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

        for (int i = 0; i < 5; i++) { // Column
            for (int j = 0; j < 2; j++) { // Row
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
    private TilePane addTilePane() {
        TilePane tiles = new TilePane();
        tiles.setAlignment(Pos.CENTER);
        tiles.setVgap(SPACING);
        tiles.setHgap(SPACING);
        
        String name = "test";
        for (int i = 0; i < 20; i++) {
            name += i;
            Card c = new Card(name);
            c.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent event) {
                    System.out.println(c.getText());
                }
            });
            tiles.getChildren().add(c);
        }
        return tiles;
    }
}