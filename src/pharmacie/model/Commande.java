package pharmacie.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a supply order from a supplier.
 */
public class Commande {
    private Long id;
    private Fournisseur fournisseur;
    private LocalDateTime dateCreation;
    private StatutCommande statut;
    private List<LigneCommande> lignes;

    public Commande() {
        this.lignes = new ArrayList<>();
        this.statut = StatutCommande.EN_ATTENTE;
        this.dateCreation = LocalDateTime.now();
    }

    public Commande(Long id, Fournisseur fournisseur, LocalDateTime dateCreation, StatutCommande statut) {
        this.id = id;
        this.fournisseur = fournisseur;
        this.dateCreation = dateCreation;
        this.statut = statut;
        this.lignes = new ArrayList<>();
    }

    public void ajouterLigne(LigneCommande ligne) {
        this.lignes.add(ligne);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public StatutCommande getStatut() {
        return statut;
    }

    public void setStatut(StatutCommande statut) {
        this.statut = statut;
    }

    public List<LigneCommande> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneCommande> lignes) {
        this.lignes = lignes;
    }

    public java.math.BigDecimal getTotalMontant() {
        java.math.BigDecimal total = java.math.BigDecimal.ZERO;
        for (LigneCommande l : lignes) {
            if (l.getProduit() != null && l.getProduit().getPrixAchat() != null) {
                java.math.BigDecimal lineTotal = l.getProduit().getPrixAchat()
                        .multiply(new java.math.BigDecimal(l.getQuantite()));
                total = total.add(lineTotal);
            }
        }
        return total;
    }
}
