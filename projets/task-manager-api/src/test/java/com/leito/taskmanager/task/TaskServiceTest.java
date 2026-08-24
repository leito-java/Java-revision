package com.leito.taskmanager.task;

import com.leito.taskmanager.task.api.CreateTaskRequest;
import com.leito.taskmanager.task.application.TaskService;
import com.leito.taskmanager.task.domain.Task;
import com.leito.taskmanager.task.domain.TaskPriority;
import com.leito.taskmanager.task.infrastructure.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService service;

    @Test
    void createNormalizesTheTitleAndStartsIncomplete() {
        given(repository.save(any(Task.class))).willAnswer(invocation -> invocation.getArgument(0));

        var response = service.create(new CreateTaskRequest("  Apprendre HttpClient  ", TaskPriority.HIGH));

        assertThat(response.title()).isEqualTo("Apprendre HttpClient");
        assertThat(response.priority()).isEqualTo(TaskPriority.HIGH);
        assertThat(response.completed()).isFalse();
    }
}
