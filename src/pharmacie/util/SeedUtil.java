package pharmacie.util;

import pharmacie.config.DBConnection;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class SeedUtil {
    public static void main(String[] args) {
        System.out.println("Seeding Database...");
        try (Connection conn = DBConnection.getInstance().getConnection();
                Statement stmt = conn.createStatement()) {

            // Enable Foreign Keys
            stmt.execute("SET foreign_key_checks = 1");

            // Check if admin exists
            boolean adminExists = false;
            try (java.sql.ResultSet rs = stmt
                    .executeQuery("SELECT count(*) FROM utilisateur WHERE email='admin@pharmacie.com'")) {
                if (rs.next() && rs.getInt(1) > 0)
                    adminExists = true;
            }

            if (!adminExists) {
                System.out.println("Inserting Admin User...");
                String sqlUser = "INSERT INTO utilisateur (nom, prenom, email, password_hash, role) VALUES " +
                        "('Admin', 'System', 'admin@pharmacie.com', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'ADMIN')";
                stmt.executeUpdate(sqlUser);
                System.out.println("Admin User Inserted.");
            } else {
                System.out.println("Admin User already exists.");
            }

            // Simple check for other tables...
            // For now, let's just make sure the admin user is there since that's the
            // blocker.

            System.out.println("Seeding Complete.");

        } catch (SQLException e) {
            e.printStackTrace();
            try (java.io.PrintWriter pw = new java.io.PrintWriter("seed_error.log")) {
                e.printStackTrace(pw);
                pw.println("SQL State: " + e.getSQLState());
                pw.println("Error Code: " + e.getErrorCode());
                pw.println("Message: " + e.getMessage());
            } catch (java.io.FileNotFoundException ex) {
            }
        }
    }
}
