package ma.projet.beans;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "personnes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_personne")
public abstract class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    protected Personne() {
    }

    protected Personne(String nom, String prenom, String telephone, String adresse, LocalDate dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
    }

    public Long getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getTelephone() { return telephone; }
    public String getAdresse() { return adresse; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
}
