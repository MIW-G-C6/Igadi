/*
package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

*/
/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 *
 *     This class contains task information for things that have been done/can be done in the gardens. Tasks are coupled
 *     to one patch/garden per task. The isDone field represents if a task has been completed or not.
 *//*


public class Task {
    //Fields
    @Id
    @GeneratedValue
    @Column(name="taskId")
    private Integer taskId;

    @Column(unique = true, nullable = false, name="taskName")
    private String taskName;

    @Column(unique = true, nullable = false, name="taskDescription")
    private String taskDescription;

    @Column(unique = true, nullable = false, name="isDone")
    private boolean isDone;

    @ManyToOne
    private int patchId;

    @ManyToOne
    private int gardenId;
}*/
