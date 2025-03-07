package Tareas.tareas.ControllerTest;

import Tareas.tareas.Controller.TaskController;
import Tareas.tareas.DTO.TaskCreateDTO;
import Tareas.tareas.DTO.TaskDTO;
import Tareas.tareas.Service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllTasks() throws Exception {
        TaskDTO task1 = new TaskDTO("uuid-1", "Tarea 1", "Descripción 1", 1, "Por Hacer");
        TaskDTO task2 = new TaskDTO("uuid-2", "Tarea 2", "Descripción 2", 1, "Por Hacer");

        Mockito.when(taskService.getAllTasks()).thenReturn(Arrays.asList(task1, task2));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testCreateTask() throws Exception {
        TaskCreateDTO createDTO = new TaskCreateDTO("Nueva Tarea", "Descripción", 1);
        TaskDTO responseDTO = new TaskDTO("uuid-123", "Nueva Tarea", "Descripción", 1, "Por Hacer");

        Mockito.when(taskService.createTask(any(TaskCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("uuid-123"))
                .andExpect(jsonPath("$.title").value("Nueva Tarea"));
    }

    @Test
    public void testGetTaskById() throws Exception {
        TaskDTO responseDTO = new TaskDTO("uuid-123", "Tarea 1", "Descripción", 1, "Por Hacer");

        Mockito.when(taskService.getTaskById("uuid-123")).thenReturn(responseDTO);

        mockMvc.perform(get("/api/tasks/uuid-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("uuid-123"))
                .andExpect(jsonPath("$.title").value("Tarea 1"));
    }

    @Test
    public void testUpdateTask() throws Exception {
        TaskCreateDTO updateDTO = new TaskCreateDTO("Tarea Actualizada", "Descripción actualizada", 2);
        TaskDTO responseDTO = new TaskDTO("uuid-123", "Tarea Actualizada", "Descripción actualizada", 2, "En Progreso");

        Mockito.when(taskService.updateTask(Mockito.eq("uuid-123"), any(TaskCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/tasks/uuid-123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Tarea Actualizada"));
    }

    @Test
    public void testDeleteTask() throws Exception {
        Mockito.doNothing().when(taskService).deleteTask("uuid-123");

        mockMvc.perform(delete("/api/tasks/uuid-123"))
                .andExpect(status().isNoContent());
    }
}

