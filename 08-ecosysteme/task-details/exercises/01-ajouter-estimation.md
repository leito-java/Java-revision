# Exercice — ajouter une estimation

## Objectif

Ajouter une durée estimée facultative à une tâche.

## Travail demandé

1. créez une migration `V3__add_estimated_minutes.sql` ;
2. ajoutez `estimated_minutes` comme entier positif facultatif ;
3. ajoutez `Integer estimatedMinutes` dans le domaine et les DTO ;
4. refusez zéro et les valeurs négatives avec Bean Validation et une contrainte SQL ;
5. ajoutez les tests API et PostgreSQL ;
6. documentez le nouveau champ.

## Critères de réussite

- une tâche sans estimation reste valide ;
- une estimation positive est conservée ;
- une estimation négative retourne `400` par l'API et est refusée par PostgreSQL ;
- `V1`, `V2` et `V3` sont enregistrées par Flyway.
