package GUI;
import java.util.ArrayList;

public class User{

  private String name;
  private ArrayList<String> workouts = new ArrayList<String>();

  public void setName(String name){
    this.name = name;
  }
  public String returnName(){
    return this.name;
  }

  public void addWorkout(String workout){
    this.workouts.add(workout);
  }

  public void removeWorkout(String workout){
    this.workouts.remove(workout);
  }

  public String returnWorkouts(){
	    System.out.printf("Workouts added:\n");
	    if(this.workouts.size() == 0){
	      System.out.println("You have no workouts");
	    }
	    else{
	      for(String w : workouts){
	        System.out.println(w);
	      }
	    }
	    return "";
	  }

  public Boolean checkWorkout(String workout){
    if(workouts.size() == 0){
      return false;
    }
    return this.workouts.contains(workout);
  }
  
  
}
