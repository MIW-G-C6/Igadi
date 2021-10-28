package nl.miwgroningen.se6.gardengnomes.Igadi.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="patchId")
    private Integer patchId;


    @ManyToOne()
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name = "garden_gardenId")
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
