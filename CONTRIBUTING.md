# Contribuer

Les changements importants passent par une branche, une Pull Request et une CI verte.

## Workflow

```bash
git switch main
git pull --ff-only origin main
git switch -c feat/nom-court

# Après les modifications
mvn -f projets/task-manager-api/pom.xml verify
git add chemin/du/fichier
git commit -m "feat: décrire la fonctionnalité"
git push -u origin feat/nom-court
```

Les branches utilisent notamment `feat/`, `fix/`, `docs/`, `test/`, `refactor/`, `chore/` ou `ci/`. Les messages suivent Conventional Commits.

## Definition of Done

- le code compile avec Java 21 ;
- les tests unitaires et d'intégration réussissent ;
- les erreurs et validations sont couvertes ;
- aucun secret ni fichier généré n'est ajouté ;
- la documentation débutant est mise à jour ;
- la CI de la Pull Request est verte.
