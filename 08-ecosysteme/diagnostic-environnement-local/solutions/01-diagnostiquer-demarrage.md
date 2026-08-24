# Correction — Diagnostiquer trois pannes de démarrage

## Scénario A

La couche suspecte est la chaîne Maven/JDK. Il faut exécuter `mvn -version` dans le même terminal. Sa ligne `Java version` peut révéler un ancien JDK alors que `java` pointe déjà vers Java 21.

Définissez `JAVA_HOME` et placez `%JAVA_HOME%\bin` ou `$env:JAVA_HOME\bin` au début du `PATH`, puis relancez `mvn -version` et `mvn test`. Réinstaller sans vérifier cette information ne prouve pas que Maven utilisera la nouvelle installation.

## Scénario B

La configuration Spring Boot vise encore le port par défaut `5432`. Dans le terminal qui lancera Maven :

```powershell
$env:DB_URL = "jdbc:postgresql://localhost:5433/taskflow"
mvn spring-boot:run
```

La preuve finale est double : Spring Boot démarre sur `8080` et `Invoke-RestMethod http://localhost:8080/api/tasks` reçoit une réponse.

## Scénario C

Non. pgAdmin ne découvre pas et n'enregistre pas automatiquement chaque instance locale. Ajoutez un serveur avec l'hôte `127.0.0.1`, le port `5433`, la base de maintenance `postgres`, l'utilisateur configuré et son mot de passe local.

Vérifiez ensuite la base `taskflow`, la table `tasks` et `flyway_schema_history`.

## Exemple de preuves de Pull Request

```text
- `mvn -version` : Maven utilise Java 21
- `mvn test` : 8 tests exécutés, 0 échec
- démarrage PostgreSQL : port local contrôlé
- test manuel : création, redémarrage de l'API et tâche toujours présente
```

Le mot de passe, le contenu de `.env` et les journaux contenant des secrets ne doivent pas être copiés.
