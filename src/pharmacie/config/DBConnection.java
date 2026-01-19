package pharmacie.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton class to manage database connections.
 * Reads configuration from db.properties.
 */
public class DBConnection {
    private static DBConnection instance;
    private String url;
    private String username;
    private String password;

    private DBConnection() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("pharmacie/config/db.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                // Fallback or error if using file system path structures differently in IDE vs
                // CLI
                // For this environment, let's try to load from a known location if classpath
                // fails or just handle classpath correctly.
                // Assuming src/pharmacie/config is in classpath after compilation?
                // Actually, usually resources are copied. I'll stick to a simple classpath
                // resource load.
                // If that fails, hardcode defaults for simplicity in this specific environment
                // or use absolute path?
                // Let's try standard approach. If it fails, I'll fix.
                System.out.println("Warning: db.properties not found in classpath, using defaults.");
                this.url = "jdbc:mysql://localhost:3306/pharmacie_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
                this.username = "pharmacie_user";
                this.password = "pharmacie_pass";
                return;
            }
            prop.load(input);
            this.url = prop.getProperty("db.url");
            this.username = prop.getProperty("db.user");
            this.password = prop.getProperty("db.password");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error loading database properties", ex);
        }
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
