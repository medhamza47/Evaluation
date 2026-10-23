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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "taches")
@NamedQuery(name = Tache.FIND_ABOVE_1000, query = "select t from Tache t where t.prix > :prix order by t.nom")
public class Tache {
    public static final String FIND_ABOVE_1000 = "Tache.findAbove1000";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private BigDecimal prix;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Projet projet;
    @OneToMany(mappedBy = "tache")
    private List<EmployeTache> affectations = new ArrayList<>();

    protected Tache() {
    }

    public Tache(String nom, LocalDate dateDebut, LocalDate dateFin, BigDecimal prix) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
    }

    void setProjet(Projet projet) { this.projet = projet; }
    public Long getId() { return id; }
    public String getNom() { return nom; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public BigDecimal getPrix() { return prix; }
    public Projet getProjet() { return projet; }
    public List<EmployeTache> getAffectations() { return List.copyOf(affectations); }
    public void setNom(String nom) { this.nom = nom; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }
}
