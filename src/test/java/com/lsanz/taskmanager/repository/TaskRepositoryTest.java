package com.lsanz.taskmanager.repository;

import com.lsanz.taskmanager.entity.Task;
import com.lsanz.taskmanager.entity.TaskStatus;
import com.lsanz.taskmanager.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void findByDueDate_returnsTasksWithMatchingDate() {
        // Arrange
        User user = new User();
        user.setName("Luis");
        user.setEmail("luis@example.com");
        user = userRepository.save(user);

        Task task = new Task();
        task.setTitle("Demo task 1");
        task.setStatus(TaskStatus.OPEN);
        task.setUser(user);
        task.setDueDate(LocalDate.of(2025,1,1));
        taskRepository.save(task);

        Task task2 = new Task();
        task2.setTitle("Demo task 2");
        task2.setStatus(TaskStatus.OPEN);
        task2.setUser(user);
        task2.setDueDate(LocalDate.of(2025,1,1));
        taskRepository.save(task2);

        Task task3 = new Task();
        task3.setTitle("Demo task 3");
        task3.setStatus(TaskStatus.OPEN);
        task3.setUser(user);
        task3.setDueDate(LocalDate.of(2025,1,3));
        taskRepository.save(task3);

        // Act
        var result = taskRepository.findByDueDate(LocalDate.of(2025,1,1), PageRequest.of(0, 10));

        // Assert
        assertThat(result.getTotalElements()).isEqualTo(2);

        // Verificar titulos de las tareas
        assertThat(result.getContent()
                .stream()
                .map(Task::getTitle)
                .toList())
                .containsExactlyInAnyOrder("Demo task 1", "Demo task 2");

    }

    @Test
    void findByUserId() {

    }
}
