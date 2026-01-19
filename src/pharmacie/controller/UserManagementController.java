package pharmacie.controller;

import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import pharmacie.dao.interfaces.DAOFactory;
import pharmacie.dao.interfaces.UtilisateurDAO;
import pharmacie.model.Utilisateur;
import pharmacie.service.AuthService;
import pharmacie.view.UserManagementView;

import java.time.LocalDateTime;
import java.util.List;

public class UserManagementController {
    private UserManagementView view;
    private UtilisateurDAO utilisateurDAO;
    private AuthService authService;

    public UserManagementController(UserManagementView view) {
        this.view = view;
        this.utilisateurDAO = DAOFactory.getFactory(DAOFactory.Type.MYSQL).getUtilisateurDAO();
        this.authService = new AuthService();
    }

    public void loadData(TableView<Utilisateur> table) {
        List<Utilisateur> users = utilisateurDAO.findAll();
        table.setItems(FXCollections.observableArrayList(users));
    }

    public void addUser(Utilisateur u) {
        // Hash password before saving
        String hash = authService.hashPassword(u.getPasswordHash());
        u.setPasswordHash(hash);
        u.setDateCreation(LocalDateTime.now());

        utilisateurDAO.save(u);
        loadData(view.getTable());
    }

    public void deleteUser(Utilisateur u) {
        if (u != null) {
            utilisateurDAO.delete(u.getId());
            loadData(view.getTable());
        }
    }
}
