package ma.projet.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import ma.projet.classes.Produit;
import ma.projet.dao.AbstractDao;

public class ProduitService extends AbstractDao<Produit> {
    public ProduitService() {
        super(Produit.class, "stock");
    }

    public List<Produit> findByCategorie(Long categorieId) {
        return read(manager -> manager.createQuery(
                "select p from Produit p where p.categorie.id = :id order by p.reference", Produit.class)
                .setParameter("id", categorieId).getResultList());
    }

    public List<Produit> findOrderedBetween(LocalDate debut, LocalDate fin) {
        validateRange(debut, fin);
        return read(manager -> manager.createQuery(
                "select distinct p from Produit p join p.lignesCommande ligne join ligne.commande c " +
                        "where c.date between :debut and :fin order by p.reference", Produit.class)
                .setParameter("debut", debut).setParameter("fin", fin).getResultList());
    }

    public List<Produit> findAbovePrice(BigDecimal prix) {
        return read(manager -> manager.createNamedQuery(Produit.FIND_ABOVE_100, Produit.class)
                .setParameter("prix", prix).getResultList());
    }

    private static void validateRange(LocalDate debut, LocalDate fin) {
        if (debut == null || fin == null || fin.isBefore(debut)) {
            throw new IllegalArgumentException("The date range must be non-null and ordered");
        }
    }
}
