import java.sql.*;

public class Batch {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:test.db";
        String table = "COMPANY";
        int records = 10; // Number of records to insert

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {

            // Enable auto-commit for simplicity (unless bulk operations are needed)
            conn.setAutoCommit(true);

            // Prepare batch insert operations
            String sql = "INSERT INTO " + table + " (ID, NAME, AGE, ADDRESS, SALARY) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (int i = 0; i < records; i++) {
                    pstmt.setInt(1, i + 5); // ID starting from 5
                    pstmt.setString(2, "name_" + i);
                    pstmt.setInt(3, 32);
                    pstmt.setString(4, "California");
                    pstmt.setDouble(5, 20000.00);
                    pstmt.addBatch();
                }
                int[] results = pstmt.executeBatch();

                // Print batch execution results
                for (int result : results) {
                    System.out.print(result + " ");
                }
                System.out.println();
            }

            // Query and display data
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
            while (rs.next()) {
                System.out.printf("ID = %d, NAME = %s, AGE = %d, ADDRESS = %s, SALARY = %.2f%n",
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getInt("AGE"),
                        rs.getString("ADDRESS"),
                        rs.getDouble("SALARY"));
            }
        } catch (SQLException e) {
            System.err.println("Database error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Operation done successfully");
    }
}