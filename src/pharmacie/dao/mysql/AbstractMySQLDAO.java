package pharmacie.dao.mysql;

import pharmacie.config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractMySQLDAO {
    protected Connection getConnection() throws SQLException {
        return DBConnection.getInstance().getConnection();
    }
}
