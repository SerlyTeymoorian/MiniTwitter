package observer;

public interface Observer {
	
	//observe the Subject class and update if any change occurs 
	public void update(Subject subject, String message); 
}