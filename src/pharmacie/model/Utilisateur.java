package pharmacie.model;

import java.time.LocalDateTime;

/**
 * Represents a system user (Admin or Employee).
 */
public class Utilisateur extends Personne {
    private String passwordHash;
    private Role role;
    private LocalDateTime dateCreation;

    public Utilisateur() {
    }

    public Utilisateur(Long id, String nom, String prenom, String email, String passwordHash, Role role) {
        super(id, nom, prenom, email);
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}
