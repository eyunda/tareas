package Tareas.tareas.DTO;

public class TaskDTO {
    private String id;
    private String title;
    private String description;
    private Integer stateId;
    private String stateName;

    public TaskDTO() {}

    public TaskDTO(String id, String title, String description, Integer stateId, String stateName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.stateId = stateId;
        this.stateName = stateName;
    }

    // Getters y setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
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
    public String getStateName() {
        return stateName;
    }
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
