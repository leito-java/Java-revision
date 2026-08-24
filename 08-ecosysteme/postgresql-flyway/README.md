# Persister avec PostgreSQL et Flyway

Cette étape remplace la base H2 en mémoire du mode développement par PostgreSQL. Les tâches survivent maintenant au redémarrage de l'API.

## Objectifs

À la fin de ce chapitre, vous saurez :

- expliquer la différence entre une base en mémoire et une base persistante ;
- lancer PostgreSQL avec Docker Compose ;
- connecter Spring Boot avec des variables d'environnement ;
- versionner le schéma avec Flyway ;
- séparer les profils `dev`, `test` et `postgres-test` ;
- vérifier une migration PostgreSQL dans la CI.

## Prérequis

- avoir terminé le chapitre [API REST Spring Boot](../spring-boot-api/README.md) ;
- connaître les notions de table, colonne, clé primaire et contrainte ;
- disposer de Java 21, Maven et Docker Compose.

## Pourquoi quitter H2 en développement ?

H2 est pratique pour des tests rapides, mais une base en mémoire est vidée quand l'application s'arrête. PostgreSQL se rapproche d'un environnement réel et conserve les données dans un volume.

| Besoin | Solution retenue |
|---|---|
| Base locale persistante | PostgreSQL |
| Démarrage reproductible | Docker Compose |
| Historique du schéma | Flyway |
| Vérification entités/schéma | Hibernate `validate` |
| Tests rapides | H2 avec le profil `test` |
| Test réaliste de migration | PostgreSQL dans GitHub Actions |

## Architecture

```text
Angular
   ↓ HTTP /api/tasks
Spring Boot
   ↓ Spring Data JPA
PostgreSQL
   ↑ schéma créé et versionné par Flyway
```

Flyway modifie le schéma. Hibernate vérifie seulement que les entités Java correspondent au schéma obtenu. Cette séparation évite que la structure de production change silencieusement.

## Les fichiers importants

| Fichier | Responsabilité |
|---|---|
| `compose.yml` | démarre PostgreSQL et son volume |
| `.env.example` | documente les valeurs locales non sensibles |
| `application.yml` | configuration commune |
| `application-dev.yml` | connexion PostgreSQL locale |
| `application-test.yml` | base H2 isolée pour les tests rapides |
| `V1__create_tasks.sql` | première version du schéma |
| `V2__add_task_details.sql` | évolution compatible vers le statut et les détails |
| `PostgresMigrationIntegrationTest` | validation réelle de Flyway en CI |

## Démarrage guidé

Placez-vous dans `projets/task-manager-api` :

```powershell
Copy-Item .env.example .env
docker compose up -d postgres
docker compose ps
mvn spring-boot:run
```

Au démarrage, l'ordre important est :

1. Spring Boot ouvre la connexion PostgreSQL ;
2. Flyway crée sa table d'historique ;
3. Flyway applique `V1__create_tasks.sql` ;
4. Hibernate valide la table `tasks` ;
5. l'API commence à accepter les requêtes.

## Comprendre une migration

Le nom `V1__create_tasks.sql` suit trois règles :

- `V1` : numéro unique et croissant ;
- `__` : deux caractères de soulignement ;
- `create_tasks` : description lisible.

Une migration appliquée devient une partie de l'historique. Ne la réécrivez pas : ajoutez `V2`, puis `V3`, etc. Flyway compare les fichiers disponibles à sa table `flyway_schema_history` et applique uniquement les versions manquantes.

## Contraintes utiles

La migration protège les données au plus près de la base :

- `PRIMARY KEY` rend chaque identifiant unique ;
- `NOT NULL` interdit les valeurs absentes ;
- `VARCHAR(120)` limite le titre ;
- `CHECK` refuse une priorité inconnue ;
- `GENERATED ... AS IDENTITY` produit l'identifiant.

Les validations Angular et Java améliorent le message utilisateur. Les contraintes SQL restent le dernier filet de sécurité.

## Profils Spring

| Profil | Base | Flyway | Usage |
|---|---|---|---|
| `dev` | PostgreSQL local | activé | développement manuel |
| `test` | H2 en mémoire | désactivé | tests rapides |
| `postgres-test` | PostgreSQL de CI | activé | migration réaliste |

## Vérifications

```powershell
mvn test
```

Dans GitHub Actions, le même build reçoit `POSTGRES_TEST_URL`. Le test PostgreSQL n'est exécuté que lorsque cette variable existe, ce qui évite d'exiger Docker pour les tests locaux rapides.

## Exercice

Ajoutez une catégorie sans modifier `V1` ou `V2` : [énoncé](exercises/01-ajouter-categorie.md), puis [correction](solutions/01-ajouter-categorie.md).

Complétez ensuite le [quiz](quiz.md) et relisez les [erreurs fréquentes](mistakes.md).

## Sources officielles

- [Initialisation et migrations Flyway avec Spring Boot](https://docs.spring.io/spring-boot/how-to/data-initialization.html)
- [Migrations Flyway](https://documentation.red-gate.com/fd/migrations-271585107.html)
- [Colonnes d'identité PostgreSQL](https://www.postgresql.org/docs/current/ddl-identity-columns.html)
- [Services et healthchecks Docker Compose](https://docs.docker.com/reference/compose-file/services/)
- [Image Docker officielle PostgreSQL](https://hub.docker.com/_/postgres)

## Checklist

- [ ] je peux expliquer le rôle de PostgreSQL, Flyway et Hibernate ;
- [ ] je sais démarrer et arrêter la base sans supprimer son volume ;
- [ ] je crée une nouvelle migration au lieu de modifier une ancienne ;
- [ ] je sais pourquoi H2 est limité au profil de test ;
- [ ] je peux retrouver la migration appliquée dans `flyway_schema_history`.
