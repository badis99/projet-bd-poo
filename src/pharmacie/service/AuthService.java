package pharmacie.service;

import pharmacie.dao.interfaces.DAOFactory;
import pharmacie.dao.interfaces.UtilisateurDAO;
import pharmacie.exception.AuthentificationException;
import pharmacie.model.Utilisateur;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * Service handling authentication and security.
 */
public class AuthService {
    private UtilisateurDAO utilisateurDAO;

    public AuthService() {
        this.utilisateurDAO = DAOFactory.getFactory(DAOFactory.Type.MYSQL).getUtilisateurDAO();
    }

    public Utilisateur login(String email, String password) throws AuthentificationException {
        Optional<Utilisateur> optUser = utilisateurDAO.findByEmail(email);
        if (optUser.isEmpty()) {
            throw new AuthentificationException("Utilisateur introuvable.");
        }

        Utilisateur user = optUser.get();
        // hashPassword() implementation below
        String hashedInput = hashPassword(password);

        // In a real app, use constant-time comparison
        if (!hashedInput.equals(user.getPasswordHash())) {
            throw new AuthentificationException("Mot de passe incorrect.");
        }

        return user;
    }

    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur de hashage", e);
        }
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
