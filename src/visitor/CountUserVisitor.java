package visitor;

import user.User;
import user.UserGroup;

public class CountUserVisitor implements TwitterEntryVisitor {

	private int userCounter = 0; 
	
	// visit the User class and get the total number of users 
	@Override
	public void visitTwitterEntry(User user) {
		userCounter = user.getTotal(); 
	}

	@Override
	public void visitMessages(User user) {}

	@Override
	public void visitPosMess(User user) {}

	@Override
	public void visitGroups(UserGroup group) {}
	
	//return the total number of users 
	public int getUserTotal() {
		return userCounter; 
	}

}
