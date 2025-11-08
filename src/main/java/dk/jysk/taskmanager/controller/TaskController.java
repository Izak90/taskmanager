package dk.jysk.taskmanager.controller;

import dk.jysk.taskmanager.entity.TaskEntity;
import dk.jysk.taskmanager.exceptions.CreatingTaskException;
import dk.jysk.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping
    public List<TaskEntity> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TaskEntity getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskEntity create(@Valid @RequestBody TaskEntity taskEntity) {
        if (taskEntity.getId() != null) {
            throw new CreatingTaskException("Error creating a new task. The parameter id cannot be passed", "BAD_TASK_ID", HttpStatus.BAD_REQUEST);
        }
        return service.save(taskEntity);
    }

    @PutMapping("/{id}")
    public TaskEntity update(@PathVariable Long id, @Valid @RequestBody TaskEntity taskEntity) {
        return service.update(id, taskEntity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
