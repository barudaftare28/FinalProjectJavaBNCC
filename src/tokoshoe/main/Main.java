package tokoshoe.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tokoshoe.view.SepatuForm;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);  // Start the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            SepatuForm sepatuForm = new SepatuForm();  // Initialize SepatuForm
            Scene scene = new Scene(sepatuForm, 800, 600);  // Create Scene with desired size
            primaryStage.setScene(scene);  // Set the Scene to the primary Stage
            primaryStage.setTitle("Toko Sepatu - Manajemen Produk");  // Set window title
            primaryStage.show();  // Show the application window
        } catch (Exception e) {
            e.printStackTrace();  // Print any exceptions to the console
        }
    }
}
