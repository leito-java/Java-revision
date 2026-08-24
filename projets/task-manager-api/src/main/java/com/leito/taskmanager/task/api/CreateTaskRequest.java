package com.leito.taskmanager.task.api;

import com.leito.taskmanager.task.domain.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Corps JSON attendu pour créer une tâche. */
public record CreateTaskRequest(
        @NotBlank(message = "Le titre est obligatoire")
        @Size(min = 3, max = 120, message = "Le titre doit contenir entre 3 et 120 caractères")
        String title,

        @NotNull(message = "La priorité est obligatoire")
        TaskPriority priority
) {
    public CreateTaskRequest {
        if (title != null) title = title.trim();
    }
}
