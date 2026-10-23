package ma.projet.classes;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "lignes_commande_produit")
public class LigneCommandeProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Commande commande;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Produit produit;
    private int quantite;

    protected LigneCommandeProduit() {
    }

    LigneCommandeProduit(Commande commande, Produit produit, int quantite) {
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
    }

    public Long getId() { return id; }
    public Commande getCommande() { return commande; }
    public Produit getProduit() { return produit; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
}
