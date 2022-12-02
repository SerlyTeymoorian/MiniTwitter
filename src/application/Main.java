/*
 Author: Serly Teymoorian 
 3560 - Assignment 2 
 */
package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;


//trigger the UI 
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		AdminPanel adminPanel = AdminPanel.getInstance(); 
		
		primaryStage.setTitle("MiniTwitter");
		primaryStage.setScene(adminPanel.getScene());
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
