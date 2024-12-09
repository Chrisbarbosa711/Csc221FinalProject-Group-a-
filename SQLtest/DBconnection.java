package SQLtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class DBconnection {
    private Connection conn;

    // Establish connection to the database
    public Connection establishConnection() {
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
    public void closeConnection() {
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
    public ResultSet searchExercise(String searchKey) {
        ResultSet rs = null;
        String query = "SELECT exercise FROM workouts WHERE exercise LIKE ?";  // Use a prepared statement to prevent SQL injection

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + searchKey + "%");  // Use wildcard matching
            rs = pstmt.executeQuery();  // Execute the query
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return rs;
    }
    
    
    // add exercise, mark it as true in tracker for given day
    public void addExercise(String day, String exerciseName) {
        String query = "UPDATE tracker SET " + day + " = TRUE WHERE Track_exercise = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, exerciseName);  // Replace the placeholder
            pstmt.executeUpdate();  // Execute the update

        } catch (SQLException e) {
            System.out.println("Error updating tracker: " + e.getMessage());
        }
    }
    
    // delete exercise, marks it false in tracker for given day
    public void deleteExercise(String exerciseName) {
        String query = "DELETE FROM tracker WHERE Track_exercise = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, exerciseName);  // Replace the placeholder with the exercise name

            pstmt.executeUpdate();  // Execute the delete query

        } catch (SQLException e) {
            System.out.println("Error deleting exercise: " + e.getMessage());
        }
    }

    // exercises by day, returns a result set of exercises, that are marked ture for the given day on the tracker table
    // also includes the reps and sets
    public ResultSet dayExercises(String day) {
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
            return pstmt.executeQuery();  // Return the result set
        } catch (SQLException e) {
            System.out.println("Error retrieving exercises for " + day + ": " + e.getMessage());
            return null;
        }
    }
    
    
    // returns all the details of a given exercise in tracker
    public ResultSet exerciseDetail(String exerciseName) {
        String query = "SELECT * FROM workouts WHERE exercise = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, exerciseName);  // Replace the placeholder with the exercise name
            return pstmt.executeQuery();  // Return the result set
        } catch (SQLException e) {
            System.out.println("Error retrieving details for '" + exerciseName + "': " + e.getMessage());
            return null;
        }
    }
    
    // setting set counts for a exercise on tracker
    public void setSets(String exerciseName, int num) {
        String query = "UPDATE tracker SET sets = ? WHERE Track_exercise = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, num);               // Set the new number of sets
            pstmt.setString(2, exerciseName);  // Set the exercise name
            
            pstmt.executeUpdate();  // Execute the update query

        } catch (SQLException e) {
            System.out.println("Error updating sets for '" + exerciseName + "': " + e.getMessage());
        }
    }
    
    // setting rep counts
    public void setReps(String exerciseName, int num) {
        String query = "UPDATE tracker SET reps = ? WHERE Track_exercise = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, num);               // Set the new number of reps
            pstmt.setString(2, exerciseName);  // Set the exercise name

            pstmt.executeUpdate();  // Execute the update query

        } catch (SQLException e) {
            System.out.println("Error updating reps for '" + exerciseName + "': " + e.getMessage());
        }
    }
    
    
    
}

