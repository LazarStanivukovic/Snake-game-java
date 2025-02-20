package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {
	public static Database datab = new Database("BAZA");
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
	        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
	        primaryStage.setTitle("Database Manager");
	        primaryStage.setScene(scene);
	        primaryStage.setOnCloseRequest(event -> {
	            Platform.exit();  // Terminates the JavaFX application properly
	            System.exit(0);   // Ensures the entire process terminates
	        });
	        //Platform.setImplicitExit(false);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
}
