package hmcts.com.hmcts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hmcts.com.hmcts.contoller.TaskController;
import hmcts.com.hmcts.entity.Task;
import hmcts.com.hmcts.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        sampleTask = new Task(1L, "Sample Task");
    }

    @Test
    void getAllTasks_shouldReturnList() throws Exception {
        Mockito.when(taskService.getAllTasks()).thenReturn(Arrays.asList(sampleTask));

        mockMvc.perform(get("/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Sample Task"));
    }

    @Test
    void getTaskById_shouldReturnTask() throws Exception {
        Mockito.when(taskService.getTaskById(1L)).thenReturn(Optional.of(sampleTask));

        mockMvc.perform(get("/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Task"));
    }

    @Test
    void createTask_shouldReturnCreatedTask() throws Exception {
        Task toCreate = new Task(null, "New Task");
        Task created = new Task(1L, "New Task");

        Mockito.when(taskService.createTask(any())).thenReturn(created);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(toCreate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    void updateTask_shouldReturnUpdatedTask() throws Exception {
        Task updated = new Task(1L, "Updated Task");

        Mockito.when(taskService.updateTask(any(Long.class), any(Task.class))).thenReturn(updated);

        mockMvc.perform(put("/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"));
    }

    @Test
    void deleteTask_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/1"))
                .andExpect(status().isOk());

        Mockito.verify(taskService).deleteTask(1L);
    }
}