package dk.jysk.taskmanager.service;

import dk.jysk.taskmanager.dto.TaskDTO;
import dk.jysk.taskmanager.entity.TaskEntity;

import java.util.List;

public interface TaskService {
    List<TaskEntity> findAll();

    TaskEntity findById(Long id);

    TaskEntity save(TaskDTO taskDTO);

    TaskEntity update(Long id, TaskDTO taskDTO);

    void delete(Long id);
}
