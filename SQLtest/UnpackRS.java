package SQLtest;

import java.sql.*;
import java.util.ArrayList;

public class UnpackRS {

    // Static method to unpack a ResultSet into an ArrayList of exercises (strings)
    public static ArrayList<String> unpackSearch(ResultSet rs) {
        ArrayList<String> exercises = new ArrayList<>();

        try {
            // Iterate through the ResultSet and add each exercise to the list
            while (rs.next()) {
                String exercise = rs.getString("exercise");  // Get the exercise name from the ResultSet
                exercises.add(exercise);  // Add to the ArrayList
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exercises;  // Return the ArrayList containing the exercises
    }
	
    
    
    public static ArrayList<String[]> unpackDayExercise(ResultSet rs) {
        ArrayList<String[]> dayexerciseDetails = new ArrayList<>();

        try {
            // Iterate through the ResultSet
            while (rs.next()) {
                String trackExercise = rs.getString("Track_exercise");  // Get exercise name
                int sets = rs.getInt("sets");  // Get sets (integer)
                int reps = rs.getInt("reps");  // Get reps (integer)

                // Convert sets and reps to strings
                String setsStr = String.valueOf(sets);
                String repsStr = String.valueOf(reps);

                // Create a String[] with exercise, sets, and reps
                String[] details = new String[]{trackExercise, setsStr, repsStr};

                // Add the details to the ArrayList
                dayexerciseDetails.add(details);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dayexerciseDetails;  // Return the ArrayList of exercise details
    }
	
	
    public static String[] unpackExerciseDetailsByName(ResultSet rs) {
        String[] exerciseDetails = new String[25]; // Array to hold all the column values (including ID)

        try {
            if (rs.next()) { // We expect only one row, so we check for that
                // Extracting each column by name, with null handling

                exerciseDetails[0] = String.valueOf(rs.getInt("ID"));  // ID is an integer, convert it to a string
                exerciseDetails[1] = rs.getString("Exercise") != null ? rs.getString("Exercise") : "N/A";
                exerciseDetails[2] = rs.getString("DifficultyLevel") != null ? rs.getString("DifficultyLevel") : "N/A";
                exerciseDetails[3] = rs.getString("TargetMuscleGroup") != null ? rs.getString("TargetMuscleGroup") : "N/A";
                exerciseDetails[4] = rs.getString("PrimeMoverMuscle") != null ? rs.getString("PrimeMoverMuscle") : "N/A";
                exerciseDetails[5] = rs.getString("SecondaryMuscle") != null ? rs.getString("SecondaryMuscle") : "N/A";
                exerciseDetails[6] = rs.getString("TertiaryMuscle") != null ? rs.getString("TertiaryMuscle") : "N/A";
                exerciseDetails[7] = rs.getString("PrimaryEquipment") != null ? rs.getString("PrimaryEquipment") : "N/A";
                exerciseDetails[8] = rs.getString("SecondaryEquipment") != null ? rs.getString("SecondaryEquipment") : "N/A";
                exerciseDetails[9] = rs.getString("Posture") != null ? rs.getString("Posture") : "N/A";
                exerciseDetails[10] = rs.getString("SingleorDoubleArm") != null ? rs.getString("SingleorDoubleArm") : "N/A";
                exerciseDetails[11] = rs.getString("ContinuousOrAlternatingArms") != null ? rs.getString("ContinuousOrAlternatingArms") : "N/A";
                exerciseDetails[12] = rs.getString("Grip") != null ? rs.getString("Grip") : "N/A";
                exerciseDetails[13] = rs.getString("LoadPositionEnding") != null ? rs.getString("LoadPositionEnding") : "N/A";
                exerciseDetails[14] = rs.getString("FootElevation") != null ? rs.getString("FootElevation") : "N/A";
                exerciseDetails[15] = rs.getString("CombinationExercises") != null ? rs.getString("CombinationExercises") : "N/A";
                exerciseDetails[16] = rs.getString("MovementPattern1") != null ? rs.getString("MovementPattern1") : "N/A";
                exerciseDetails[17] = rs.getString("MovementPattern2") != null ? rs.getString("MovementPattern2") : "N/A";
                exerciseDetails[18] = rs.getString("MovementPattern3") != null ? rs.getString("MovementPattern3") : "N/A";
                exerciseDetails[19] = rs.getString("PlaneOfMotion1") != null ? rs.getString("PlaneOfMotion1") : "N/A";
                exerciseDetails[20] = rs.getString("PlaneOfMotion2") != null ? rs.getString("PlaneOfMotion2") : "N/A";
                exerciseDetails[21] = rs.getString("PlaneOfMotion3") != null ? rs.getString("PlaneOfMotion3") : "N/A";
                exerciseDetails[22] = rs.getString("BodyRegion") != null ? rs.getString("BodyRegion") : "N/A";
                exerciseDetails[23] = rs.getString("ForceType") != null ? rs.getString("ForceType") : "N/A";
                exerciseDetails[24] = rs.getString("Mechanics") != null ? rs.getString("Mechanics") : "N/A";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exerciseDetails;  // Return the array of strings
    }
	
	
}
