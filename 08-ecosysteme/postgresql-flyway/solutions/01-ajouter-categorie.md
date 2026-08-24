# Correction — ajouter une catégorie

## Migration

Créez `src/main/resources/db/migration/V3__add_category_to_tasks.sql` :

```sql
ALTER TABLE tasks
    ADD COLUMN category VARCHAR(50);
```

La colonne accepte `NULL` afin que les données existantes restent compatibles.

## Modèle et DTO

Dans l'entité :

```java
@Column(length = 50)
private String category;
```

Dans les DTO de création et de modification :

```java
@Size(max = 50, message = "La catégorie ne doit pas dépasser 50 caractères")
String category
```

Une chaîne vide ne représente pas une vraie catégorie. Normalisez-la vers `null` avant d'appeler le domaine.

## Vérifications

Testez au minimum une tâche avec catégorie, une tâche sans catégorie et le rejet d'une valeur trop longue. Le test PostgreSQL doit constater trois migrations réussies.
