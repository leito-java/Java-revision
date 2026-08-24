# Erreurs fréquentes

## Remplacer brutalement `completed`

Le frontend actuel dépend encore de cette propriété. Gardez une période de compatibilité et migrez les consommateurs avant de la supprimer du JSON.

## Ajouter directement un statut `NOT NULL`

Les anciennes lignes ne possèdent aucune valeur. Ajoutez la colonne, remplissez-la, puis activez `NOT NULL`.

## Garder deux sources de vérité

Stocker durablement `completed` et `status` permet des contradictions. Stockez seulement le statut et dérivez le booléen.

## Exposer le nom Java de l'enum sans contrôle

Renommer une constante pourrait casser le JSON. `@JsonValue` et `@JsonCreator` rendent le contrat explicite.

## Casser le contrat sans test de compatibilité

Un test utilisant encore `completed` prouve que l'ancien Angular peut continuer à modifier une tâche jusqu'à sa propre évolution.

## Modifier V1

Une migration appliquée appartient à l'histoire. Toute évolution doit être décrite dans un nouveau fichier V2, V3, etc.
