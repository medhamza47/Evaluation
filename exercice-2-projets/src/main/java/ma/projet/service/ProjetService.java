package ma.projet.service;

import java.time.LocalDate;
import java.util.List;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.AbstractDao;

public class ProjetService extends AbstractDao<Projet> {
    public ProjetService() {
        super(Projet.class, "projets");
    }

    public List<Tache> findTachesPlanifiees(Long projetId) {
        return read(manager -> manager.createQuery(
                "select t from Tache t where t.projet.id = :id order by t.dateDebut", Tache.class)
                .setParameter("id", projetId).getResultList());
    }

    public List<Tache> findTachesRealisees(Long projetId) {
        return read(manager -> manager.createQuery(
                "select distinct t from Tache t join t.affectations a " +
                        "where t.projet.id = :id and a.dateDebutReelle is not null and a.dateFinReelle is not null " +
                        "order by t.dateDebut", Tache.class)
                .setParameter("id", projetId).getResultList());
    }

    public List<Tache> findTachesRealisees(Long projetId, LocalDate debut, LocalDate fin) {
        validateRange(debut, fin);
        return read(manager -> manager.createQuery(
                "select distinct t from Tache t join t.affectations a " +
                        "where t.projet.id = :id and a.dateDebutReelle <= :fin and a.dateFinReelle >= :debut " +
                        "order by t.dateDebut", Tache.class)
                .setParameter("id", projetId).setParameter("debut", debut).setParameter("fin", fin)
                .getResultList());
    }

    private static void validateRange(LocalDate debut, LocalDate fin) {
        if (debut == null || fin == null || fin.isBefore(debut)) {
            throw new IllegalArgumentException("The date range must be non-null and ordered");
        }
    }
}
