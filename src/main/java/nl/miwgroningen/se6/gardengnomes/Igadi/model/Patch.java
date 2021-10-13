package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Tjerk Nagel
 *
 *
 *     This class is used to create patches, which are part of a garden. They are managed by one garden manager.
 */

@Entity
public class Patch {

    // Fields
    @Id
    @GeneratedValue
    @Column(name="patchId")
    private Integer patchId;

    @ManyToOne
    private Garden garden;

    public Integer getPatchId() {
        return patchId;
    }

    public void setPatchId(Integer patchId) {
        this.patchId = patchId;
    }

    public Garden getGarden() {
        return garden;
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
    }
}