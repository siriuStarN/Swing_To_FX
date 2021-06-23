package controller;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TreeViewController {
	VBox rootBox;
	Stage dialog;
	TreeView<String> treeView;
	
	public TreeViewController(TreeItem<String> inputTree) {
		rootBox = new VBox();
		rootBox.setPrefSize(300, 400);
		dialog = new Stage();
		createScene();
		
		rootBox.setSpacing(10);
		treeView = new TreeView<String>();
		treeView.setRoot(inputTree);
		
		rootBox.getChildren().add(treeView);
		
		dialog.setTitle("Swing Hierachy");
		dialog.setResizable(false);
		
	}
	
	public void showDialog() {
		Platform.runLater(() -> {
			dialog.show();
		});
	}
	
	public void createScene() {
		Scene scene = new Scene(rootBox);
		dialog.setScene(scene);
	}
}
