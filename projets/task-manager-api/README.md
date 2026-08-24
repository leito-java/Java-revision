# Task Manager API

API REST Spring Boot utilisée par le projet Angular `projets/task-manager`.

Suivez d'abord le [chapitre pédagogique Spring Boot](../../08-ecosysteme/spring-boot-api/README.md). Le frontend est maintenu séparément dans [`Angular-revision`](https://github.com/leito-java/Angular-revision).

## Prérequis

- Java 21 ;
- Maven 3.6.3 ou supérieur.

## Lancer l'API

```powershell
cd projets/task-manager-api
mvn spring-boot:run
```

L'API écoute sur `http://localhost:8080`. La documentation interactive est disponible sur `http://localhost:8080/swagger-ui.html`.

## Vérifier

```powershell
mvn test
```

La base H2 est volontairement en mémoire pour cette première version : son contenu repart de zéro à chaque redémarrage. PostgreSQL viendra dans l'évolution suivante.
