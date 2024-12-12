package GUI;
import java.util.ArrayList;


//this class serves as a storage for the user information that the person running the program enters in the launch screen
//currently the usage is somewhat limited but would allow us the utilize this information for account creation, etc.
//functionality that would require more time that alloted but are entirely possible with the utilization for a user class
public class User {
	
	private String name;
	private ArrayList<String> workouts = new ArrayList<String>();

	  public void setName(String name){
	    this.name = name;
	  }
	  public String returnName(){
	    return this.name;
	  }
	  

}
