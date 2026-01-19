package pharmacie.model;

import java.math.BigDecimal;

/**
 * Represents a product in the pharmacy inventory.
 */
public class Produit {
    private Long id;
    private String nom;
    private String description;
    private BigDecimal prixAchat;
    private BigDecimal prixVente;
    private int stockActuel;
    private int seuilMin;
    private String codeBarre;

    public Produit() {
    }

    public Produit(Long id, String nom, BigDecimal prixAchat, BigDecimal prixVente, int stockActuel, int seuilMin,
            String codeBarre) {
        this.id = id;
        this.nom = nom;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
        this.stockActuel = stockActuel;
        this.seuilMin = seuilMin;
        this.codeBarre = codeBarre;
    }

    public boolean isStockLow() {
        return stockActuel <= seuilMin;
    }

    // Getters and Setters

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(BigDecimal prixAchat) {
        this.prixAchat = prixAchat;
    }

    public BigDecimal getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(BigDecimal prixVente) {
        this.prixVente = prixVente;
    }

    public int getStockActuel() {
        return stockActuel;
    }

    public void setStockActuel(int stockActuel) {
        this.stockActuel = stockActuel;
    }

    public int getSeuilMin() {
        return seuilMin;
    }

    public void setSeuilMin(int seuilMin) {
        this.seuilMin = seuilMin;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    @Override
    public String toString() {
        return nom + " (Stock: " + stockActuel + ")";
    }
}
