package com.leito.taskmanager.task.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

/** Entité persistée dans la table tasks. */
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false)
    private boolean completed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TaskPriority priority;

    protected Task() {
        // Constructeur requis par JPA.
    }

    public Task(String title, TaskPriority priority) {
        this.title = Objects.requireNonNull(title);
        this.priority = Objects.requireNonNull(priority);
        this.completed = false;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    /** L'entité protège sa propre mise à jour au lieu d'exposer des setters publics. */
    public void update(String title, TaskPriority priority, boolean completed) {
        this.title = Objects.requireNonNull(title);
        this.priority = Objects.requireNonNull(priority);
        this.completed = completed;
    }
}
