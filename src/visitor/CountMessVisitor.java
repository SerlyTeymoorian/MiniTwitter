package visitor;

import user.User;
import user.UserGroup;

public class CountMessVisitor implements TwitterEntryVisitor{
	
	private  int messCounter = 0; 

	@Override
	public void visitTwitterEntry(User user) {}

	// visit the User class and get the total number of messages 
	@Override
	public void visitMessages(User user) {
		messCounter = user.getTotMess(); 
	}

	@Override
	public void visitPosMess(User user) {}

	@Override
	public void visitGroups(UserGroup group) {}
	
	//return the total number of messages 
	public int getMessCounter() {
		return messCounter; 
	}

}
