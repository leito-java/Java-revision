# Erreurs fréquentes

## Lancer Spring Boot avant PostgreSQL

L'application ne peut pas ouvrir sa connexion. Lancez d'abord `docker compose up -d postgres`, puis contrôlez `docker compose ps`.

## Laisser Hibernate créer le schéma

`ddl-auto: update` semble pratique, mais masque l'historique des changements. Utilisez Flyway pour modifier et `validate` pour contrôler.

## Modifier `V1` après son exécution

Flyway détecte un checksum différent. Créez une nouvelle migration `V2` au lieu de réécrire une version partagée.

## Versionner `.env`

Un fichier `.env` peut contenir de vrais secrets. Seul `.env.example`, avec des valeurs locales fictives, doit être commité.

## Utiliser le mot de passe de développement en production

`taskflow_dev` n'est qu'une valeur locale. En production, fournissez `DB_PASSWORD` depuis un gestionnaire de secrets.

## Tester uniquement avec H2

H2 ne reproduit pas toutes les règles PostgreSQL. Gardez les tests H2 rapides, mais ajoutez un test de migration sur PostgreSQL dans la CI.

## Supprimer le volume sans le vouloir

`docker compose down` conserve le volume nommé. L'option `-v` le supprime avec les données : ne l'utilisez que pour repartir volontairement de zéro.
