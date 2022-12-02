package visitor;

import user.User;
import user.UserGroup;

public interface TwitterEntryVisitor {
	
	// visit the User class and get the total number of users 
	public void visitTwitterEntry(User user);
	
	// visit the User class and get the total number of messages 
	public void visitMessages(User user);
	
	// visit the User class and get the percentage of positive messages
	public void visitPosMess(User user);
	
	// visit the UserGroup class and get the total number of user groups 
	public void visitGroups(UserGroup group); 

}
