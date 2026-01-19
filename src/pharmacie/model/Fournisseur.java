package pharmacie.model;

/**
 * Represents a supplier.
 */
public class Fournisseur {
    private Long id;
    private String nom;
    private String adresse;
    private String telephone;
    private String email;
    private int notePerformance; // 0 to 100

    public Fournisseur() {
    }

    public Fournisseur(Long id, String nom, String telephone, String email) {
        this.id = id;
        this.nom = nom;
        this.telephone = telephone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNotePerformance() {
        return notePerformance;
    }

    public void setNotePerformance(int notePerformance) {
        this.notePerformance = notePerformance;
    }

    @Override
    public String toString() {
        return nom;
    }
}
