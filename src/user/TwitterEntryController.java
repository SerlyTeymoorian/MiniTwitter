package user;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import visitor.CountGroupVisitor;
import visitor.CountMessVisitor;
import visitor.CountUserVisitor;
import visitor.PosMessVisitor;
import java.util.HashMap;

//controlling the Main UI 
public class TwitterEntryController implements Initializable { 

	// getting tree view 
	@FXML private TreeView<String> treeView; 	
	
	// text where to enter group id 
	@FXML private TextField groupId; 
	
	// text where to enter user id 
	@FXML private TextField userId;
	
	@FXML private Label userView; 
	
	private boolean valid = true; 
	
	protected static TreeItem<String> currentItem;  
	
	private Alert alert = new Alert(Alert.AlertType.INFORMATION);
	
	//add an icon to the UserGroup 
	private ImageView icon = new ImageView("icon/group.png"); 
	
	//protected so UserController can have access to them 
	protected static Map<String, TreeItem<String>> items = new HashMap<String, TreeItem<String>>(); 
	
	protected static Map<TreeItem<String>, Stage> itemStage = new HashMap<TreeItem<String>, Stage>(); 
	
	protected static Map<TreeItem<String>, UserController> control = new HashMap<TreeItem<String>, UserController>(); 
	
//*******************************************************************************//	
//									METHODS 				    				 
//*******************************************************************************//	
	
	// set the Root of the Tree 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//create a root object TreeItem 
		UserGroup rootEntry = new UserGroup(); 
		rootEntry.setID("Root");	
		
		//set the width and height of the icon 
		icon.setFitHeight(17);
		icon.setFitWidth(25); 
		
		//put it into the UserGroup map 
		UserGroup.listOfUserGroups.put(rootEntry.getName(), rootEntry); 
		
		//create a TreeItem from the Root group 
		TreeItem<String> rootItem = new TreeItem<> (rootEntry.getName(), icon); 
		rootItem.setExpanded(true);
		
		//set the root item as a User Group => Root 
		treeView.setRoot(rootItem);
		treeView.setShowRoot(true);
		treeView.setVisible(true);
	}
	
	// Switch the scene from AdminPanel to UserView if the selected item is a User not a group
	@FXML
	public void switchUserUI(ActionEvent event) throws IOException {
		
		//is the selected item is a User and not a Group 
		if(User.listOfUsers.containsKey(currentItem.getValue())) {
			
			//load the user  file 
			FXMLLoader loader = new FXMLLoader(getClass().getResource("UserUI.fxml")); 
			Parent root = loader.load(); 
			//get the user controller 
			UserController userController = loader.getController();
			
			//update information 
			userController.setInfo(currentItem);
			
			control.put(currentItem, userController); 
			
			//set the stage and pass in the scene containing the root 
			Stage stage = new Stage(); 
			Scene scene = new Scene(root);
			stage.setTitle("User View");
			stage.setScene(scene);
			
			items.put(currentItem.getValue(), currentItem); 
			itemStage.put(currentItem, stage); 
			
			stage.show();
			
		}	
	}

	
	// Click on a TreeItem and get the item 	
	@FXML
	public void mouseClick(MouseEvent mouseEvent) {
		currentItem = treeView.getSelectionModel().getSelectedItem(); 
	}

	//add a new user to the selected user group 
	@FXML
	public void addUser(ActionEvent event) {
		
		//if the selected item is a group and a user appear only in one group
		if(UserGroup.listOfUserGroups.containsKey(currentItem.getValue()) && !User.listOfUsers.containsKey(userId.getText())) {
			
			//get the reference to the selected UserGroup 
			UserGroup currentItemGroup = UserGroup.listOfUserGroups.get(currentItem.getValue()); 
			
			//create a new UserGroup from the given GriupId 
			User input = new User();
			input.setID(userId.getText());
			
			//add it to the selected group's entry 
			currentItemGroup.addEntry(input);
			
			//add to the user list 
			User.listOfUsers.put(userId.getText(), input); 
			UserGroup.listOfUserGroups.replace((String) currentItem.getValue(), currentItemGroup); 
			
			if(input.getName().indexOf(' ') >= 0) {
				valid = false; 
			}
			
			//pass the group to the TreeView 
			currentItem.getChildren().add(new TreeItem<String>(input.getName()));
		}	 
	}
	
	//add a new User Group to the Selected User Group 
	@FXML
	public void addGroup(ActionEvent event) {

		//if the selected item is a group
		if(UserGroup.listOfUserGroups.containsKey(currentItem.getValue())) {
			
			//get the reference to the selected UserGroup 
			UserGroup currentItemGroup = UserGroup.listOfUserGroups.get(currentItem.getValue()); 
			
			//create a new UserGroup from the given GriupId 
			UserGroup input = new UserGroup();
			input.setID(groupId.getText());
			
			//add it to the selected group's entry 
			currentItemGroup.addEntry(input); 
			UserGroup.listOfUserGroups.replace((String) currentItem.getValue(), currentItemGroup); 
			
			//add the group the list of groups 
			UserGroup.listOfUserGroups.put(groupId.getText(), input); 
			
			TreeItem<String> itemToAdd = new TreeItem<>(input.getName(), icon);
			
			//pass the group to the TreeView 
			currentItem.getChildren().add(itemToAdd); 
		}
	}

	// Show the total number of Users 
	@FXML
	public void showUserTotal() {
		
		User user = new User(); 
		
		//create an instance of a visitor 
		CountUserVisitor userVisitor = new CountUserVisitor(); 
		
		//accept the visitor 
		user.accept(userVisitor);
		
		//get the total number of users 
		int numOfUsers = userVisitor.getUserTotal(); 
		
		//set the alert 
		alert.setTitle("Total Number of Users");
		alert.setContentText("The total number of users is: " + numOfUsers);
		alert.show();
	}

	// Show the total number of User Groups 
	@FXML
	public void groupTotal(ActionEvent event) {
		
		UserGroup group = new UserGroup();
		//create an instance of visitor 
		CountGroupVisitor groupVisitor = new CountGroupVisitor(); 
		
		//accept the visitor
		group.accept(groupVisitor);
		
		//get the total number of groups 
		int numOfGroups = groupVisitor.getGroupCounter(); 
		
		//set the alert 
		alert.setTitle("Total Number of Groups");
		alert.setContentText("The total number of groups is: " + numOfGroups);
		alert.show();
	}

	//Show the total number of Twitter Messages 
	@FXML
	public void showTotalMessages() {
		
		User user = new User(); 
		
		//create an instance of visitor 
		CountMessVisitor messVisitor = new CountMessVisitor(); 
		
		//accept the visitor 
		user.accept(messVisitor);
		
		//get the total number of messages 
		int numOfTotalMess = messVisitor.getMessCounter(); 
		
		//set the alert 
		alert.setTitle("Total # of Messsages");
		alert.setContentText("Total Number of Twitter Messages is: " + numOfTotalMess);
		alert.show();
	}

	// Show the percentage of positive messages 
	@FXML
	public void showPosPercentage() {
		
		User user = new User(); 
		
		//Create an instance of visitor 
		PosMessVisitor posMessVisitor = new PosMessVisitor();
		
		//accept the visitor 
		user.accept(posMessVisitor);
		
		double percentageOfPosMess = posMessVisitor.getPercentage(); 
		
		//set the alert 
		alert.setTitle("Positive Percentage Messages");
		alert.setContentText("The percentage of positive messages is: " + percentageOfPosMess + "%");
		alert.show();
	}
	
	//validate 
	@FXML
	public void validate(ActionEvent event) {
		
		alert.setTitle("Validate Result");
		
		if(valid) {
			alert.setContentText(String.valueOf("All the User Ids are Valid!!"));
		} else {
			alert.setContentText(String.valueOf("All the User Ids are not Valid"));
		}
		
		alert.show();
	}
}
