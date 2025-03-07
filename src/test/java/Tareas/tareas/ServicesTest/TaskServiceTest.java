package Tareas.tareas.ServicesTest;

import Tareas.tareas.DTO.TaskCreateDTO;
import Tareas.tareas.DTO.TaskDTO;
import Tareas.tareas.Entity.State;
import Tareas.tareas.Entity.Task;
import Tareas.tareas.Repository.StateRepository;
import Tareas.tareas.Repository.TaskRepository;
import Tareas.tareas.Service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private StateRepository stateRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTasks() {

        State state = new State("Por Hacer");
        state.setId(1);
        Task task1 = new Task("Tarea 1", "Descripción 1", state);
        task1.setId("uuid-1");
        Task task2 = new Task("Tarea 2", "Descripción 2", state);
        task2.setId("uuid-2");

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<TaskDTO> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testCreateTask() {

        TaskCreateDTO createDTO = new TaskCreateDTO("Nueva Tarea", "Descripción", 1);
        State state = new State("Por Hacer");
        state.setId(1);

        when(stateRepository.findById(1)).thenReturn(Optional.of(state));

        Task savedTask = new Task("Nueva Tarea", "Descripción", state);
        savedTask.setId("uuid-123");

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskDTO createdTask = taskService.createTask(createDTO);

        assertNotNull(createdTask);
        assertEquals("Nueva Tarea", createdTask.getTitle());
        assertEquals(1, createdTask.getStateId());
        verify(stateRepository, times(1)).findById(1);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    public void testUpdateTask() {

        State state = new State("Por Hacer");
        state.setId(1);
        State newState = new State("En Progreso");
        newState.setId(2);

        Task existingTask = new Task("Tarea Existente", "Descripción vieja", state);
        existingTask.setId("uuid-123");

        TaskCreateDTO updateDTO = new TaskCreateDTO("Tarea Actualizada", "Descripción nueva", 2);

        when(taskRepository.findById("uuid-123")).thenReturn(Optional.of(existingTask));
        when(stateRepository.findById(2)).thenReturn(Optional.of(newState));

        Task updatedTask = new Task("Tarea Actualizada", "Descripción nueva", newState);
        updatedTask.setId("uuid-123");

        when(taskRepository.save(existingTask)).thenReturn(updatedTask);

        TaskDTO result = taskService.updateTask("uuid-123", updateDTO);

        assertEquals("Tarea Actualizada", result.getTitle());
        assertEquals(2, result.getStateId());
        verify(taskRepository, times(1)).findById("uuid-123");
        verify(stateRepository, times(1)).findById(2);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    public void testDeleteTask() {

        when(taskRepository.existsById("uuid-123")).thenReturn(true);

        taskService.deleteTask("uuid-123");

        verify(taskRepository, times(1)).deleteById("uuid-123");
    }
}
