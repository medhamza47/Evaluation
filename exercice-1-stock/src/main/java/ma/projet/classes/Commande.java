package ma.projet.classes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommandeProduit> lignes = new ArrayList<>();

    protected Commande() {
    }

    public Commande(LocalDate date) {
        this.date = date;
    }

    public void addLigne(Produit produit, int quantite) {
        lignes.add(new LigneCommandeProduit(this, produit, quantite));
    }

    public Long getId() { return id; }
    public LocalDate getDate() { return date; }
    public List<LigneCommandeProduit> getLignes() { return List.copyOf(lignes); }
    public void setDate(LocalDate date) { this.date = date; }
}
