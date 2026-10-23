package ma.projet.service;

import java.util.List;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.AbstractDao;

public class CommandeService extends AbstractDao<Commande> {
    public CommandeService() {
        super(Commande.class, "stock");
    }

    public List<LigneCommandeProduit> findProductsInOrder(Long commandeId) {
        return read(manager -> manager.createQuery(
                "select ligne from LigneCommandeProduit ligne join fetch ligne.produit where ligne.commande.id = :id order by ligne.id",
                LigneCommandeProduit.class)
                .setParameter("id", commandeId)
                .getResultList());
    }
}
