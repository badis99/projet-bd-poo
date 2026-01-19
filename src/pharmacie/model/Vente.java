package pharmacie.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sale transaction with a client.
 */
public class Vente {
    private Long id;
    private LocalDateTime dateVente;
    private Client client;
    private Utilisateur utilisateur; // The employee/admin who made the sale
    private BigDecimal total;
    private List<LigneVente> lignes;

    public Vente() {
        this.lignes = new ArrayList<>();
        this.total = BigDecimal.ZERO;
        this.dateVente = LocalDateTime.now();
    }

    public Vente(Long id, LocalDateTime dateVente, Client client, Utilisateur utilisateur) {
        this.id = id;
        this.dateVente = dateVente;
        this.client = client;
        this.utilisateur = utilisateur;
        this.lignes = new ArrayList<>();
        this.total = BigDecimal.ZERO;
    }

    public void ajouterLigne(LigneVente ligne) {
        this.lignes.add(ligne);
        this.calculerTotal();
    }

    public void calculerTotal() {
        this.total = BigDecimal.ZERO;
        for (LigneVente ligne : lignes) {
            if (ligne.getSousTotal() != null) {
                this.total = this.total.add(ligne.getSousTotal());
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateVente() {
        return dateVente;
    }

    public void setDateVente(LocalDateTime dateVente) {
        this.dateVente = dateVente;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<LigneVente> getLignes() {
        return lignes;
    }

    public void setLignes(List<LigneVente> lignes) {
        this.lignes = lignes;
        this.calculerTotal();
    }
}
