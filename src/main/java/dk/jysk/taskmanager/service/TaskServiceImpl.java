package dk.jysk.taskmanager.service;

import dk.jysk.taskmanager.entity.TaskEntity;
import dk.jysk.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<TaskEntity> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public TaskEntity findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Override
    public TaskEntity save(TaskEntity taskEntity) {
        return taskRepository.save(taskEntity);
    }

    @Override
    public TaskEntity update(Long id, TaskEntity updatedTaskEntity) {
        TaskEntity existing = findById(id);
        existing.setTitle(updatedTaskEntity.getTitle());
        existing.setDescription(updatedTaskEntity.getDescription());
        existing.setStatus(updatedTaskEntity.getStatus());
        return taskRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
