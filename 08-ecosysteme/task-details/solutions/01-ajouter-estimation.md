# Correction — ajouter une estimation

## Migration

```sql
ALTER TABLE tasks
    ADD COLUMN estimated_minutes INTEGER,
    ADD CONSTRAINT chk_tasks_estimated_minutes
        CHECK (estimated_minutes IS NULL OR estimated_minutes > 0);
```

## DTO

```java
@Positive(message = "L'estimation doit être strictement positive")
Integer estimatedMinutes
```

`Integer` est utilisé à la place de `int`, car l'absence d'estimation est une valeur métier valide.

## Entité

```java
@Column(name = "estimated_minutes")
private Integer estimatedMinutes;
```

Transmettez ensuite cette propriété au constructeur, à `update` et à `TaskResponse`.

## Tests

Ajoutez un test de création avec `estimatedMinutes: 45`, un test de validation avec `-1` et une insertion SQL dans le test PostgreSQL.
