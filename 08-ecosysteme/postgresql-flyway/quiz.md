# Quiz — PostgreSQL et Flyway

1. Pourquoi H2 reste-t-il utile dans ce projet ?
2. Quel composant doit modifier le schéma : Hibernate ou Flyway ?
3. Que signifie `ddl-auto: validate` ?
4. Pourquoi ne faut-il pas modifier une migration déjà appliquée ?
5. Quel fichier démarre PostgreSQL localement ?
6. À quoi sert le volume Docker nommé ?
7. Où Flyway enregistre-t-il les migrations déjà exécutées ?
8. Pourquoi la CI utilise-t-elle un vrai service PostgreSQL ?

## Réponses

1. H2 fournit une base isolée et rapide pour les tests automatisés.
2. Flyway modifie le schéma ; Hibernate le valide.
3. Hibernate compare les entités au schéma et bloque le démarrage en cas d'incohérence.
4. Flyway vérifie son historique et son checksum ; réécrire le passé rend les environnements incohérents.
5. `compose.yml`.
6. Il conserve les fichiers PostgreSQL lorsque le conteneur est recréé.
7. Dans `flyway_schema_history`.
8. Pour détecter les différences SQL que H2 ne reproduit pas fidèlement.
