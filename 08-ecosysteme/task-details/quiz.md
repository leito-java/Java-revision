# Quiz — évolution du domaine

1. Pourquoi `status` remplace-t-il le stockage de `completed` ?
2. Pourquoi la réponse contient-elle encore `completed` ?
3. Quelle valeur reçoit une tâche créée sans statut ?
4. Pourquoi V2 ajoute-t-elle d'abord une colonne nullable ?
5. À quoi sert la commande `UPDATE` dans V2 ?
6. Pourquoi le domaine conserve-t-il un enum Java en majuscules et expose-t-il un JSON en minuscules ?
7. Que doit faire l'API si `status: done` et `completed: false` sont envoyés ensemble ?

## Réponses

1. Trois états métier ne peuvent pas être représentés par un booléen.
2. Pour laisser le frontend actuel fonctionner pendant sa migration.
3. `todo`.
4. Les anciennes lignes doivent recevoir une valeur avant que `NOT NULL` soit activé.
5. Elle transforme les données historiques vers le nouveau modèle.
6. Chaque couche garde une convention claire et le contrat JSON reste stable.
7. Retourner `400 Bad Request`, car les deux valeurs se contredisent.
