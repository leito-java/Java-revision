# Exercice — Ajouter une route de basculement

## Objectif

Ajouter `PATCH /api/tasks/{id}/toggle` pour inverser l'état `completed`.

## Travail demandé

1. Ajoutez une méthode `toggle()` à l'entité.
2. Ajoutez le cas d'utilisation dans `TaskService`.
3. Exposez la route dans `TaskController`.
4. Retournez la tâche mise à jour.
5. Testez la réussite et l'identifiant inconnu.

## Contraintes

- le contrôleur n'accède pas directement au repository ;
- une tâche inconnue retourne `404` ;
- la modification est effectuée dans une transaction.
