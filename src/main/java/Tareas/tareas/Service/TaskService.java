package Tareas.tareas.Service;


import Tareas.tareas.DTO.TaskCreateDTO;
import Tareas.tareas.DTO.TaskDTO;
import Tareas.tareas.Entity.State;
import Tareas.tareas.Entity.Task;
import Tareas.tareas.Repository.StateRepository;
import Tareas.tareas.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private StateRepository stateRepository;

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO getTaskById(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        return convertToDTO(task);
    }

    public TaskDTO createTask(TaskCreateDTO taskCreateDTO) {
        State state = stateRepository.findById(taskCreateDTO.getStateId())
                .orElseThrow(() -> new RuntimeException("State not found with id: " + taskCreateDTO.getStateId()));
        Task task = new Task(taskCreateDTO.getTitle(), taskCreateDTO.getDescription(), state);
        task = taskRepository.save(task);
        return convertToDTO(task);
    }

    public TaskDTO updateTask(String id, TaskCreateDTO taskUpdateDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        State state = stateRepository.findById(taskUpdateDTO.getStateId())
                .orElseThrow(() -> new RuntimeException("State not found with id: " + taskUpdateDTO.getStateId()));
        task.setTitle(taskUpdateDTO.getTitle());
        task.setDescription(taskUpdateDTO.getDescription());
        task.setState(state);
        task = taskRepository.save(task);
        return convertToDTO(task);
    }

    public void deleteTask(String id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    private TaskDTO convertToDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getState().getId(),
                task.getState().getName()
        );
    }
}
