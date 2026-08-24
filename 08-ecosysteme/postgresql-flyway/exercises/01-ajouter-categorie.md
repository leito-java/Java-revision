# Exercice — ajouter une catégorie

## Objectif

Ajouter une catégorie facultative à chaque tâche sans modifier les migrations `V1` et `V2` déjà partagées.

## Travail demandé

1. créez `V3__add_category_to_tasks.sql` ;
2. ajoutez une colonne `category` de 50 caractères qui accepte `NULL` ;
3. ajoutez la propriété correspondante dans `Task` et les DTO ;
4. normalisez une chaîne vide vers `null` ;
5. ajoutez un test d'intégration API et adaptez le test PostgreSQL ;
6. documentez un exemple JSON.

## Critères de réussite

- les migrations s'exécutent dans l'ordre `V1`, `V2`, `V3` ;
- une ancienne tâche sans catégorie reste valide ;
- une catégorie de plus de 50 caractères retourne `400` ;
- Hibernate valide le schéma final.
