package ma.projet.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;
import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.AbstractDao;

public class MariageService extends AbstractDao<Mariage> {
    public MariageService() {
        super(Mariage.class, "etat-civil");
    }

    public List<Homme> findHommesMariesAQuatreFemmesBetween(LocalDate debut, LocalDate fin) {
        if (debut == null || fin == null || fin.isBefore(debut)) {
            throw new IllegalArgumentException("The date range must be non-null and ordered");
        }
        return read(manager -> {
            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery<Homme> query = builder.createQuery(Homme.class);
            Root<Homme> homme = query.from(Homme.class);
            Join<Homme, Mariage> mariage = homme.join("mariages", JoinType.INNER);
            Join<Mariage, Femme> femme = mariage.join("femme", JoinType.INNER);
            query.select(homme).distinct(true)
                    .where(builder.and(
                            builder.lessThanOrEqualTo(mariage.get("dateDebut"), fin),
                            builder.or(builder.isNull(mariage.get("dateFin")),
                                    builder.greaterThanOrEqualTo(mariage.get("dateFin"), debut))))
                    .groupBy(homme)
                    .having(builder.equal(builder.countDistinct(femme), 4L));
            return manager.createQuery(query).getResultList();
        });
    }
}
