package pharmacie.dao.mysql;

import pharmacie.dao.interfaces.*;

public class MySQLDAOFactory extends DAOFactory {
    @Override
    public UtilisateurDAO getUtilisateurDAO() {
        return new MySQLUtilisateurDAO();
    }

    @Override
    public ProduitDAO getProduitDAO() {
        return new MySQLProduitDAO();
    }

    @Override
    public FournisseurDAO getFournisseurDAO() {
        return new MySQLFournisseurDAO();
    }

    @Override
    public ClientDAO getClientDAO() {
        return new MySQLClientDAO();
    }

    @Override
    public CommandeDAO getCommandeDAO() {
        return new MySQLCommandeDAO();
    }

    @Override
    public VenteDAO getVenteDAO() {
        return new MySQLVenteDAO();
    }
}
