package ma.projet.classes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String libelle;
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produit> produits = new ArrayList<>();

    protected Categorie() {
    }

    public Categorie(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public void addProduit(Produit produit) {
        produits.add(produit);
        produit.setCategorie(this);
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getLibelle() { return libelle; }
    public List<Produit> getProduits() { return List.copyOf(produits); }
    public void setCode(String code) { this.code = code; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
}
