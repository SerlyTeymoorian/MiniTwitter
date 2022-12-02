package user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import observer.Observer;
import observer.Subject;
import visitor.CountMessVisitor;
import visitor.CountUserVisitor;
import visitor.PosMessVisitor;
import visitor.TwitterEntryVisitor;

//User is a type of TwitterEntry 
//Subclass of TreeItem 
//	-> merge the User and the TreeItem
//User is both a Subject and an Observer
// -> when others following it (Subject) and when following others (Observer)
//	whenever post sth, notify all the followers 

public class User extends Subject implements TwitterEntry, Observer  {
	
	//unique User id
	private String userId;
	
	private static int totMess = 0; 
	
	//list of followers' User IDs: User IDs following this user 
	private List<String> followers = new ArrayList<String>(); 
	
	//list of following User IDs: List User IDs this user is following 
	private List<String> following = new ArrayList<String>(); 
	
	//news feed list containing list of Twitter messages 
	private List<String> twitterMess = new ArrayList<String>(); 
	
	protected static Map<String, User> listOfUsers = new HashMap<String, User> (); 
	
	private static List<String> distinctTwitterMess = new ArrayList<String>(); 

//*******************************************************************************//	
//									METHODS 				    				 
//*******************************************************************************//		
	public User() {};

	//to observe
	//Whenever a new message is posted, 
	//    => all the followers’ news feed list view should be updated and refreshed automatically.
	@Override
	public void update(Subject subject, String message) {
		
		if(subject instanceof User) {
			User a = (User)subject; 
			
			//add the tweetMessage to the observer 
			this.twitterMess.add(a.getName() + ": " + message); 
			
			//replace the old version with updated version 
			User.listOfUsers.replace(this.getName(), this); 
		}
		
	} 
	
	//accept a Visitor (4 types of visitors) 
	@Override
	public void accept(TwitterEntryVisitor visitor) {
		
		if(visitor instanceof CountUserVisitor) {
			visitor.visitTwitterEntry(this); 
			
		} else if(visitor instanceof CountMessVisitor) {
			visitor.visitMessages(this);
			
		} else if(visitor instanceof PosMessVisitor) {
			visitor.visitPosMess(this);
		}
	}
	
	//follow other user 
	public void follow(User user) {
		
		following.add(user.getName()); 
		
		//add this user to the follower's list of input user 
		user.addFollower(this);
		
		//attach this user as an observer to the followed user 
		user.attach(this);
		
		User.listOfUsers.replace(user.getName(), user); 
		
	}
	
	//follow a user 
	public void addFollower(User user) {
		
		//check to make sure the input user is not already followed 
		if(!followers.contains(user.getName())) {
			followers.add(user.getName()); 
		}
	}
	
	//add a twitter message and notify the observers 
	public void addTweetMessage(String mess) {
		//add the mess 
		twitterMess.add(this.getName() + ": " + mess);
		
		//add it to distinct messages 
		distinctTwitterMess.add(mess); 
		
		//increment the total # of messages 
		++totMess; 
		
		//update the news feed of all observers (followers) 
		//notify all the observers about the update 
		this.notifyObservers(mess);
	}

	//get the total number of users 
	@Override
	public int getTotal() {
		return listOfUsers.size();
	}
	
	//get the user id 
	@Override
	public String getName() {
		return userId;
	}

	//set the user ID 
	@Override
	public void setID(String id) {
		userId = id; 
	}

//************************************** Getters and Setters *************************************//
	public void setTotMess(int num) {
		totMess = num; 
	}
	public int getTotMess() {
		return totMess; 
	}
	//get the list of followers 
	public List<String> getFollowers() {
		return followers;
	}

	//set the list of followers 
	public void setFollowers(List<String> followers) {
		this.followers = followers;
	}

	//get the list of following 
	public List<String> getFollowing() {
		return following;
	}

	//set the list of following 
	public void setFollowing(List<String> following) {
		this.following = following;
	}

	//get the list of twitter messages 
	public List<String> getTwitterMess() {
		return twitterMess;
	}

	//set the twitter messages 
	public void setTwitterMess(List<String> twitterMess) {
		this.twitterMess = twitterMess;
	}

	//get the list of users 
	public Map<String, User> getListOfUsers(){
		return listOfUsers; 
	}
	
	public static List<String> getDistinctMess(){
		return distinctTwitterMess; 
	}

}
