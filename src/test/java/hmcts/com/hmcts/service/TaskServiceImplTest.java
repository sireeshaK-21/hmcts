package hmcts.com.hmcts.service;
import hmcts.com.hmcts.entity.Task;
import hmcts.com.hmcts.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceImplTest {

    private TaskRepository taskRepository;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskRepository = Mockito.mock(TaskRepository.class);
        taskService = new TaskServiceImpl(taskRepository);
    }

    @Test
    void getAllTasks_shouldReturnTasks() {
        List<Task> mockTasks = Arrays.asList(new Task(1L, "Test Task"));
        when(taskRepository.findAll()).thenReturn(mockTasks);

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(1, tasks.size());
        assertEquals("Test Task", tasks.get(0).getTitle());
    }

    @Test
    void getTaskById_shouldReturnTask() {
        Task task = new Task(1L, "Test Task");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(1L);
        assertTrue(result.isPresent());
        assertEquals("Test Task", result.get().getTitle());
    }

    @Test
    void createTask_shouldReturnSavedTask() {
        Task task = new Task(null, "New Task");
        Task saved = new Task(1L, "New Task");

        when(taskRepository.save(task)).thenReturn(saved);

        Task result = taskService.createTask(task);
        assertEquals(1L, result.getId());
    }

    @Test
    void updateTask_shouldUpdateAndReturnTask() {
        Task existing = new Task(1L, "Old Task");
        Task updated = new Task(1L, "Updated Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(taskRepository.save(any())).thenReturn(updated);

        Task result = taskService.updateTask(1L, updated);
        assertEquals("Updated Task", result.getTitle());
    }

    @Test
    void deleteTask_shouldCallRepositoryDelete() {
        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}