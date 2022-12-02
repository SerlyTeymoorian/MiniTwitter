package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


//SINGLETON Class 
public class AdminPanel {
	
	//private static object 
	private static AdminPanel instance; 
	
	//Group of panes to include in a scene 
	private Parent root; 
	
	private Scene scene; 
	
	//private constructor 
	private AdminPanel() throws IOException {
		root = FXMLLoader.load(getClass().getResource("/user/MainUI.fxml"));
		scene = new Scene(root); 
	}; 
	
	//static getter 
	public static AdminPanel getInstance() throws IOException {
		if(instance == null) {
			return new AdminPanel(); 
			
		} else {
			return instance; 
		}
	}

	//return the root 
	public Scene getScene() {
		return scene;
	}
}
