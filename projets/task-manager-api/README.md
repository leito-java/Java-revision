# Task Manager API

API REST Spring Boot utilisée par le Task Manager du dépôt [`Angular-revision`](https://github.com/leito-java/Angular-revision).

Lisez d'abord [Créer une API REST avec Spring Boot](../../08-ecosysteme/spring-boot-api/README.md), puis [Persister avec PostgreSQL et Flyway](../../08-ecosysteme/postgresql-flyway/README.md) et [Faire évoluer le domaine et le contrat API](../../08-ecosysteme/task-details/README.md).

## Prérequis

- Java 21 ;
- Maven 3.6.3 ou supérieur ;
- Docker Desktop, ou Docker Engine avec le plugin Compose.

## Démarrage local

Depuis `projets/task-manager-api` :

```powershell
Copy-Item .env.example .env
docker compose up -d postgres
docker compose ps
mvn spring-boot:run
```

Les valeurs de développement par défaut sont :

| Élément | Valeur |
|---|---|
| API | `http://localhost:8080` |
| Swagger UI | `http://localhost:8080/swagger-ui.html` |
| Base | `taskflow` |
| Utilisateur | `taskflow` |
| Mot de passe | `taskflow_dev` |
| Port PostgreSQL | `5432` |

Le mot de passe est volontairement simple pour le développement local. Utilisez un secret fourni par l'environnement en production.

## Variables Spring Boot

Le profil `dev` accepte les variables suivantes :

```powershell
$env:DB_URL = "jdbc:postgresql://localhost:5432/taskflow"
$env:DB_USERNAME = "taskflow"
$env:DB_PASSWORD = "taskflow_dev"
mvn spring-boot:run
```

Sans variables, les valeurs locales du tableau sont utilisées.

## Migrations

Flyway exécute au démarrage les scripts de `src/main/resources/db/migration`. Hibernate utilise `ddl-auto: validate` : il contrôle la cohérence des entités, mais ne modifie pas le schéma.

Ne modifiez pas une migration déjà appliquée. Pour chaque évolution, créez un nouveau fichier, par exemple :

```text
V3__add_category_to_tasks.sql
```

`V1` crée la table. `V2` ajoute la description, le statut et l'échéance, transforme les anciennes valeurs `completed`, puis retire cette colonne du stockage.

## Contrat des tâches

Créer une tâche enrichie :

```json
{
  "title": "Préparer la démonstration",
  "description": "Présenter Angular et Spring Boot",
  "priority": "high",
  "status": "in-progress",
  "dueDate": "2026-09-15"
}
```

Valeurs acceptées pour `status` : `todo`, `in-progress` et `done`. Une création sans statut commence automatiquement à `todo`.

Chaque réponse contient encore `completed` pour ne pas casser le frontend Angular actuel. Cette valeur est calculée depuis `status` et n'est plus stockée dans PostgreSQL.

## Tests

```powershell
mvn test
```

- le profil `test` utilise H2 et désactive Flyway pour garder les tests rapides ;
- la CI démarre aussi PostgreSQL et vérifie réellement la migration Flyway.

## Vérifier la persistance

1. créez une tâche depuis Angular ;
2. arrêtez puis relancez uniquement l'API ;
3. rechargez Angular ;
4. vérifiez que la tâche existe encore.

Pour arrêter PostgreSQL sans supprimer ses données :

```powershell
docker compose down
```

Le volume nommé est conservé. La commande `docker compose down -v` supprimerait volontairement les données locales.
