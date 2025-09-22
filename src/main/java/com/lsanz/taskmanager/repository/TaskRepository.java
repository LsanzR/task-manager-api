package com.lsanz.taskmanager.repository;

import com.lsanz.taskmanager.entity.Task;
import com.lsanz.taskmanager.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Listar por estado
    Page<Task> findByStatus(TaskStatus status, Pageable pageable);

    // Listar por usuario
    Page<Task> findByUserId(Long userId, Pageable pageable);

    // Listar por usuario + estado
    Page<Task> findByUserIdAndStatus(Long userId, TaskStatus status, Pageable pageable);

    // Rango de fechas de vencimiento
    Page<Task> findByDueDateBetween(LocalDate from,LocalDate to, Pageable pageable);

    // Comprobar si un usuario ya tiene una tarea con el mismo titulo
    boolean existsByUserIdAndTitle(Long userId, String title);
}
