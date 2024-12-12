package SQLtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class DBconnection {
    private static Connection conn;
    

    // Establish connection to the database
    public static Connection establishConnection() {
        try {
            // Database credentials
            String url = "jdbc:mysql://localhost:3306/workoutsdb"; // Update with your database name
            String user = "root"; // Your MySQL username
            String password = "ktitanbbsql04"; // Your MySQL password
            
            // Create the connection
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established successfully!");
            return conn;  // Return the established connection
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    // Close the connection
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connection closed successfully!");
            } catch (SQLException e) {
                System.out.println("Failed to close the connection: " + e.getMessage());
            }
        }
    }
    
   
    // search function:
    public static ResultSet searchExercise(String searchKey) {
    	System.out.println("Search request recieved");
        ResultSet rs = null;
        String query = "SELECT exercise FROM workouts WHERE exercise LIKE ?";  // Use a prepared statement to prevent SQL injection

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + searchKey + "%");  // Use wildcard matching
            rs = pstmt.executeQuery();  // Execute the query
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        System.out.println("Returning searchExercise result set");
        return rs;
    }

    
    
    // add exercise, mark it as true in tracker for given day
    public static void addExercise(String day, String exerciseName) {
    	System.out.println("Add exercise request recieved for day: " + day + " and exercise: " + exerciseName);
        String query = "UPDATE tracker SET " + day + " = TRUE WHERE Track_exercise = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, exerciseName);  // Replace the placeholder
            pstmt.executeUpdate();  // Execute the update
            System.out.println("adding exercise...");
        } catch (SQLException e) {
            System.out.println("Error updating tracker: " + e.getMessage());
        }
        
    }
    
 // delete exercise, marks it false in tracker for given day
    public static void deleteExercise(String day, String exerciseName) {
        System.out.println("Delete exercise request received");

        // Update the specific day column and mark it as false
        String query = "UPDATE tracker SET " + day + " = FALSE WHERE Track_exercise = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, exerciseName);  // Replace the placeholder with the exercise name
            pstmt.executeUpdate();  // Execute the update query
            System.out.println("Deleting exercise...");
        } catch (SQLException e) {
            System.out.println("Error updating tracker: " + e.getMessage());
        }
    }


    // exercises by day, returns a result set of exercises, that are marked ture for the given day on the tracker table
    // also includes the reps and sets
    public static ResultSet dayExercises(String day) {
    	
    	System.out.println("Day Exercise request recieved for day : " + day);
    	
    	String query = "SELECT Track_exercise, sets, reps FROM tracker WHERE " + day + " = TRUE";
        try {
            // Validate the day to prevent SQL injection
            Set<String> validDays = Set.of("monday", "tuesday", "wednesday", 
                                           "thursday", "friday", "saturday", "sunday");

            if (!validDays.contains(day.toLowerCase())) { // only expects days of the week 
                System.out.println("Invalid day: " + day);
                return null;
            }

            PreparedStatement pstmt = conn.prepareStatement(query);
            System.out.println("Returning dayExercise result set");
            return pstmt.executeQuery();  // Return the result set
        } catch (SQLException e) {
            System.out.println("Error retrieving exercises for " + day + ": " + e.getMessage());
            return null;
        }
        
    }
    
    
    // returns all the details of a given exercise in tracker
    public static ResultSet exerciseDetail(String exerciseName) {
    	System.out.println("exercise detail request recieved");
        String query = "SELECT * FROM workouts WHERE exercise = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, exerciseName);  // Replace the placeholder with the exercise name
            System.out.println("Returning exercise detail rs");
            return pstmt.executeQuery();  // Return the result set
        } catch (SQLException e) {
            System.out.println("Error retrieving details for '" + exerciseName + "': " + e.getMessage());
            return null;
        }
    }
    
    // setting set counts for a exercise on tracker
    public static void setSets(String exerciseName, int num) {
    	System.out.println("set Sets request recieved");
        String query = "UPDATE tracker SET sets = ? WHERE Track_exercise = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, num);               // Set the new number of sets
            pstmt.setString(2, exerciseName);  // Set the exercise name
            System.out.println("Setting sets...");
            pstmt.executeUpdate();  // Execute the update query

        } catch (SQLException e) {
            System.out.println("Error updating sets for '" + exerciseName + "': " + e.getMessage());
        }
    }
    
    // setting rep counts
    public static void setReps(String exerciseName, int num) {
    	System.out.println("set Reps request recieved");
        String query = "UPDATE tracker SET reps = ? WHERE Track_exercise = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, num);               // Set the new number of reps
            pstmt.setString(2, exerciseName);  // Set the exercise name
            System.out.println("Setting reps");
            pstmt.executeUpdate();  // Execute the update query

        } catch (SQLException e) {
            System.out.println("Error updating reps for '" + exerciseName + "': " + e.getMessage());
        }
    }
    
    public static void deleteWorkouts() {
        String[] week = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
       
        String updateSql = "UPDATE tracker SET Done = 0, Monday = 0, Tuesday = 0, Wednesday = 0, Thursday = 0, Friday = 0, Saturday = 0, Sunday = 0 " +
                           "WHERE Track_exercise = ?";

        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
            for (String day : week) {
            	System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
                try (ResultSet exercises = dayExercises(day)) {
                    while (exercises != null && exercises.next()) {
                        String exerciseName = exercises.getString("Track_exercise");
                        updateStmt.setString(1, exerciseName);
                        updateStmt.addBatch();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("Error: " + ex.getMessage());
                }
            }
            updateStmt.executeBatch();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
}

