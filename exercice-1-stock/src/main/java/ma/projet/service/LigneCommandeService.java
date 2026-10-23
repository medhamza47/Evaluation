package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.AbstractDao;

public class LigneCommandeService extends AbstractDao<LigneCommandeProduit> {
    public LigneCommandeService() {
        super(LigneCommandeProduit.class, "stock");
    }
}
