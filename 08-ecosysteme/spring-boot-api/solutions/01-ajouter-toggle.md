# Correction — Ajouter une route de basculement

Dans l'entité :

```java
public void toggle() {
    this.completed = !this.completed;
}
```

Dans le service :

```java
@Transactional
public TaskResponse toggle(long id) {
    Task task = findEntity(id);
    task.toggle();
    return TaskResponse.from(task);
}
```

Dans le contrôleur :

```java
@PatchMapping("/{id}/toggle")
public TaskResponse toggle(@PathVariable long id) {
    return service.toggle(id);
}
```

JPA détecte la modification de l'entité pendant la transaction. La règle ne dépend pas du protocole HTTP.
