package ma.projet.service;

import java.time.LocalDate;
import java.util.List;
import ma.projet.classes.Tache;
import ma.projet.dao.AbstractDao;

public class TacheService extends AbstractDao<Tache> {
    public TacheService() {
        super(Tache.class, "projets");
    }

    public List<Tache> findAbovePrice(java.math.BigDecimal prix) {
        return read(manager -> manager.createNamedQuery(Tache.FIND_ABOVE_1000, Tache.class)
                .setParameter("prix", prix).getResultList());
    }

    public List<Tache> findRealiseesBetween(LocalDate debut, LocalDate fin) {
        if (debut == null || fin == null || fin.isBefore(debut)) {
            throw new IllegalArgumentException("The date range must be non-null and ordered");
        }
        return read(manager -> manager.createQuery(
                "select distinct t from Tache t join t.affectations a " +
                        "where a.dateDebutReelle <= :fin and a.dateFinReelle >= :debut order by t.nom", Tache.class)
                .setParameter("debut", debut).setParameter("fin", fin).getResultList());
    }
}
