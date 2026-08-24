package com.leito.taskmanager.task.api;

import com.leito.taskmanager.task.domain.Task;
import com.leito.taskmanager.task.domain.TaskPriority;

/** Réponse publique de l'API : l'entité JPA n'est jamais exposée directement. */
public record TaskResponse(
        Long id,
        String title,
        boolean completed,
        TaskPriority priority
) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.isCompleted(), task.getPriority());
    }
}
