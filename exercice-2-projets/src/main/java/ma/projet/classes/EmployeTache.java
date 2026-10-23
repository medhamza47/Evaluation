package ma.projet.classes;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "employe_taches")
public class EmployeTache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Employe employe;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Tache tache;
    private LocalDate dateDebutReelle;
    private LocalDate dateFinReelle;

    protected EmployeTache() {
    }

    EmployeTache(Employe employe, Tache tache, LocalDate dateDebutReelle, LocalDate dateFinReelle) {
        this.employe = employe;
        this.tache = tache;
        this.dateDebutReelle = dateDebutReelle;
        this.dateFinReelle = dateFinReelle;
    }

    public Long getId() { return id; }
    public Employe getEmploye() { return employe; }
    public Tache getTache() { return tache; }
    public LocalDate getDateDebutReelle() { return dateDebutReelle; }
    public LocalDate getDateFinReelle() { return dateFinReelle; }
    public void setDateDebutReelle(LocalDate dateDebutReelle) { this.dateDebutReelle = dateDebutReelle; }
    public void setDateFinReelle(LocalDate dateFinReelle) { this.dateFinReelle = dateFinReelle; }
}
