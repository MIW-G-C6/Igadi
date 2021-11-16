package nl.miwgroningen.se6.gardengnomes.Igadi.dto;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 */

public class TaskDTO {

    private Integer taskId;
    private String taskName;
    private String taskDescription;
    private boolean isDone;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String doneOrNot() {
        if(this.isDone) {
            return "Done";
        } else {
            return "Not done";
        }
    }
}