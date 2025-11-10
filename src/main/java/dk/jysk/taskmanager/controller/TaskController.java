package dk.jysk.taskmanager.controller;

import dk.jysk.taskmanager.dto.ErrorResponse;
import dk.jysk.taskmanager.dto.TaskDTO;
import dk.jysk.taskmanager.entity.TaskEntity;
import dk.jysk.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "List all tasks", description = "Accessible to USER and ADMIN roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = TaskEntity.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping
    public List<TaskEntity> getAll() {
        return service.findAll();
    }

    @Operation(summary = "List task by id", description = "Accessible to USER and ADMIN roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = TaskEntity.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Task not found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/{id}")
    public TaskEntity getById(
            @Parameter(description = "ID of the task to consult", example = "1") @PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Create a new task", description = "Requires ADMIN role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task Created Successfully", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = TaskEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "Error Creating Task", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) })
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskEntity create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Data to create task: only title, description and status are accepted",
                required = true,
                content = @Content(
                    schema = @Schema(implementation = TaskDTO.class)
                )
            )
            @Valid @RequestBody TaskDTO taskEntity) {
        return service.save(taskEntity);
    }

    @Operation(summary = "Update task by id", description = "Requires ADMIN role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task Updated Successfully", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = TaskEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "Error Updating Task", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Task not found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) })
    })
    @PutMapping("/{id}")
    public TaskEntity update(
            @Parameter(description = "ID of the task to update", example = "1") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Data to update task: only title, description and status are accepted",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = TaskDTO.class)
                    )
            )
            @Valid @RequestBody TaskDTO taskDTO) {
        return service.update(id, taskDTO);
    }

    @Operation(summary = "Delete task by id", description = "Requires ADMIN role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task Deleted Successfully"),
            @ApiResponse(responseCode = "400", description = "Error Deleting Task", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) })
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "ID of the task to delete", example = "1")  @PathVariable Long id) {
        service.delete(id);
    }
}
