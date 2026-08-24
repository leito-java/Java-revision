# Erreurs fréquentes — environnement local

## Confondre « installé » et « utilisé »

Plusieurs JDK peuvent être présents. La preuve pertinente est la ligne `Java version` de `mvn -version` dans le terminal courant.

## Copier une commande PowerShell dans cmd

`Remove-Item` et `$env:NOM` appartiennent à PowerShell. Dans `cmd`, utilisez `rmdir` et `set "NOM=valeur"`.

## Supposer que le port 5432 désigne toujours la bonne base

Un ordinateur peut exécuter plusieurs instances PostgreSQL. Vérifiez le port dans `DB_URL` et le processus qui l'écoute.

## Arrêter un processus avant de l'identifier

Un port occupé peut correspondre au service attendu ou à une autre application importante. Relevez son PID puis identifiez-le avant toute action.

## Attendre que pgAdmin découvre le serveur

pgAdmin mémorise des connexions déclarées. Enregistrez explicitement l'hôte, le port, la base et le rôle.

## Corriger seulement la dernière ligne du journal

`BUILD FAILURE` ne suffit pas pour diagnostiquer. Remontez jusqu'au premier message métier ou `Caused by` utile.

## Modifier plusieurs variables simultanément

Une correction massive empêche de savoir quelle hypothèse était juste. Changez un élément, relancez le test minimal, puis conservez la preuve.

## Partager les mots de passe pendant le diagnostic

Masquez les secrets dans les captures, les scripts et les Pull Requests. Documentez le nom de la variable, jamais une valeur de production.
