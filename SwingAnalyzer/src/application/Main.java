package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage primaryStage;
	private AnchorPane rootLayout;
	
	@Override
	public void start(Stage primaryStage) {
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root,400,400);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Swing Analyzer");
		
		initRootLayout();
	}
	
	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../view/MainUI.fxml"));
			rootLayout = (AnchorPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
