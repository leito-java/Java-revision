# Exercice — Diagnostiquer trois pannes de démarrage

Pour chaque scénario, écrivez :

1. la couche probablement en panne ;
2. la commande de vérification minimale ;
3. la correction la plus petite ;
4. la preuve finale attendue.

## Scénario A — compilation

Le `pom.xml` demande Java 21. `java -version` affiche 21, mais Maven échoue avec :

```text
Fatal error compiling: error: release version 21 not supported
```

Quelle information manque avant de réinstaller Java ?

## Scénario B — connexion PostgreSQL

PostgreSQL écoute sur `5433`. Spring Boot affiche :

```text
Connection to localhost:5432 refused
```

Quelle valeur faut-il aligner ? Dans quel terminal doit-elle être définie ?

## Scénario C — pgAdmin

L'API fonctionne avec PostgreSQL sur `5433`, mais aucun nouveau serveur n'apparaît dans pgAdmin.

Est-ce la preuve que l'API utilise une autre base ? Quelles informations faut-il enregistrer dans pgAdmin ?

## Bonus — qualité d'une Pull Request

Rédigez une section « Vérifications » contenant quatre preuves utiles sans exposer de secret.
