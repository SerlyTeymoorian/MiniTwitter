package visitor;

import user.User;
import user.UserGroup;

public class CountGroupVisitor implements TwitterEntryVisitor{

	private int groupCounter = 0; 
	
	@Override
	public void visitTwitterEntry(User user) {}

	@Override
	public void visitMessages(User user) {}

	@Override
	public void visitPosMess(User user) {}

	//visit the UserGroup and get total number of user groups  
	@Override
	public void visitGroups(UserGroup group) {
		groupCounter = group.getTotal(); 
	}

	// return the total number of groups 
	public int getGroupCounter() {
		return groupCounter; 
	}
}
