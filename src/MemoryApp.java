import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class MemoryApp extends Application {

    private TilePane layout;
    private Scene scene;

    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage window) {
        layout = new TilePane();
        scene = new Scene(layout, 350, 300);

        layout.setStyle("-fx-background-color: DAE6F3;");

        String name = "test";
        for (int i = 0; i < 20; i++) {
            name += i;
            Card c = new Card(name);
            c.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent event) {
                    System.out.println(c.getText());
                }
            });
            layout.getChildren().add(c);
        }
        
        window.setScene(scene);
        window.setTitle("Memory game");
        window.show();
    }
}