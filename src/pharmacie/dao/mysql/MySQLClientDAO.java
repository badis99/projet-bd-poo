package pharmacie.dao.mysql;

import pharmacie.dao.interfaces.ClientDAO;
import pharmacie.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLClientDAO extends AbstractMySQLDAO implements ClientDAO {

    private Client mapResultSet(ResultSet rs) throws SQLException {
        Client c = new Client();
        c.setId(rs.getLong("id"));
        c.setNom(rs.getString("nom"));
        c.setPrenom(rs.getString("prenom"));
        c.setEmail(rs.getString("email"));
        c.setTelephone(rs.getString("telephone"));
        c.setCarteVitale(rs.getString("carte_vitale"));
        return c;
    }

    @Override
    public Optional<Client> findById(Long id) {
        String sql = "SELECT * FROM client WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    return Optional.of(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        List<Client> list = new ArrayList<>();
        String sql = "SELECT * FROM client";
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(Client entity) {
        if (entity.getId() == null) {
            String sql = "INSERT INTO client (nom, prenom, email, telephone, carte_vitale) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, entity.getNom());
                stmt.setString(2, entity.getPrenom());
                stmt.setString(3, entity.getEmail());
                stmt.setString(4, entity.getTelephone());
                stmt.setString(5, entity.getCarteVitale());
                stmt.executeUpdate();
                try (ResultSet gk = stmt.getGeneratedKeys()) {
                    if (gk.next())
                        entity.setId(gk.getLong(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String sql = "UPDATE client SET nom=?, prenom=?, email=?, telephone=?, carte_vitale=? WHERE id=?";
            try (Connection conn = getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, entity.getNom());
                stmt.setString(2, entity.getPrenom());
                stmt.setString(3, entity.getEmail());
                stmt.setString(4, entity.getTelephone());
                stmt.setString(5, entity.getCarteVitale());
                stmt.setLong(6, entity.getId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM client WHERE id = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Client> findByCarteVitale(String carteVitale) {
        String sql = "SELECT * FROM client WHERE carte_vitale = ?";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, carteVitale);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next())
                    return Optional.of(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
