package com.lsanz.taskmanager.repository;

import com.lsanz.taskmanager.entity.Task;
import com.lsanz.taskmanager.entity.TaskStatus;
import com.lsanz.taskmanager.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenSavingTask_thenItCanBeQueriedByStatus() {
        // Arrange -> Preparar datos de prueba
        User user = new User();
        user.setName("Luis");
        user.setEmail("luis@example.com");
        user = userRepository.save(user);

        Task task = new Task();
        task.setTitle("Demo task");
        task.setStatus(TaskStatus.OPEN);
        task.setUser(user);
        taskRepository.save(task);

        // Act -> Ejecutar consulta
        var result = taskRepository.findByStatus(TaskStatus.OPEN, PageRequest.of(0, 10));

        // Assert -> Verificar el resultado
        assertThat(result.getTotalElements()).isGreaterThan(0);
    }
}
