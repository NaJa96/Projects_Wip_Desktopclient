package de.fhdw.javafx.desktopclient;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("loginview.fxml")); //
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace(); //
		}
	}

	public static void main(String[] args) {
		/*Image image = new Image("Deutsche Bertelsbank.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);*/


		launch(args);
	}
}
