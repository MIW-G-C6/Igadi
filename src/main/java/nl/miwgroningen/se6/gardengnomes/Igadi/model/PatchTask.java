package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 *
 *     These tasks are coupled to a specific patch, which in turn is part of a garden.
 */

@Entity
public class PatchTask extends Task {

    @ManyToOne()
    private Patch patch;

    public Patch getPatch() {
        return patch;
    }

    public void setPatch(Patch patch) {
        this.patch = patch;
    }
}