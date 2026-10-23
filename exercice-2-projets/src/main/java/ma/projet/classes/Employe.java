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
@Table(name = "employes")
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    @OneToMany(mappedBy = "chefProjet")
    private List<Projet> projetsGeres = new ArrayList<>();
    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeTache> affectations = new ArrayList<>();

    protected Employe() {
    }

    public Employe(String nom, String prenom, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    public void addProjetGere(Projet projet) {
        projetsGeres.add(projet);
        projet.setChefProjet(this);
    }

    public void affecter(Tache tache, java.time.LocalDate debutReel, java.time.LocalDate finReelle) {
        affectations.add(new EmployeTache(this, tache, debutReel, finReelle));
    }

    public Long getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getTelephone() { return telephone; }
    public List<Projet> getProjetsGeres() { return List.copyOf(projetsGeres); }
    public List<EmployeTache> getAffectations() { return List.copyOf(affectations); }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
}
