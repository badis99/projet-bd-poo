package pharmacie.test;

import pharmacie.config.DBConnection;
import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        System.out.println("Testing Database Connection...");
        try (Connection conn = DBConnection.getInstance().getConnection()) {
            if (conn != null) {
                System.out.println("Connection SUCCESSFUL!");
            } else {
                System.out.println("Connection Failed (Returned null).");
            }
        } catch (Exception e) {
            System.out.println("Connection FAILED: " + e.getMessage());
            e.printStackTrace();
            try (java.io.PrintWriter pw = new java.io.PrintWriter("error.log")) {
                e.printStackTrace(pw);
            } catch (java.io.FileNotFoundException ex) {
            }
        }
    }
}
