# Java — Guide de révision et d'apprentissage

Un dépôt communautaire pour apprendre Java pas à pas, réviser efficacement et pratiquer avec des exemples exécutables.

## Parcours conseillé

| Étape | Thème | Objectif |
|---|---|---|
| 01 | Fondamentaux | Variables, types, conditions, boucles, méthodes |
| 02 | Programmation orientée objet | Classes, objets, encapsulation, héritage, polymorphisme |
| 03 | Collections et génériques | List, Set, Map, Stream API |
| 04 | Gestion des erreurs | Exceptions et validation |
| 05 | Fichiers et dates | I/O, NIO et dates |
| 06 | Tests | JUnit et tests unitaires |
| 07 | Concurrence | Threads et CompletableFuture |
| 08 | [Écosystème](08-ecosysteme/README.md) | Maven, Spring Boot, API REST et persistance |

## Organisation

```text
01-fondamentaux/      # Cours courts et exercices
02-poo/
03-collections/
04-exceptions/
05-fichiers-et-dates/
06-tests/
07-concurrence/
08-ecosysteme/
projets/              # Mini-projets guidés
ressources/           # Liens, fiches mémo et glossaire
```

## Projet full-stack

Le backend du Task Manager se trouve dans [`projets/task-manager-api`](projets/task-manager-api/README.md). Il expose une API REST consommée par le dépôt Angular séparé [`Angular-revision`](https://github.com/leito-java/Angular-revision).

Commencez par le chapitre [Créer une API REST avec Spring Boot](08-ecosysteme/spring-boot-api/README.md) avant de lire tout le projet.

## Convention pour chaque chapitre

- `README.md` : notions, prérequis et pièges fréquents ;
- `examples/` : exemples minimaux et commentés ;
- `exercises/` : énoncés puis corrections ;
- `quiz.md` : questions rapides de révision.

## Premiers thèmes à documenter

- Différence entre `==` et `.equals()`
- Visibilité : `public`, `private`, `protected`
- `ArrayList`, `HashSet` et `HashMap`
- Lambda, interfaces fonctionnelles et Stream API
- Exceptions vérifiées/non vérifiées
- JUnit et le principe Arrange–Act–Assert

## Contribuer

Consultez le [guide de contribution](CONTRIBUTING.md) pour le workflow Git, les conventions et la Definition of Done.

## Licence

La licence **MIT** est simple et adaptée à un contenu éducatif ouvert.
