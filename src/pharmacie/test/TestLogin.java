package pharmacie.test;

import pharmacie.dao.interfaces.DAOFactory;
import pharmacie.dao.interfaces.UtilisateurDAO;
import pharmacie.model.Utilisateur;
import pharmacie.service.AuthService;

import java.util.List;
import java.util.Optional;

public class TestLogin {
    public static void main(String[] args) {
        System.out.println("=== DIAGNOSTIC LOGIN SCRIPT ===");

        try {
            UtilisateurDAO dao = DAOFactory.getFactory(DAOFactory.Type.MYSQL).getUtilisateurDAO();

            // 1. List all users
            System.out.println("\n--- Listing All Users in DB ---");
            List<Utilisateur> users = dao.findAll();
            System.out.println("Total users found: " + users.size());
            for (Utilisateur u : users) {
                System.out.println("ID: " + u.getId() + " | Email: " + u.getEmail() + " | Role: " + u.getRole());
            }

            // 2. Try specific lookup
            String targetEmail = "admin@pharmacie.com";
            System.out.println("\n--- Looking up " + targetEmail + " ---");
            Optional<Utilisateur> opt = dao.findByEmail(targetEmail);
            if (opt.isPresent()) {
                System.out.println("User FOUND: " + opt.get().getNom());
                System.out.println("Hash in DB: " + opt.get().getPasswordHash());

                // 3. Check password hash matches
                AuthService auth = new AuthService();
                String inputPass = "admin123";
                String computedHash = auth.hashPassword(inputPass);
                System.out.println("Computed Hash for 'admin123': " + computedHash);

                if (computedHash.equals(opt.get().getPasswordHash())) {
                    System.out.println("Hashes MATCH.");
                } else {
                    System.out.println("Hashes DO NOT MATCH.");
                }
            } else {
                System.out.println("User NOT FOUND by findByEmail.");
            }

        } catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
