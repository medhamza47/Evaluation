package ma.projet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.Produit;
import ma.projet.util.HibernateUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StockServiceTest {
    @BeforeAll
    static void configureDatabase() {
        System.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        System.setProperty("hibernate.connection.url", "jdbc:h2:mem:stock;DB_CLOSE_DELAY=-1");
        System.setProperty("hibernate.connection.username", "sa");
        System.setProperty("hibernate.connection.password", "");
        System.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        System.setProperty("hibernate.hbm2ddl.auto", "create-drop");
    }

    @AfterAll
    static void closeDatabase() {
        HibernateUtil.shutdown("stock");
        System.clearProperty("hibernate.connection.driver_class");
        System.clearProperty("hibernate.connection.url");
        System.clearProperty("hibernate.connection.username");
        System.clearProperty("hibernate.connection.password");
        System.clearProperty("hibernate.dialect");
        System.clearProperty("hibernate.hbm2ddl.auto");
    }

    @Test
    void persistsStockAndFiltersOrdersByCategoryDateAndNamedPriceQuery() {
        CategorieService categories = new CategorieService();
        ProduitService products = new ProduitService();
        CommandeService orders = new CommandeService();

        Categorie category = new Categorie("INFO", "Informatique");
        Produit keyboard = new Produit("KB-1", new BigDecimal("120.00"));
        Produit mouse = new Produit("MS-1", new BigDecimal("80.00"));
        category.addProduit(keyboard);
        category.addProduit(mouse);
        categories.create(category);
        Commande order = new Commande(LocalDate.of(2025, 3, 14));
        order.addLigne(keyboard, 7);
        order.addLigne(mouse, 14);
        orders.create(order);

        assertEquals(2, products.findByCategorie(category.getId()).size());
        assertEquals(List.of("KB-1", "MS-1"), products.findOrderedBetween(
                LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 31))
                .stream().map(Produit::getReference).toList());
        assertEquals(List.of("KB-1"), products.findAbovePrice(new BigDecimal("100"))
                .stream().map(Produit::getReference).toList());
        assertEquals(2, orders.findProductsInOrder(order.getId()).size());
        assertThrows(IllegalArgumentException.class,
                () -> products.findOrderedBetween(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 3, 1)));
    }
}
