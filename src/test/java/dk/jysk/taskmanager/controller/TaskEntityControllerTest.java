package dk.jysk.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.jysk.taskmanager.dto.TaskDTO;
import dk.jysk.taskmanager.entity.TaskEntity;
import dk.jysk.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Base64;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskEntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    private String adminAuth;
    private String userAuth;

    @BeforeEach
    void setup() {
        taskRepository.deleteAll();

        adminAuth = "Basic " + Base64.getEncoder().encodeToString("admin:admin123".getBytes());
        userAuth = "Basic " + Base64.getEncoder().encodeToString("user:user123".getBytes());
    }

    @Test
    void shouldReturnAllTasksForUser() throws Exception {
        mockMvc.perform(get("/tasks")
                        .header("Authorization", userAuth)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateTaskAsAdmin() throws Exception {
        TaskDTO newTask = new TaskDTO("Prepare Interview", "Create demo project", "PENDING");

        mockMvc.perform(post("/tasks")
                        .header("Authorization", adminAuth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Prepare Interview"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void shouldForbidUserFromCreatingTask() throws Exception {
        TaskDTO newTask = new TaskDTO("Unauthorized Task", "User should not create this", "PENDING");

        mockMvc.perform(post("/tasks")
                        .header("Authorization", userAuth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTask)))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnTaskById() throws Exception {
        TaskEntity saved = taskRepository.save(new TaskEntity(null, "Read Docs", "Spring Boot testing", "DONE", null, null));

        mockMvc.perform(get("/tasks/{id}", saved.getId())
                        .header("Authorization", userAuth))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.title").value("Read Docs"))
                .andExpect((ResultMatcher) jsonPath("$.status").value("DONE"));
    }

    @Test
    void shouldUpdateTaskAsAdmin() throws Exception {
        TaskEntity saved = taskRepository.save(new TaskEntity(null, "Old Title", "Old Description", "PENDING", null, null));

        TaskDTO update = new TaskDTO("Updated Title", "Updated Description", "IN_PROGRESS");

        mockMvc.perform(put("/tasks/{id}", saved.getId())
                        .header("Authorization", adminAuth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.title").value("Updated Title"))
                .andExpect((ResultMatcher) jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void shouldForbidUserFromUpdatingTask() throws Exception {
        TaskEntity saved = taskRepository.save(new TaskEntity(null, "Blocked Task", "User cannot update", "PENDING", null, null));

        TaskDTO update = new TaskDTO("Hack Attempt", "User trying to update", "FAILED");

        mockMvc.perform(put("/tasks/{id}", saved.getId())
                        .header("Authorization", userAuth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldDeleteTaskAsAdmin() throws Exception {
        TaskEntity saved = taskRepository.save(new TaskEntity(null, "Delete Me", "Cleanup", "DONE", null, null));

        mockMvc.perform(delete("/tasks/{id}", saved.getId())
                        .header("Authorization", adminAuth))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldForbidUserFromDeletingTask() throws Exception {
        TaskEntity saved = taskRepository.save(new TaskEntity(null, "Protected Task", "User cannot delete", "DONE", null, null));

        mockMvc.perform(delete("/tasks/{id}", saved.getId())
                        .header("Authorization", userAuth))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturn404WhenTaskNotFound() throws Exception {
        mockMvc.perform(get("/tasks/{id}", 9999L)
                        .header("Authorization", userAuth))
                .andExpect(status().is4xxClientError());
    }

}
