package ma.projet.beans;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("FEMME")
@NamedQuery(name = Femme.MARRIED_AT_LEAST_TWICE,
        query = "select f from Femme f where (select count(m) from Mariage m where m.femme = f) >= 2 order by f.nom")
@NamedNativeQuery(name = Femme.CHILDREN_BETWEEN,
        query = "select coalesce(sum(m.nbr_enfant), 0) from mariages m " +
                "where m.femme_id = ?1 and m.date_debut <= ?3 and (m.date_fin is null or m.date_fin >= ?2)")
public class Femme extends Personne {
    public static final String MARRIED_AT_LEAST_TWICE = "Femme.marriedAtLeastTwice";
    public static final String CHILDREN_BETWEEN = "Femme.childrenBetween";

    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mariage> mariages = new ArrayList<>();

    protected Femme() {
    }

    public Femme(String nom, String prenom, String telephone, String adresse, LocalDate dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() { return List.copyOf(mariages); }
}
