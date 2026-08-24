# Exercice — ajouter une date d'échéance

## Objectif

Ajouter une colonne facultative `due_date` à la table `tasks` sans modifier la migration `V1`.

## Travail demandé

1. créez `V2__add_due_date_to_tasks.sql` ;
2. ajoutez une colonne SQL `due_date` de type `DATE`, qui accepte `NULL` ;
3. ajoutez un champ `LocalDate dueDate` dans l'entité `Task` ;
4. transmettez cette valeur dans les DTO de création, modification et réponse ;
5. ajoutez au moins un test d'intégration ;
6. redémarrez l'API et contrôlez l'historique Flyway.

## Contraintes

- ne modifiez pas `V1__create_tasks.sql` ;
- conservez les anciennes tâches valides, même sans date ;
- utilisez le format JSON ISO `YYYY-MM-DD` ;
- expliquez pourquoi la colonne est nullable lors de cette évolution.

## Critères de réussite

- Flyway applique `V2` après `V1` ;
- Hibernate valide le schéma ;
- une tâche sans date reste acceptée ;
- une tâche avec date renvoie la même valeur dans l'API.
