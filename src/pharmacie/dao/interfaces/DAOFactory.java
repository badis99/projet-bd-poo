package pharmacie.dao.interfaces;

public abstract class DAOFactory {
    // Enum for factory types
    public enum Type {
        MYSQL, XML, MEMORY
    }

    public abstract UtilisateurDAO getUtilisateurDAO();

    public abstract ProduitDAO getProduitDAO();

    public abstract FournisseurDAO getFournisseurDAO();

    public abstract ClientDAO getClientDAO();

    public abstract CommandeDAO getCommandeDAO();

    public abstract VenteDAO getVenteDAO();

    public static DAOFactory getFactory(Type type) {
        if (type == Type.MYSQL) {
            return new pharmacie.dao.mysql.MySQLDAOFactory();
        }
        return null; // Handle other types
    }
}
