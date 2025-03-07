package Tareas.tareas.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskCreateDTO {
    @NotBlank
    private String title;

    private String description;

    @NotNull
    private Integer stateId;

    public TaskCreateDTO() {}

    public TaskCreateDTO(String title, String description, Integer stateId) {
        this.title = title;
        this.description = description;
        this.stateId = stateId;
    }

    // Getters y setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getStateId() {
        return stateId;
    }
    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }
}
