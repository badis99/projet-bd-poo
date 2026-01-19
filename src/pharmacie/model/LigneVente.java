package pharmacie.model;

import java.math.BigDecimal;

/**
 * Represents a line item in a sale.
 */
public class LigneVente {
    private Long id;
    private Produit produit;
    private int quantite;
    private BigDecimal prixUnitaire;
    private BigDecimal sousTotal;

    public LigneVente() {
    }

    public LigneVente(Produit produit, int quantite, BigDecimal prixUnitaire) {
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.updateSousTotal();
    }

    public void updateSousTotal() {
        if (prixUnitaire != null) {
            this.sousTotal = prixUnitaire.multiply(new BigDecimal(quantite));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
        this.updateSousTotal();
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
        this.updateSousTotal();
    }

    public BigDecimal getSousTotal() {
        return sousTotal;
    }

    public void setSousTotal(BigDecimal sousTotal) {
        this.sousTotal = sousTotal;
    }
}
