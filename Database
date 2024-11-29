package Final_project_codes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/fitness_tracker"; // Replace with your database URL
        String username = "root"; // Replace with your MySQL username
        String password = "???"; // Replace with your MySQL password

        // Establishing connection
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connection to MySQL database successful!");

            // Creating a Statement
            Statement statement = connection.createStatement();

            // Example query: Retrieve data from the Exercises table
            String query = "SELECT * FROM Exercises";
            ResultSet resultSet = statement.executeQuery(query);

            // Displaying the data
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String difficultyLevel = resultSet.getString("difficulty_level");
                String targetMuscleGroup = resultSet.getString("target_muscle_group");
                String primeMoverMuscle = resultSet.getString("prime_mover_muscle");
                String secondaryMuscle = resultSet.getString("secondary_muscle");
                String tertiaryMuscle = resultSet.getString("tertiary_muscle");
                String primaryEquipment = resultSet.getString("primary_equipment");

                System.out.println("ID: " + id + ", Exercise: " + name + ", Difficulty: " + difficultyLevel +
                                   ", Target Muscle: " + targetMuscleGroup + ", Primary Equipment: " + primaryEquipment);
            }

        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}


