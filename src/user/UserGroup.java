package user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import visitor.CountGroupVisitor;
import visitor.TwitterEntryVisitor;

//UserGroup is a container containing: Users or UserGroups 

public class UserGroup implements TwitterEntry {
	
	//Group ID
	private String groupID; 
		
	//either User or UserGroup 
	private List<TwitterEntry> entries = new ArrayList<TwitterEntry> ();
	
	protected static Map<String, UserGroup> listOfUserGroups = new HashMap<String, UserGroup> (); 
	
//*******************************************************************************//	
//									METHODS 				    				 	
//*******************************************************************************//			
	public UserGroup() {}; 

	//adding a TwitterEntry to the group 
	public void addEntry(TwitterEntry entry) {
		entries.add(entry); 
	}
	
	// accept a visitor 
	@Override
	public void accept(TwitterEntryVisitor visitor) {
		
		//show the total number of groups 
		if(visitor instanceof CountGroupVisitor) {
			visitor.visitGroups(this);
		}
	}
	
	// get the list of UserGroups 
	public static Map<String, UserGroup> getListOfUserGroups() {
		return listOfUserGroups; 
	}
	
	//get the total number user groups 
	@Override
	public int getTotal() {
		return listOfUserGroups.size();
	}
	
//******************************************** Getters and Setters ************************************//
	
	// get the Users and UserGroups that the group is containing 
	public List<TwitterEntry> getEntries() {
		return entries;
	}

	// set the entries 
	public void setEntries(List<TwitterEntry> entries) {
		this.entries = entries;
	}

	//set the group id 
	@Override 
	public void setID(String id) {
		groupID = id; 		
	}

	// get the group id 
	@Override
	public String getName() {
		return groupID;
	}

} 
