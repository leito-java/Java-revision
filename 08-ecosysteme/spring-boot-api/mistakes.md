# Erreurs fréquentes — API Spring Boot

## Le contrôleur contient toute la logique

Déplacez les cas d'utilisation dans `TaskService`. Le contrôleur doit principalement gérer HTTP.

## `@Valid` ne produit aucune erreur

Vérifiez la présence du starter Validation et placez `@Valid` devant `@RequestBody`.

## Une priorité inconnue produit une erreur illisible

Transformez `HttpMessageNotReadableException` en réponse `400` cohérente avec un gestionnaire global.

## L'entité est renvoyée directement

Ajoutez un DTO de réponse afin de stabiliser le contrat JSON et d'éviter d'exposer des champs internes.

## Les données disparaissent au redémarrage

La base H2 est en mémoire dans cette étape. Utilisez PostgreSQL dans l'évolution suivante.

## Le navigateur Angular bloque la requête

Vérifiez le proxy Angular ou la configuration CORS limitée à `http://localhost:4200` en développement.

## Le test dépend des données de démonstration

Activez un profil `test`, nettoyez le repository avant chaque scénario et créez uniquement les données nécessaires au test.
