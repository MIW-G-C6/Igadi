package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import javax.persistence.*;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 *
 *     This class contains task information for things that have been done/can be done in the gardens. Tasks are coupled
 *     to one patch/garden per task. The isDone field represents if a task has been completed or not.
 */

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Task {

    //Fields
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE) // TODO correctly implement TABLE strategy and test if this works to prevent bug IG-89 (primary key violation bug) when you create a PatchTask or a GardenTask
    @Column(name="taskId")
    private Integer taskId;

    @Column(nullable = false, name="taskName")
    private String taskName;

    @Column(nullable = false, name="taskDescription")
    private String taskDescription;

    @Column(nullable = false, name="isDone")
    private boolean isDone;

    public Integer getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public boolean getIsDone() {
        return isDone;
    }
}