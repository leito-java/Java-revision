# Quiz — API REST Spring Boot

1. Quel est le rôle d'un contrôleur ?
2. Pourquoi la logique métier est-elle placée dans un service ?
3. Que fait `JpaRepository` ?
4. Pourquoi utiliser `@Valid` ?
5. Quelle réponse convient après une création ?
6. Pourquoi ne pas exposer directement l'entité JPA ?
7. À quoi sert `@Transactional` ?
8. Quelle différence existe entre un test unitaire et un test d'intégration ?

## Réponses

1. Adapter une requête HTTP à un cas d'utilisation.
2. Pour la rendre réutilisable et indépendante du contrôleur.
3. Il fournit les opérations courantes de persistance.
4. Pour exécuter les contraintes des DTOs entrants.
5. `201 Created`, idéalement avec un en-tête `Location`.
6. Pour ne pas coupler le contrat public au modèle de base de données.
7. À délimiter une opération cohérente sur la base.
8. Le test unitaire isole une classe ; le test d'intégration vérifie plusieurs couches ensemble.
