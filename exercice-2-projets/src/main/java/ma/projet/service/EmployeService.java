package ma.projet.service;

import java.util.List;
import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.AbstractDao;

public class EmployeService extends AbstractDao<Employe> {
    public EmployeService() {
        super(Employe.class, "projets");
    }

    public List<Tache> findTachesRealisees(Long employeId) {
        return read(manager -> manager.createQuery(
                "select distinct t from Tache t join t.affectations a " +
                        "where a.employe.id = :id and a.dateDebutReelle is not null " +
                        "and a.dateFinReelle is not null order by t.nom", Tache.class)
                .setParameter("id", employeId).getResultList());
    }

    public List<Projet> findProjetsGeres(Long employeId) {
        return read(manager -> manager.createQuery(
                "select p from Projet p where p.chefProjet.id = :id order by p.nom", Projet.class)
                .setParameter("id", employeId).getResultList());
    }
}
