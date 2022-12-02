package visitor;

public interface Visitable {
	
	// accept a visitor 
	public void accept(TwitterEntryVisitor visitor); 

}
