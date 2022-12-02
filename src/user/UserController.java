package user;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

//controlling User UI 
public class UserController implements Initializable {
	
	//the id to follow 
	@FXML private TextField followingId; 
	
	//list of users that the selected user is following 
	@FXML private ListView<String> listOfFollowingUsers; 
	
	//list of Twitter messages 
	@FXML private ListView<String> listOfNewsFeed; 
	
	// the twitter message to post 
	@FXML private TextArea tweetMessage; 
	
	//user view id 
	@FXML private Label userView; 
	
	@FXML private Label hiddenItem; 
	
	//Id of view 
	@FXML private AnchorPane viewId; 

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//set the label to the user's id 
		userView.setText("The User View of: " + TwitterEntryController.currentItem.getValue());
		hiddenItem.setText(TwitterEntryController.currentItem.getValue());
		hiddenItem.setVisible(false);
	} 
	
	// follow a user that the id is typed 
	@FXML 
	public void followUser(ActionEvent event) {
		
		//user id to follow 
		String id = followingId.getText();
		
		//get the user to follow 
		User userToFollow = User.listOfUsers.get(id); 
		
		//get the user 
		User input = User.listOfUsers.get(TwitterEntryController.currentItem.getValue());
		
		input.follow(userToFollow);
		
		//insert the updated version of User in the map
		User.listOfUsers.replace(TwitterEntryController.currentItem.getValue(), input); 
		
		//update the list of following users 
		listOfFollowingUsers.getItems().add(userToFollow.getName()); 
	}
	
	// enter a message and post it 
	@FXML
	public void postTweet(ActionEvent event) throws IOException {
		
		//get the message 
		String tweetPost = tweetMessage.getText(); 
		
		//get the current user 
		User user = User.listOfUsers.get(TwitterEntryController.currentItem.getValue()); 
		
		//add the message to the news feed of user 
		//notify all the observers that the tweetMessage was posted 
		user.addTweetMessage(tweetPost);	
		
		User.listOfUsers.replace(user.getName(), user);  
		
		//update your news feed 
		setInfo(TwitterEntryController.currentItem);
		
		getListToUpdate(user); 
	}
	
	//get the TreeItem when selecting a view 
	@FXML
	public void getTheTreeView(MouseEvent mouseEvent) {
		//get the user id of that view 
		Label label = (Label) viewId.getChildren().get(8); 
		
		//get the corresponding TreeItem of that user id 
		TwitterEntryController.currentItem = new TreeItem<String>(label.getText());  
	}
	
	
	public void getListToUpdate(User user) throws IOException{	
		
		Map<String, TreeItem<String>> listOfTrees = TwitterEntryController.items; 
		
		for(int i = 0; i < user.getFollowers().size(); ++i) {
			
			if(listOfTrees.containsKey(user.getFollowers().get(i))) {
				
				TreeItem<String> item = TwitterEntryController.items.get(user.getFollowers().get(i)); 
				
				//get the user controller 
				UserController userController = TwitterEntryController.control.get(item); 
				userController.setInfo(item);
					
				Stage stage = TwitterEntryController.itemStage.get(item); 
				stage.show(); 	
			}
		}
	}
	
	//set the information of list of following users and messages 
	public void setInfo(TreeItem<String> item) {
		
		//clear the list 
		listOfNewsFeed.getItems().clear();
		listOfFollowingUsers.getItems().clear();
		
		//get the user selected 
		User a = User.listOfUsers.get(item.getValue()); 

		//setting the ListView to show the user's followers' list 
		for(String follower : a.getFollowing()) {
			
			listOfFollowingUsers.getItems().add(follower +"\n"); 
		}
		
		//setting the ListView to show the News Feed 
		for(String tweetMess : a.getTwitterMess()) {
			
			listOfNewsFeed.getItems().add(tweetMess); 
		}
	}

}
