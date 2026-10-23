package ma.projet.classes;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produits")
@NamedQuery(name = Produit.FIND_ABOVE_100, query = "select p from Produit p where p.prix > :prix order by p.reference")
public class Produit {
    public static final String FIND_ABOVE_100 = "Produit.findAbove100";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private BigDecimal prix;
    @ManyToOne(fetch = FetchType.LAZY)
    private Categorie categorie;
    @OneToMany(mappedBy = "produit")
    private List<LigneCommandeProduit> lignesCommande = new ArrayList<>();

    protected Produit() {
    }

    public Produit(String reference, BigDecimal prix) {
        this.reference = reference;
        this.prix = prix;
    }

    public Long getId() { return id; }
    public String getReference() { return reference; }
    public BigDecimal getPrix() { return prix; }
    public Categorie getCategorie() { return categorie; }
    public List<LigneCommandeProduit> getLignesCommande() { return List.copyOf(lignesCommande); }
    public void setReference(String reference) { this.reference = reference; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }
    void setCategorie(Categorie categorie) { this.categorie = categorie; }
}
