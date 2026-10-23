package ma.projet.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import ma.projet.beans.Femme;
import ma.projet.dao.AbstractDao;

public class FemmeService extends AbstractDao<Femme> {
    public FemmeService() {
        super(Femme.class, "etat-civil");
    }

    public Optional<Femme> findPlusAgee() {
        return read(manager -> manager.createQuery(
                "select f from Femme f order by f.dateNaissance asc", Femme.class)
                .setMaxResults(1).getResultList().stream().findFirst());
    }

    public long countChildrenBetween(Long femmeId, LocalDate debut, LocalDate fin) {
        if (femmeId == null || debut == null || fin == null || fin.isBefore(debut)) {
            throw new IllegalArgumentException("A woman and a valid date range are required");
        }
        return read(manager -> ((Number) manager.createNamedQuery(Femme.CHILDREN_BETWEEN)
                .setParameter(1, femmeId).setParameter(2, debut).setParameter(3, fin)
                .getSingleResult()).longValue());
    }

    public List<Femme> findMarieesAuMoinsDeuxFois() {
        return read(manager -> manager.createNamedQuery(Femme.MARRIED_AT_LEAST_TWICE, Femme.class)
                .getResultList());
    }
}
