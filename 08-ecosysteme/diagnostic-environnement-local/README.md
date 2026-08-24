# Diagnostiquer un environnement local Java

Un développeur professionnel ne se contente pas de relancer une commande au hasard. Il localise la couche en panne, formule une hypothèse, réalise un petit test, corrige une seule cause puis vérifie le résultat.

Ce chapitre transforme les difficultés rencontrées pendant le lancement du Task Manager en méthode réutilisable sur d'autres projets Java.

## Objectifs

À la fin de ce chapitre, vous saurez :

- vérifier le JDK réellement utilisé par Java, le compilateur et Maven ;
- distinguer une commande PowerShell d'une commande `cmd` ;
- identifier le processus qui écoute sur un port ;
- suivre la configuration Spring Boot jusqu'à PostgreSQL ;
- comprendre pourquoi pgAdmin n'affiche pas automatiquement toutes les instances ;
- lire une erreur Flyway en partant de sa cause racine ;
- produire des preuves de fonctionnement utiles dans une Pull Request.

## La carte mentale du système

```text
Maven
  └─ utilise un JDK pour compiler et démarrer Spring Boot
       └─ Spring Boot lit DB_URL, DB_USERNAME et DB_PASSWORD
            └─ se connecte à PostgreSQL sur un port précis
                 └─ Flyway applique les migrations
                      └─ Hibernate valide le schéma
                           └─ l'API écoute sur le port 8080
```

Une erreur en bas de cette chaîne peut empêcher toutes les couches suivantes de démarrer. Commencez toujours par la première couche qui échoue.

## 1. Vérifier Java avant de compiler

Exécutez les trois commandes dans **le même terminal** que celui qui lancera Maven :

```powershell
java -version
javac -version
mvn -version
```

| Commande | Ce qu'elle vérifie |
|---|---|
| `java -version` | le runtime trouvé dans `PATH` |
| `javac -version` | le compilateur du JDK |
| `mvn -version` | la version de Java réellement utilisée par Maven |

Le `pom.xml` du projet demande Java 21. L'erreur `release version 21 not supported` signifie que Maven utilise un JDK plus ancien, même si un JDK 21 est installé ailleurs sur la machine.

Correction temporaire dans PowerShell :

```powershell
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
mvn -version
```

Équivalent dans `cmd` :

```cmd
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"
mvn -version
```

Adaptez le chemin à la version installée. Cette modification ne concerne que le terminal courant. Pour une configuration durable, modifiez `JAVA_HOME` et `Path` dans les variables d'environnement Windows, puis ouvrez un nouveau terminal.

## 2. Reconnaître PowerShell et cmd

Les deux terminaux n'utilisent pas toujours la même syntaxe.

| Besoin | PowerShell | cmd |
|---|---|---|
| Changer de dossier | `Set-Location "C:\projet"` | `cd /d "C:\projet"` |
| Supprimer `node_modules` | `Remove-Item -Recurse -Force node_modules` | `rmdir /s /q node_modules` |
| Définir une variable | `$env:DB_URL = "..."` | `set "DB_URL=..."` |
| Lire une variable | `$env:DB_URL` | `echo %DB_URL%` |

Si Windows répond « n'est pas reconnu en tant que commande », vérifiez d'abord que la commande correspond au terminal ouvert.

## 3. Vérifier les ports et les processus

Dans cette application, les ports habituels sont :

| Port | Service |
|---|---|
| `4200` | serveur Angular |
| `8080` | API Spring Boot |
| `5432` ou `5433` | PostgreSQL local |

PowerShell permet de voir qui écoute :

```powershell
Get-NetTCPConnection -State Listen -LocalPort 4200, 8080, 5432, 5433 |
  Select-Object LocalAddress, LocalPort, OwningProcess
```

Puis d'identifier un processus à partir de son PID :

```powershell
Get-Process -Id 1234
```

Dans `cmd` :

```cmd
netstat -ano | findstr /C:":4200" /C:":8080" /C:":5432" /C:":5433"
tasklist /FI "PID eq 1234"
```

Un port occupé n'est pas forcément une erreur : il peut s'agir du service attendu. Identifiez le processus avant de l'arrêter.

## 4. Suivre la configuration PostgreSQL

Le profil `dev` lit :

```text
DB_URL      → adresse, port et nom de la base
DB_USERNAME → rôle PostgreSQL
DB_PASSWORD → mot de passe de ce rôle
```

Si aucune variable n'est fournie, `application-dev.yml` utilise `localhost:5432`, la base `taskflow` et l'utilisateur `taskflow`.

Pour une instance sur `5433`, configurez les trois valeurs dans le terminal qui lance l'API :

```powershell
$env:DB_URL = "jdbc:postgresql://localhost:5433/taskflow"
$env:DB_USERNAME = "taskflow"
$env:DB_PASSWORD = "taskflow_dev"
mvn spring-boot:run
```

N'écrivez jamais un vrai mot de passe dans Git, une capture d'écran ou un journal partagé. Les valeurs ci-dessus sont réservées au développement local.

## 5. Comprendre pgAdmin

pgAdmin est un client d'administration. Il ne crée pas une connexion visuelle pour chaque serveur PostgreSQL trouvé sur l'ordinateur. Il faut enregistrer le serveur une fois :

| Champ pgAdmin | Valeur locale possible |
|---|---|
| Name | `TaskFlow PostgreSQL 16` |
| Host name/address | `127.0.0.1` |
| Port | `5433` |
| Maintenance database | `postgres` |
| Username | `taskflow` |
| Password | valeur locale de `DB_PASSWORD` |

Deux serveurs peuvent cohabiter sur `5432` et `5433`. Le nom affiché dans pgAdmin n'a aucun effet sur le port : seuls les paramètres de connexion comptent.

Après connexion, vérifiez :

- la base `taskflow` ;
- la table `public.tasks` ;
- la table `public.flyway_schema_history` ;
- les versions `1` et `2` avec un statut réussi.

## 6. Lire une erreur Spring Boot ou Flyway

La dernière ligne `BUILD FAILURE` décrit seulement la conséquence. Remontez dans le journal jusqu'au premier `Caused by` utile.

| Message racine | Hypothèse principale | Vérification minimale |
|---|---|---|
| `Connection refused` | aucun PostgreSQL sur ce port | contrôler le port et le service |
| `password authentication failed` | utilisateur ou mot de passe incorrect | comparer les variables à l'instance ciblée |
| `database ... does not exist` | mauvais nom de base | vérifier `DB_URL` et pgAdmin |
| `Validate failed` ou checksum différent | migration appliquée modifiée | inspecter `flyway_schema_history` et créer une nouvelle migration |
| `release version 21 not supported` | Maven utilise un ancien JDK | lire la ligne `Java version` de `mvn -version` |

Ne changez pas plusieurs réglages à la fois. Sinon, vous ne saurez pas quelle correction a réellement fonctionné.

## 7. Séquence de démarrage et preuves

Ordre recommandé :

1. vérifier `mvn -version` ;
2. démarrer PostgreSQL ;
3. confirmer son port ;
4. définir les variables de connexion si nécessaire ;
5. lancer `mvn test` ;
6. lancer `mvn spring-boot:run` ;
7. tester l'API ;
8. lancer Angular.

Vérification rapide de l'API :

```powershell
Invoke-RestMethod http://localhost:8080/api/tasks
```

Test de persistance : créez une tâche, redémarrez uniquement Spring Boot, puis contrôlez qu'elle existe encore. Ce test prouve davantage qu'une simple page qui s'affiche.

Dans une Pull Request, notez les commandes exécutées et les résultats : version du JDK, nombre de tests réussis, build, migration PostgreSQL et test manuel. Ne collez ni mot de passe ni journal complet contenant des données sensibles.

## Méthode professionnelle en six questions

1. Quel comportement précis est observé ?
2. Quelle est la première erreur utile ?
3. Quelle couche peut produire cette erreur ?
4. Quel test minimal confirme l'hypothèse ?
5. Quelle modification unique corrige la cause ?
6. Quelle preuve montre que le système complet fonctionne encore ?

## Pratiquer

Réalisez l'[exercice de diagnostic](exercises/01-diagnostiquer-demarrage.md), puis comparez avec la [correction](solutions/01-diagnostiquer-demarrage.md).

Exécutez ensuite le [script de contrôle en lecture seule](examples/check-environment.ps1), répondez au [quiz](quiz.md) et consultez les [erreurs fréquentes](mistakes.md).

## Je peux continuer si…

- je sais prouver quel JDK Maven utilise ;
- je reconnais la syntaxe de mon terminal ;
- je peux relier un port à un processus ;
- je sais expliquer la chaîne Spring Boot → Flyway → PostgreSQL ;
- je peux enregistrer une instance dans pgAdmin ;
- je cherche une cause racine avant de modifier la configuration.

## Sources officielles

- [Maven — installation et variable `JAVA_HOME`](https://maven.apache.org/install.html)
- [Spring Boot — profils](https://docs.spring.io/spring-boot/reference/features/profiles.html)
- [Spring Boot — configuration externalisée](https://docs.spring.io/spring-boot/reference/features/external-config.html)
- [Flyway — migrations](https://documentation.red-gate.com/fd/migrations-271585107.html)
- [PostgreSQL — rôles et utilisateurs](https://www.postgresql.org/docs/current/user-manag.html)
- [pgAdmin — enregistrer un serveur](https://www.pgadmin.org/docs/pgadmin4/latest/server_dialog.html)
