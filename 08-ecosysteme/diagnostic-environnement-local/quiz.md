# Quiz — diagnostic de l'environnement local

1. Pourquoi faut-il lire `mvn -version` même si `java -version` est correct ?
2. Que signifie généralement `release version 21 not supported` ?
3. Quelle syntaxe définit `DB_URL` dans PowerShell ?
4. Quelle commande PowerShell associe un port en écoute à un PID ?
5. Une instance PostgreSQL sur `5433` apparaît-elle automatiquement dans pgAdmin ?
6. Quel composant applique les migrations : Flyway ou Hibernate avec `validate` ?
7. Pourquoi cherche-t-on le premier `Caused by` utile ?
8. Quelle vérification prouve que les données sont persistantes ?

## Réponses

1. Maven peut utiliser un autre JDK que la commande `java` trouvée dans le `PATH`.
2. Le compilateur utilisé par Maven est plus ancien que la version demandée par le projet.
3. `$env:DB_URL = "valeur"`.
4. `Get-NetTCPConnection`, puis `Get-Process` avec le PID obtenu.
5. Non, il faut enregistrer manuellement le serveur avec son hôte et son port.
6. Flyway applique les migrations ; Hibernate valide le résultat.
7. Parce que `BUILD FAILURE` est une conséquence générale, tandis que la cause racine guide le diagnostic.
8. Créer une donnée, redémarrer uniquement l'API puis vérifier que la donnée existe encore.
