package pharmacie.util;

import pharmacie.dao.interfaces.DAOFactory;
import pharmacie.dao.interfaces.UtilisateurDAO;
import pharmacie.model.Utilisateur;
import pharmacie.service.AuthService;

import java.util.Optional;

public class UpdateAdminPassword {
    public static void main(String[] args) {
        System.out.println("Updating Admin Password...");
        try {
            AuthService auth = new AuthService();
            String newHash = auth.hashPassword("admin123");
            System.out.println("Computed Hash for 'admin123': " + newHash);

            UtilisateurDAO dao = DAOFactory.getFactory(DAOFactory.Type.MYSQL).getUtilisateurDAO();
            Optional<Utilisateur> opt = dao.findByEmail("admin@pharmacie.com");

            if (opt.isPresent()) {
                Utilisateur admin = opt.get();
                admin.setPasswordHash(newHash);
                dao.save(admin);
                System.out.println("Password updated successfully.");
            } else {
                System.out.println("Admin user not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
