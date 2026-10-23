package ma.projet.beans;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("HOMME")
public class Homme extends Personne {
    @OneToMany(mappedBy = "homme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mariage> mariages = new ArrayList<>();

    protected Homme() {
    }

    public Homme(String nom, String prenom, String telephone, String adresse, LocalDate dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() { return List.copyOf(mariages); }
}
