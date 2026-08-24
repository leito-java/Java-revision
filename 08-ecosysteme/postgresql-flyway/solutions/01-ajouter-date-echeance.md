# Correction — ajouter une date d'échéance

## Migration

Créez `src/main/resources/db/migration/V2__add_due_date_to_tasks.sql` :

```sql
ALTER TABLE tasks
    ADD COLUMN due_date DATE;
```

La colonne accepte `NULL` afin que les lignes créées avant cette évolution restent valides.

## Entité

Ajoutez dans `Task` :

```java
@Column(name = "due_date")
private LocalDate dueDate;
```

Adaptez ensuite le constructeur ou la méthode métier `update`, les requêtes `CreateTaskRequest` et `UpdateTaskRequest`, puis `TaskResponse`.

## Exemple de vérification MockMvc

```java
mockMvc.perform(post("/api/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
                {
                  "title": "Préparer la démonstration",
                  "priority": "high",
                  "dueDate": "2026-09-15"
                }
                """))
    .andExpect(status().isCreated())
    .andExpect(jsonPath("$.dueDate").value("2026-09-15"));
```

## À retenir

`V1` représente l'histoire déjà partagée. `V2` décrit explicitement l'évolution suivante et peut être exécutée de façon identique dans chaque environnement.
