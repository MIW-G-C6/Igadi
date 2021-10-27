package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

/**
 * @author Lukas de Ruiter <lukas_kremlin@hotmail.com>
 *
 *     These tasks are coupled to gardens, so not specifically to a patch.
 */

@Entity
public class GardenTask extends Task {

    @ManyToOne()
    private Garden garden;

    public Garden getGarden() {
        return garden;
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
    }
}
