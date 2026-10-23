# Evaluation Hibernate & JPA

Correction des trois exercices du laboratoire MLIAEdu. Chaque exercice est un
module Maven indépendant et conserve les packages demandes (`ma.projet.classes`
pour les deux premiers exercices et `ma.projet.beans` pour le troisieme).

## Modules

- `exercice-1-stock` : categories, produits, commandes et lignes de commande.
- `exercice-2-projets` : employes, projets, taches et affectations avec dates reelles.
- `exercice-3-etat-civil` : personnes, hommes, femmes et mariages.
- `persistence-common` : interface DAO generique, implementation CRUD et demarrage Hibernate.

## Prerequis

- JDK 17 ou plus recent
- Maven 3.9+
- MySQL pour une execution reelle (les tests utilisent H2 en memoire)

## Compiler et tester

Depuis la racine du depot :

```shell
mvn clean verify
```

Les tests creent automatiquement une base H2 en memoire et couvrent les
operations CRUD ainsi que les requetes specifiques des trois exercices.

## Configuration MySQL

Chaque module contient son propre `src/main/resources/application.properties`.
Par defaut, Hibernate se connecte a MySQL sur `localhost:3306` avec l'utilisateur
`root` et un mot de passe vide, puis cree/actualise la base correspondante
(`gestion_stock`, `gestion_projets` ou `etat_civil`).

Pour modifier les parametres, editez le fichier du module concerne ou passez
les proprietes Hibernate correspondantes avec `-D`, par exemple :

```shell
mvn -pl exercice-1-stock exec:java \
  -Dhibernate.connection.username=root \
  -Dhibernate.connection.password=votre_mot_de_passe
```

Les classes de service utilisent les noms d'unites de persistance `stock`,
`projets` et `etat-civil`. Les requetes de date utilisent des intervalles
inclusifs et les mariages/taches qui chevauchent l'intervalle fourni.
