package ma.projet.service;

import java.time.LocalDate;
import java.util.List;
import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.AbstractDao;

public class HommeService extends AbstractDao<Homme> {
    public HommeService() {
        super(Homme.class, "etat-civil");
    }

    public List<Femme> findEpousesBetween(Long hommeId, LocalDate debut, LocalDate fin) {
        validateRange(debut, fin);
        return read(manager -> manager.createQuery(
                "select distinct m.femme from Mariage m where m.homme.id = :id " +
                        "and m.dateDebut <= :fin and (m.dateFin is null or m.dateFin >= :debut) " +
                        "order by m.femme.nom", Femme.class)
                .setParameter("id", hommeId).setParameter("debut", debut).setParameter("fin", fin)
                .getResultList());
    }

    public List<Mariage> findMariagesAvecDetails(Long hommeId) {
        return read(manager -> manager.createQuery(
                "select m from Mariage m join fetch m.femme where m.homme.id = :id order by m.dateDebut",
                Mariage.class).setParameter("id", hommeId).getResultList());
    }

    private static void validateRange(LocalDate debut, LocalDate fin) {
        if (debut == null || fin == null || fin.isBefore(debut)) {
            throw new IllegalArgumentException("The date range must be non-null and ordered");
        }
    }
}
