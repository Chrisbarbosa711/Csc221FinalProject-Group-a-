package GUI;
import java.util.ArrayList;

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
