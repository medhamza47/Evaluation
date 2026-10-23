package ma.projet.beans;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "mariages")
public class Mariage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "homme_id", nullable = false)
    private Homme homme;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "femme_id", nullable = false)
    private Femme femme;
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;
    @Column(name = "date_fin")
    private LocalDate dateFin;
    @Column(name = "nbr_enfant", nullable = false)
    private int nbrEnfant;

    protected Mariage() {
    }

    public Mariage(Homme homme, Femme femme, LocalDate dateDebut, LocalDate dateFin, int nbrEnfant) {
        if (nbrEnfant < 0) {
            throw new IllegalArgumentException("The number of children cannot be negative");
        }
        this.homme = homme;
        this.femme = femme;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrEnfant = nbrEnfant;
    }

    public Long getId() { return id; }
    public Homme getHomme() { return homme; }
    public Femme getFemme() { return femme; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public int getNbrEnfant() { return nbrEnfant; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public void setNbrEnfant(int nbrEnfant) {
        if (nbrEnfant < 0) {
            throw new IllegalArgumentException("The number of children cannot be negative");
        }
        this.nbrEnfant = nbrEnfant;
    }
}
