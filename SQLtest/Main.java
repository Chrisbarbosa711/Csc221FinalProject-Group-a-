package SQLtest;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
    	
    	DBconnection.establishConnection();
    	
        String searchKey = "dumbbell";  // The exercise you want to search for
        ResultSet rs = DBconnection.searchExercise(searchKey);  // Get the ResultSet from the DBconnection

      
        ArrayList<String> exercises = UnpackRS.unpackSearch(rs);

     
        if (exercises != null && !exercises.isEmpty()) {
              System.out.println("Found exercises:");
            for (String exercise : exercises) {
                System.out.println(exercise);
            }
        } else {
            System.out.println("No exercises found for " + searchKey);
        }
    	

    	
        DBconnection.closeConnection();
	
    }
}
