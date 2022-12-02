package visitor;

import user.User;
import user.UserGroup;

public class PosMessVisitor implements TwitterEntryVisitor{
	
	private double percentage = 0.0; 

	@Override
	public void visitTwitterEntry(User user) {}

	@Override
	public void visitMessages(User user) {}

	// visit the User class and get the percentage of positive messages 
	@Override
	public void visitPosMess(User user) {
		
		int posMess = 0; 
		
		String mess; 
		
		//go through the list of users 
		for(int i = 0; i < User.getDistinctMess().size(); ++i) {
		
			mess = User.getDistinctMess().get(i);
			
			if(mess.contains("good") || mess.contains("great") || mess.contains("excellent") || mess.contains("awesome") || mess.contains("good job") || mess.contains("happy")) {
				++posMess; 
			}	
		}
		percentage = ((double)posMess / (double)User.getDistinctMess().size()) * 100;  
	}

	@Override
	public void visitGroups(UserGroup group) {}
	
	// return the percentage of positive messages 
	public double getPercentage() {
		return percentage; 
	}

}
