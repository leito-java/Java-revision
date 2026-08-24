# Faire évoluer le domaine et le contrat API

Cette étape transforme une tâche binaire en élément de travail utilisable dans une liste ou un tableau Kanban.

## Objectifs

À la fin de ce chapitre, vous saurez :

- enrichir une entité sans mélanger HTTP, métier et persistance ;
- remplacer un booléen limité par un statut métier ;
- migrer les données existantes avec Flyway ;
- faire évoluer un contrat JSON sans casser immédiatement son consommateur ;
- tester le domaine, l'API et le schéma PostgreSQL.

## Le problème avec `completed`

Un booléen ne peut exprimer que deux états. Le futur Kanban a besoin de trois colonnes :

```text
TODO → IN_PROGRESS → DONE
```

`TaskStatus` devient donc la source de vérité. `completed` reste temporairement dans les réponses JSON, mais il est calculé avec `status == DONE` et n'est plus stocké en base.

## Nouveau modèle

| Propriété | Type | Règle |
|---|---|---|
| `title` | texte | obligatoire, 3 à 120 caractères |
| `description` | texte | facultative, 1000 caractères maximum |
| `priority` | enum | `low`, `medium` ou `high` |
| `status` | enum | `todo`, `in-progress` ou `done` |
| `dueDate` | date ISO | facultative |
| `completed` | booléen dérivé | compatibilité avec l'ancien frontend |

## Évolution de la base

`V2__add_task_details.sql` procède dans un ordre sûr :

1. ajouter des colonnes encore facultatives ;
2. convertir chaque ancienne valeur `completed` vers `TODO` ou `DONE` ;
3. rendre `status` obligatoire ;
4. ajouter une contrainte `CHECK` ;
5. retirer la colonne devenue inutile.

Cette stratégie évite de rendre immédiatement invalides les lignes déjà présentes.

## Compatibilité du contrat

L'ancien frontend envoie encore :

```json
{
  "title": "Tester l'API",
  "priority": "high",
  "completed": true
}
```

Le nouveau contrat préfère :

```json
{
  "title": "Tester l'API",
  "description": "Exécuter les tests d'intégration",
  "priority": "high",
  "status": "in-progress",
  "dueDate": "2026-09-15"
}
```

`UpdateTaskRequest` accepte temporairement l'une des deux représentations. Si `status` et `completed` sont tous les deux présents, ils doivent être cohérents.

## Responsabilités

| Élément | Responsabilité |
|---|---|
| `TaskStatus` | nommer les états métier et leur format JSON |
| `Task` | protéger un statut non nul et dériver `completed` |
| DTO de requête | valider et normaliser les entrées |
| `TaskService` | choisir le statut initial et orchestrer la mise à jour |
| `TaskResponse` | exposer le nouveau contrat et la compatibilité |
| migration V2 | transformer le schéma et les données |

## Tests importants

- création détaillée et sérialisation de la date ;
- statut par défaut `todo` ;
- modification avec le nouveau champ `status` ;
- compatibilité avec l'ancien champ `completed` ;
- rejet d'un état contradictoire ;
- application réelle de V1 et V2 sur PostgreSQL dans la CI.

## Lire le code

1. `TaskStatus.java` ;
2. `Task.java` ;
3. `CreateTaskRequest` et `UpdateTaskRequest` ;
4. `TaskService.java` ;
5. `TaskResponse.java` ;
6. `V2__add_task_details.sql` ;
7. les tests.

## Pratiquer

Réalisez l'[exercice d'ajout d'une estimation](exercises/01-ajouter-estimation.md), puis consultez la [correction](solutions/01-ajouter-estimation.md).

Terminez avec le [quiz](quiz.md) et les [erreurs fréquentes](mistakes.md).

## Sources officielles

- [Validation avec Spring Boot](https://docs.spring.io/spring-boot/reference/io/validation.html)
- [Contrainte `@AssertTrue` de Jakarta Validation](https://jakarta.ee/specifications/bean-validation/3.1/apidocs/jakarta.validation/jakarta/validation/constraints/asserttrue)
- [Commande `ALTER TABLE` de PostgreSQL](https://www.postgresql.org/docs/current/sql-altertable.html)
- [Migrations versionnées avec Flyway](https://documentation.red-gate.com/fd/migrations-271585107.html)
- [Annotations JSON de Jackson](https://github.com/FasterXML/jackson-annotations)

## Checklist

- [ ] je sais pourquoi un enum est plus adapté qu'un booléen pour un workflow ;
- [ ] je peux expliquer la transformation des anciennes données dans V2 ;
- [ ] je distingue la valeur stockée de la propriété JSON dérivée ;
- [ ] je sais faire évoluer une API sans casser immédiatement Angular ;
- [ ] je peux retrouver chaque règle dans un test.
