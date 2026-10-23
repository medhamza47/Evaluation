package ma.projet.classes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projets")
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    @ManyToOne(fetch = FetchType.LAZY)
    private Employe chefProjet;
    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tache> taches = new ArrayList<>();

    protected Projet() {
    }

    public Projet(String nom, LocalDate dateDebut, LocalDate dateFin) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public void addTache(Tache tache) {
        taches.add(tache);
        tache.setProjet(this);
    }

    void setChefProjet(Employe chefProjet) { this.chefProjet = chefProjet; }
    public Long getId() { return id; }
    public String getNom() { return nom; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public Employe getChefProjet() { return chefProjet; }
    public List<Tache> getTaches() { return List.copyOf(taches); }
    public void setNom(String nom) { this.nom = nom; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
}
