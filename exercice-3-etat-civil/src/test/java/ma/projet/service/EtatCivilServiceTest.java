package ma.projet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.util.HibernateUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class EtatCivilServiceTest {
    @BeforeAll
    static void configureDatabase() {
        System.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        System.setProperty("hibernate.connection.url", "jdbc:h2:mem:etatcivil;DB_CLOSE_DELAY=-1");
        System.setProperty("hibernate.connection.username", "sa");
        System.setProperty("hibernate.connection.password", "");
        System.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        System.setProperty("hibernate.hbm2ddl.auto", "create-drop");
    }

    @AfterAll
    static void closeDatabase() {
        HibernateUtil.shutdown("etat-civil");
        System.clearProperty("hibernate.connection.driver_class");
        System.clearProperty("hibernate.connection.url");
        System.clearProperty("hibernate.connection.username");
        System.clearProperty("hibernate.connection.password");
        System.clearProperty("hibernate.dialect");
        System.clearProperty("hibernate.hbm2ddl.auto");
    }

    @Test
    void supportsCivilRegistryQueriesAndSeedsTenWomenAndFiveMen() {
        FemmeService femmes = new FemmeService();
        HommeService hommes = new HommeService();
        MariageService mariages = new MariageService();
        List<Femme> women = new ArrayList<>();
        List<Homme> men = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            women.add(femmes.create(new Femme("Femme" + i, "Prenom" + i, "060000000" + i,
                    "Adresse", LocalDate.of(1980 + i, 1, 1))));
        }
        for (int i = 0; i < 5; i++) {
            men.add(hommes.create(new Homme("Homme" + i, "Prenom" + i, "070000000" + i,
                    "Adresse", LocalDate.of(1970 + i, 1, 1))));
        }
        LocalDate start = LocalDate.of(2000, 1, 1);
        LocalDate end = LocalDate.of(2001, 1, 1);
        for (int i = 0; i < 4; i++) {
            mariages.create(new Mariage(men.get(0), women.get(i), start, null, i + 1));
        }
        mariages.create(new Mariage(men.get(1), women.get(0), LocalDate.of(1990, 1, 1),
                LocalDate.of(1995, 1, 1), 2));
        mariages.create(new Mariage(men.get(2), women.get(0), LocalDate.of(1996, 1, 1), null, 3));
        mariages.create(new Mariage(men.get(3), women.get(4), start, null, 5));

        assertEquals(10, femmes.findAll().size());
        assertEquals(5, hommes.findAll().size());
        assertEquals("Femme0", femmes.findPlusAgee().orElseThrow().getNom());
        assertEquals(4, hommes.findEpousesBetween(men.get(0).getId(), start, end).size());
        assertEquals(4, femmes.countChildrenBetween(women.get(0).getId(), start, end));
        assertTrue(femmes.findMarieesAuMoinsDeuxFois().stream()
                .anyMatch(femme -> femme.getId().equals(women.get(0).getId())));
        assertEquals(List.of(men.get(0).getId()),
                mariages.findHommesMariesAQuatreFemmesBetween(start, end).stream().map(Homme::getId).toList());
        List<Mariage> details = hommes.findMariagesAvecDetails(men.get(0).getId());
        assertEquals(4, details.size());
        assertTrue(details.stream().allMatch(mariage -> mariage.getFemme().getNom() != null));
    }
}
