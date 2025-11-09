package dk.jysk.taskmanager.service;

import dk.jysk.taskmanager.entity.TaskEntity;
import dk.jysk.taskmanager.exception.TaskManagementException;
import dk.jysk.taskmanager.exception.TaskNotFoundException;
import dk.jysk.taskmanager.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<TaskEntity> findAll() {
        log.info("Fetching every task");
        return taskRepository.findAll();
    }

    @Override
    public TaskEntity findById(Long id) {
        log.info("Fetching task with ID: {}", id);
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found."));
    }

    @Override
    public TaskEntity save(TaskEntity taskEntity) {
        log.info("Creating new task with title: {}", taskEntity.getTitle());
        try {
            TaskEntity saved = taskRepository.save(taskEntity);
            log.debug("Task created successfully with ID: {}", saved.getId());
            return saved;
        } catch (Exception e) {
            log.error("Error creating task", e);
            throw new TaskManagementException("Failed to create task");
        }
    }

    @Override
    public synchronized TaskEntity update(Long id, TaskEntity updatedTaskEntity) {
        log.info("Updating task with ID: {}", id);
        try {
            TaskEntity existing = findById(id);
            existing.setTitle(updatedTaskEntity.getTitle());
            existing.setDescription(updatedTaskEntity.getDescription());
            existing.setStatus(updatedTaskEntity.getStatus());
            return taskRepository.save(existing);
        } catch (TaskNotFoundException nf) {
            log.error(nf.getMessage());
            throw nf;
        } catch (Exception e) {
            log.error("Error updating task", e);
            throw new TaskManagementException("Failed to update task");
        }
    }

    @Override
    public synchronized void delete(Long id) {
        log.info("Deleting task with ID: {}", id);
        try {
            taskRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting task", e);
            throw new TaskManagementException("Failed to delete task");
        }
    }
}
