package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 *
 *     These tasks are coupled to a specific patch, which in turn is part of a garden.
 */

@Entity
@PrimaryKeyJoinColumn(name = "task_taskId")
public class PatchTask extends Task {

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "patch_patchId")
    private Patch patch;

    public Patch getPatch() {
        return patch;
    }

    public void setPatch(Patch patch) {
        this.patch = patch;
    }
}