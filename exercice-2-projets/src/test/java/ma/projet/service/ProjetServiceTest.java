package ma.projet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ProjetServiceTest {
    @BeforeAll
    static void configureDatabase() {
        System.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        System.setProperty("hibernate.connection.url", "jdbc:h2:mem:projets;DB_CLOSE_DELAY=-1");
        System.setProperty("hibernate.connection.username", "sa");
        System.setProperty("hibernate.connection.password", "");
        System.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        System.setProperty("hibernate.hbm2ddl.auto", "create-drop");
    }

    @AfterAll
    static void closeDatabase() {
        HibernateUtil.shutdown("projets");
        System.clearProperty("hibernate.connection.driver_class");
        System.clearProperty("hibernate.connection.url");
        System.clearProperty("hibernate.connection.username");
        System.clearProperty("hibernate.connection.password");
        System.clearProperty("hibernate.dialect");
        System.clearProperty("hibernate.hbm2ddl.auto");
    }

    @Test
    void tracksProjectLeadershipAssignmentsAndRealizedDates() {
        EmployeService employees = new EmployeService();
        ProjetService projects = new ProjetService();
        TacheService tasks = new TacheService();

        Employe employee = employees.create(new Employe("SAFI", "Said", "0600000000"));
        Projet project = new Projet("Gestion de stock", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31));
        employee.addProjetGere(project);
        Tache analysis = new Tache("Analyse", LocalDate.of(2025, 2, 1), LocalDate.of(2025, 2, 28),
                new BigDecimal("1500"));
        Tache design = new Tache("Conception", LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 31),
                new BigDecimal("900"));
        project.addTache(analysis);
        project.addTache(design);
        projects.create(project);
        employee.affecter(analysis, LocalDate.of(2025, 2, 3), LocalDate.of(2025, 2, 20));
        employees.update(employee);

        assertEquals(List.of("Gestion de stock"), employees.findProjetsGeres(employee.getId())
                .stream().map(Projet::getNom).toList());
        assertEquals(List.of("Analyse"), employees.findTachesRealisees(employee.getId())
                .stream().map(Tache::getNom).toList());
        assertEquals(2, projects.findTachesPlanifiees(project.getId()).size());
        assertEquals(List.of("Analyse"), projects.findTachesRealisees(project.getId())
                .stream().map(Tache::getNom).toList());
        assertEquals(List.of("Analyse"), tasks.findAbovePrice(new BigDecimal("1000"))
                .stream().map(Tache::getNom).toList());
        assertEquals(List.of("Analyse"), tasks.findRealiseesBetween(
                LocalDate.of(2025, 2, 10), LocalDate.of(2025, 2, 15))
                .stream().map(Tache::getNom).toList());
    }
}
