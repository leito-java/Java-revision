# Créer une API REST avec Spring Boot

Ce chapitre utilise Java 21, Spring Boot 4.1, Maven, Spring Web MVC, Spring Data JPA, Validation et H2.

## Objectif

Construire le backend du Task Manager consommé par le dépôt Angular `Angular-revision`.

```text
requête HTTP
→ TaskController
→ TaskService
→ TaskRepository
→ base H2
→ réponse JSON
```

## Responsabilités

| Élément | Rôle |
|---|---|
| `TaskController` | Traduire HTTP en appels applicatifs |
| DTOs | Définir et valider le contrat JSON |
| `TaskService` | Exécuter les cas d'utilisation |
| `TaskRepository` | Lire et écrire les entités |
| `Task` | Représenter la tâche persistée |
| `ApiExceptionHandler` | Produire des erreurs HTTP cohérentes |

Cette séparation applique le principe de responsabilité unique : chaque classe possède une raison principale de changer.

## Routes REST

| Méthode | URL | Résultat |
|---|---|---|
| `GET` | `/api/tasks` | Lister les tâches |
| `GET` | `/api/tasks/{id}` | Lire une tâche |
| `POST` | `/api/tasks` | Créer une tâche |
| `PUT` | `/api/tasks/{id}` | Modifier une tâche |
| `DELETE` | `/api/tasks/{id}` | Supprimer une tâche |

Une création réussie retourne `201 Created`. Une suppression réussie retourne `204 No Content`. Une tâche inconnue retourne `404 Not Found`.

## Pourquoi utiliser des DTOs ?

`CreateTaskRequest` et `UpdateTaskRequest` décrivent les données acceptées. `TaskResponse` décrit la réponse. L'entité JPA reste interne : une modification de la base ne casse pas automatiquement le contrat HTTP.

La validation serveur est obligatoire même si Angular valide déjà les champs, car un autre programme peut appeler directement l'API.

## Lancer le projet

```powershell
cd projets/task-manager-api
mvn spring-boot:run
```

- API : `http://localhost:8080/api/tasks`
- Swagger UI : `http://localhost:8080/swagger-ui.html`
- Console H2 : `http://localhost:8080/h2-console`

H2 est en mémoire : les données disparaissent quand l'API s'arrête. PostgreSQL viendra dans l'évolution suivante.

## Tester avec PowerShell

```powershell
Invoke-RestMethod http://localhost:8080/api/tasks

$body = @{ title = 'Comprendre Spring Boot'; priority = 'high' } | ConvertTo-Json
Invoke-RestMethod http://localhost:8080/api/tasks -Method Post -ContentType 'application/json' -Body $body
```

## Lire le code dans le bon ordre

1. `TaskManagerApiApplication.java` ;
2. `TaskController.java` ;
3. `CreateTaskRequest`, `UpdateTaskRequest` et `TaskResponse` ;
4. `TaskService.java` ;
5. `TaskRepository.java` et `Task.java` ;
6. `ApiExceptionHandler.java` ;
7. les tests.

## Pratiquer

1. Faites l'[exercice du basculement d'état](exercises/01-ajouter-toggle.md).
2. Consultez ensuite la [correction](solutions/01-ajouter-toggle.md).
3. Répondez au [quiz](quiz.md).
4. Utilisez les [erreurs fréquentes](mistakes.md) pour diagnostiquer un problème.

## Je peux continuer si…

- je sais expliquer Controller, Service et Repository ;
- je sais associer GET, POST, PUT et DELETE à une intention ;
- je comprends pourquoi l'entité et les DTOs sont séparés ;
- je sais valider une entrée et retourner `400` ou `404` ;
- je peux tester l'API sans Angular.

## Sources officielles

- [Spring — Building a RESTful Web Service](https://spring.io/guides/gs/rest-service)
- [Spring — Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa)
- [Spring Boot — Validation](https://docs.spring.io/spring-boot/reference/io/validation.html)
- [Spring Boot — Testing](https://docs.spring.io/spring-boot/reference/testing/)
