package dk.jysk.taskmanager.service;

import dk.jysk.taskmanager.entity.TaskEntity;

import java.util.List;

public interface TaskService {
    List<TaskEntity> findAll();

    TaskEntity findById(Long id);

    TaskEntity save(TaskEntity taskEntity);

    TaskEntity update(Long id, TaskEntity updatedTaskEntity);

    void delete(Long id);
}
